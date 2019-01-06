package com.neovisionaries.ws.client;

import java.io.IOException;
import java.util.LinkedList;

class WritingThread
  extends Thread
{
  private static final int SHOULD_SEND = 0;
  private static final int SHOULD_STOP = 1;
  private static final int SHOULD_CONTINUE = 2;
  private static final int SHOULD_FLUSH = 3;
  private static final int FLUSH_THRESHOLD = 1000;
  private final WebSocket mWebSocket;
  private final LinkedList<WebSocketFrame> mFrames;
  private final PerMessageCompressionExtension mPMCE;
  private boolean mStopRequested;
  private WebSocketFrame mCloseFrame;
  private boolean mFlushNeeded;
  private boolean mStopped;
  
  public WritingThread(WebSocket websocket)
  {
    super("WritingThread");
    
    this.mWebSocket = websocket;
    this.mFrames = new LinkedList();
    this.mPMCE = websocket.getPerMessageCompressionExtension();
  }
  
  public void run()
  {
    try
    {
      main();
    }
    catch (Throwable t)
    {
      WebSocketException cause = new WebSocketException(WebSocketError.UNEXPECTED_ERROR_IN_WRITING_THREAD, "An uncaught throwable was detected in the writing thread: " + t.getMessage(), t);
      
      ListenerManager manager = this.mWebSocket.getListenerManager();
      manager.callOnError(cause);
      manager.callOnUnexpectedError(cause);
    }
    synchronized (this)
    {
      this.mStopped = true;
      notifyAll();
    }
  }
  
  private void main()
  {
    this.mWebSocket.onWritingThreadStarted();
    for (;;)
    {
      int result = waitForFrames();
      if (result == 1) {
        break;
      }
      if (result == 3) {
        flushIgnoreError();
      } else if (result != 2) {
        try
        {
          sendFrames(false);
        }
        catch (WebSocketException e)
        {
          break;
        }
      }
    }
    try
    {
      sendFrames(true);
    }
    catch (WebSocketException e) {}
    notifyFinished();
  }
  
  public void requestStop()
  {
    synchronized (this)
    {
      this.mStopRequested = true;
      
      notifyAll();
    }
  }
  
  public boolean queueFrame(WebSocketFrame frame)
  {
    synchronized (this)
    {
      for (;;)
      {
        if (this.mStopped) {
          return false;
        }
        if ((this.mStopRequested) || (this.mCloseFrame != null)) {
          break;
        }
        if (frame.isControlFrame()) {
          break;
        }
        int queueSize = this.mWebSocket.getFrameQueueSize();
        if (queueSize == 0) {
          break;
        }
        if (this.mFrames.size() < queueSize) {
          break;
        }
        try
        {
          wait();
        }
        catch (InterruptedException e) {}
      }
      if (isHighPriorityFrame(frame)) {
        addHighPriorityFrame(frame);
      } else {
        this.mFrames.addLast(frame);
      }
      notifyAll();
    }
    return true;
  }
  
  private static boolean isHighPriorityFrame(WebSocketFrame frame)
  {
    return (frame.isPingFrame()) || (frame.isPongFrame());
  }
  
  private void addHighPriorityFrame(WebSocketFrame frame)
  {
    int index = 0;
    for (WebSocketFrame f : this.mFrames)
    {
      if (!isHighPriorityFrame(f)) {
        break;
      }
      index++;
    }
    this.mFrames.add(index, frame);
  }
  
  public void queueFlush()
  {
    synchronized (this)
    {
      this.mFlushNeeded = true;
      
      notifyAll();
    }
  }
  
  private void flushIgnoreError()
  {
    try
    {
      flush();
    }
    catch (IOException e) {}
  }
  
  private void flush()
    throws IOException
  {
    this.mWebSocket.getOutput().flush();
  }
  
  private int waitForFrames()
  {
    synchronized (this)
    {
      if (this.mStopRequested) {
        return 1;
      }
      if (this.mCloseFrame != null) {
        return 1;
      }
      if (this.mFrames.size() == 0)
      {
        if (this.mFlushNeeded)
        {
          this.mFlushNeeded = false;
          return 3;
        }
        try
        {
          wait();
        }
        catch (InterruptedException e) {}
      }
      if (this.mStopRequested) {
        return 1;
      }
      if (this.mFrames.size() == 0)
      {
        if (this.mFlushNeeded)
        {
          this.mFlushNeeded = false;
          return 3;
        }
        return 2;
      }
    }
    return 0;
  }
  
  private void sendFrames(boolean last)
    throws WebSocketException
  {
    long lastFlushAt = System.currentTimeMillis();
    for (;;)
    {
      WebSocketFrame frame;
      synchronized (this)
      {
        frame = (WebSocketFrame)this.mFrames.poll();
        
        notifyAll();
        if (frame == null) {
          break;
        }
      }
      sendFrame(frame);
      if ((frame.isPingFrame()) || (frame.isPongFrame()))
      {
        doFlush();
        lastFlushAt = System.currentTimeMillis();
      }
      else if (isFlushNeeded(last))
      {
        lastFlushAt = flushIfLongInterval(lastFlushAt);
      }
    }
    if (isFlushNeeded(last)) {
      doFlush();
    }
  }
  
  private boolean isFlushNeeded(boolean last)
  {
    return (last) || (this.mWebSocket.isAutoFlush()) || (this.mFlushNeeded) || (this.mCloseFrame != null);
  }
  
  private long flushIfLongInterval(long lastFlushAt)
    throws WebSocketException
  {
    long current = System.currentTimeMillis();
    if (1000L < current - lastFlushAt)
    {
      doFlush();
      
      return current;
    }
    return lastFlushAt;
  }
  
  private void doFlush()
    throws WebSocketException
  {
    try
    {
      flush();
      synchronized (this)
      {
        this.mFlushNeeded = false;
      }
    }
    catch (IOException e)
    {
      WebSocketException cause = new WebSocketException(WebSocketError.FLUSH_ERROR, "Flushing frames to the server failed: " + e.getMessage(), e);
      
      ListenerManager manager = this.mWebSocket.getListenerManager();
      manager.callOnError(cause);
      manager.callOnSendError(cause, null);
      
      throw cause;
    }
  }
  
  private void sendFrame(WebSocketFrame frame)
    throws WebSocketException
  {
    frame = compressFrame(frame);
    
    this.mWebSocket.getListenerManager().callOnSendingFrame(frame);
    
    boolean unsent = false;
    if (this.mCloseFrame != null) {
      unsent = true;
    } else if (frame.isCloseFrame()) {
      this.mCloseFrame = frame;
    }
    if (unsent)
    {
      this.mWebSocket.getListenerManager().callOnFrameUnsent(frame);
      return;
    }
    if (frame.isCloseFrame()) {
      changeToClosing();
    }
    try
    {
      this.mWebSocket.getOutput().write(frame);
    }
    catch (IOException e)
    {
      WebSocketException cause = new WebSocketException(WebSocketError.IO_ERROR_IN_WRITING, "An I/O error occurred when a frame was tried to be sent: " + e.getMessage(), e);
      
      ListenerManager manager = this.mWebSocket.getListenerManager();
      manager.callOnError(cause);
      manager.callOnSendError(cause, frame);
      
      throw cause;
    }
    this.mWebSocket.getListenerManager().callOnFrameSent(frame);
  }
  
  private void changeToClosing()
  {
    StateManager manager = this.mWebSocket.getStateManager();
    
    boolean stateChanged = false;
    synchronized (manager)
    {
      WebSocketState state = manager.getState();
      if ((state != WebSocketState.CLOSING) && (state != WebSocketState.CLOSED))
      {
        manager.changeToClosing(StateManager.CloseInitiator.CLIENT);
        
        stateChanged = true;
      }
    }
    if (stateChanged) {
      this.mWebSocket.getListenerManager().callOnStateChanged(WebSocketState.CLOSING);
    }
  }
  
  private void notifyFinished()
  {
    this.mWebSocket.onWritingThreadFinished(this.mCloseFrame);
  }
  
  private WebSocketFrame compressFrame(WebSocketFrame frame)
  {
    if (this.mPMCE == null) {
      return frame;
    }
    if ((!frame.isTextFrame()) && 
      (!frame.isBinaryFrame())) {
      return frame;
    }
    if (!frame.getFin()) {
      return frame;
    }
    if (frame.getRsv1()) {
      return frame;
    }
    byte[] payload = frame.getPayload();
    if ((payload == null) || (payload.length == 0)) {
      return frame;
    }
    byte[] compressed = compress(payload);
    if (payload.length <= compressed.length) {
      return frame;
    }
    frame.setPayload(compressed);
    
    frame.setRsv1(true);
    
    return frame;
  }
  
  private byte[] compress(byte[] data)
  {
    try
    {
      return this.mPMCE.compress(data);
    }
    catch (WebSocketException e) {}
    return data;
  }
}
