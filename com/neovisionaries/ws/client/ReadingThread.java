package com.neovisionaries.ws.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class ReadingThread
  extends Thread
{
  private static final long INTERRUPTION_TIMER_DELAY = 60000L;
  private final WebSocket mWebSocket;
  private boolean mStopRequested;
  private WebSocketFrame mCloseFrame;
  private List<WebSocketFrame> mContinuation = new ArrayList();
  private final PerMessageCompressionExtension mPMCE;
  
  public ReadingThread(WebSocket websocket)
  {
    super("ReadingThread");
    
    this.mWebSocket = websocket;
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
      WebSocketException cause = new WebSocketException(WebSocketError.UNEXPECTED_ERROR_IN_READING_THREAD, "An uncaught throwable was detected in the reading thread: " + t.getMessage(), t);
      
      ListenerManager manager = this.mWebSocket.getListenerManager();
      manager.callOnError(cause);
      manager.callOnUnexpectedError(cause);
    }
  }
  
  private void main()
  {
    this.mWebSocket.onReadingThreadStarted();
    for (;;)
    {
      synchronized (this)
      {
        if (this.mStopRequested) {
          break;
        }
      }
      WebSocketFrame frame = readFrame();
      if (frame == null) {
        break;
      }
      boolean keepReading = handleFrame(frame);
      if (!keepReading) {
        break;
      }
    }
    waitForCloseFrame();
    
    notifyFinished();
  }
  
  void requestStop()
  {
    synchronized (this)
    {
      this.mStopRequested = true;
      interrupt();
    }
  }
  
  private void callOnFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnFrame(frame);
  }
  
  private void callOnContinuationFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnContinuationFrame(frame);
  }
  
  private void callOnTextFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnTextFrame(frame);
  }
  
  private void callOnBinaryFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnBinaryFrame(frame);
  }
  
  private void callOnCloseFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnCloseFrame(frame);
  }
  
  private void callOnPingFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnPingFrame(frame);
  }
  
  private void callOnPongFrame(WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnPongFrame(frame);
  }
  
  private void callOnTextMessage(byte[] data)
  {
    try
    {
      String message = Misc.toStringUTF8(data);
      
      callOnTextMessage(message);
    }
    catch (Throwable t)
    {
      WebSocketException wse = new WebSocketException(WebSocketError.TEXT_MESSAGE_CONSTRUCTION_ERROR, "Failed to convert payload data into a string: " + t.getMessage(), t);
      
      callOnError(wse);
      callOnTextMessageError(wse, data);
    }
  }
  
  private void callOnTextMessage(String message)
  {
    this.mWebSocket.getListenerManager().callOnTextMessage(message);
  }
  
  private void callOnBinaryMessage(byte[] message)
  {
    this.mWebSocket.getListenerManager().callOnBinaryMessage(message);
  }
  
  private void callOnError(WebSocketException cause)
  {
    this.mWebSocket.getListenerManager().callOnError(cause);
  }
  
  private void callOnFrameError(WebSocketException cause, WebSocketFrame frame)
  {
    this.mWebSocket.getListenerManager().callOnFrameError(cause, frame);
  }
  
  private void callOnMessageError(WebSocketException cause, List<WebSocketFrame> frames)
  {
    this.mWebSocket.getListenerManager().callOnMessageError(cause, frames);
  }
  
  private void callOnMessageDecompressionError(WebSocketException cause, byte[] compressed)
  {
    this.mWebSocket.getListenerManager().callOnMessageDecompressionError(cause, compressed);
  }
  
  private void callOnTextMessageError(WebSocketException cause, byte[] data)
  {
    this.mWebSocket.getListenerManager().callOnTextMessageError(cause, data);
  }
  
  private WebSocketFrame readFrame()
  {
    WebSocketFrame frame = null;
    WebSocketException wse = null;
    boolean intentionallyInterrupted = false;
    try
    {
      frame = this.mWebSocket.getInput().readFrame();
      
      verifyFrame(frame);
      
      return frame;
    }
    catch (InterruptedIOException e)
    {
      if (this.mStopRequested) {
        intentionallyInterrupted = true;
      } else {
        wse = new WebSocketException(WebSocketError.INTERRUPTED_IN_READING, "Interruption occurred while a frame was being read from the web socket: " + e.getMessage(), e);
      }
    }
    catch (IOException e)
    {
      wse = new WebSocketException(WebSocketError.IO_ERROR_IN_READING, "An I/O error occurred while a frame was being read from the web socket: " + e.getMessage(), e);
    }
    catch (WebSocketException e)
    {
      wse = e;
    }
    if (!intentionallyInterrupted)
    {
      callOnError(wse);
      callOnFrameError(wse, frame);
      
      WebSocketFrame closeFrame = createCloseFrame(wse);
      
      this.mWebSocket.sendFrame(closeFrame);
    }
    return null;
  }
  
  private void verifyFrame(WebSocketFrame frame)
    throws WebSocketException
  {
    verifyReservedBits(frame);
    
    verifyFrameOpcode(frame);
    
    verifyFrameMask(frame);
    
    verifyFrameFragmentation(frame);
    
    verifyFrameSize(frame);
  }
  
  private void verifyReservedBits(WebSocketFrame frame)
    throws WebSocketException
  {
    if (this.mWebSocket.isExtended()) {
      return;
    }
    verifyReservedBit1(frame);
    verifyReservedBit2(frame);
    verifyReservedBit3(frame);
  }
  
  private void verifyReservedBit1(WebSocketFrame frame)
    throws WebSocketException
  {
    if (this.mPMCE != null)
    {
      boolean verified = verifyReservedBit1ForPMCE(frame);
      if (verified) {
        return;
      }
    }
    if (!frame.getRsv1()) {
      return;
    }
    throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV1 bit of a frame is set unexpectedly.");
  }
  
  private boolean verifyReservedBit1ForPMCE(WebSocketFrame frame)
    throws WebSocketException
  {
    if ((frame.isTextFrame()) || (frame.isBinaryFrame())) {
      return true;
    }
    return false;
  }
  
  private void verifyReservedBit2(WebSocketFrame frame)
    throws WebSocketException
  {
    if (!frame.getRsv2()) {
      return;
    }
    throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV2 bit of a frame is set unexpectedly.");
  }
  
  private void verifyReservedBit3(WebSocketFrame frame)
    throws WebSocketException
  {
    if (!frame.getRsv3()) {
      return;
    }
    throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV3 bit of a frame is set unexpectedly.");
  }
  
  private void verifyFrameOpcode(WebSocketFrame frame)
    throws WebSocketException
  {
    switch (frame.getOpcode())
    {
    case 0: 
    case 1: 
    case 2: 
    case 8: 
    case 9: 
    case 10: 
      return;
    }
    if (this.mWebSocket.isExtended()) {
      return;
    }
    throw new WebSocketException(WebSocketError.UNKNOWN_OPCODE, "A frame has an unknown opcode: 0x" + Integer.toHexString(frame.getOpcode()));
  }
  
  private void verifyFrameMask(WebSocketFrame frame)
    throws WebSocketException
  {
    if (frame.getMask()) {
      throw new WebSocketException(WebSocketError.FRAME_MASKED, "A frame from the server is masked.");
    }
  }
  
  private void verifyFrameFragmentation(WebSocketFrame frame)
    throws WebSocketException
  {
    if (frame.isControlFrame())
    {
      if (!frame.getFin()) {
        throw new WebSocketException(WebSocketError.FRAGMENTED_CONTROL_FRAME, "A control frame is fragmented.");
      }
      return;
    }
    boolean continuationExists = this.mContinuation.size() != 0;
    if (frame.isContinuationFrame())
    {
      if (!continuationExists) {
        throw new WebSocketException(WebSocketError.UNEXPECTED_CONTINUATION_FRAME, "A continuation frame was detected although a continuation had not started.");
      }
      return;
    }
    if (continuationExists) {
      throw new WebSocketException(WebSocketError.CONTINUATION_NOT_CLOSED, "A non-control frame was detected although the existing continuation had not been closed.");
    }
  }
  
  private void verifyFrameSize(WebSocketFrame frame)
    throws WebSocketException
  {
    if (!frame.isControlFrame()) {
      return;
    }
    byte[] payload = frame.getPayload();
    if (payload == null) {
      return;
    }
    if (125 < payload.length) {
      throw new WebSocketException(WebSocketError.TOO_LONG_CONTROL_FRAME_PAYLOAD, "The payload size of a control frame exceeds the maximum size (125 bytes): " + payload.length);
    }
  }
  
  private WebSocketFrame createCloseFrame(WebSocketException wse)
  {
    int closeCode;
    int closeCode;
    int closeCode;
    int closeCode;
    int closeCode;
    switch (wse.getError())
    {
    case INSUFFICENT_DATA: 
    case INVALID_PAYLOAD_LENGTH: 
      closeCode = 1002;
      break;
    case TOO_LONG_PAYLOAD: 
    case INSUFFICIENT_MEMORY_FOR_PAYLOAD: 
      closeCode = 1009;
      break;
    case NON_ZERO_RESERVED_BITS: 
    case UNEXPECTED_RESERVED_BIT: 
    case UNKNOWN_OPCODE: 
    case FRAME_MASKED: 
    case FRAGMENTED_CONTROL_FRAME: 
    case UNEXPECTED_CONTINUATION_FRAME: 
    case CONTINUATION_NOT_CLOSED: 
    case TOO_LONG_CONTROL_FRAME_PAYLOAD: 
      closeCode = 1002;
      break;
    case INTERRUPTED_IN_READING: 
    case IO_ERROR_IN_READING: 
      closeCode = 1008;
      break;
    default: 
      closeCode = 1008;
    }
    return WebSocketFrame.createCloseFrame(closeCode, wse.getMessage());
  }
  
  private boolean handleFrame(WebSocketFrame frame)
  {
    callOnFrame(frame);
    switch (frame.getOpcode())
    {
    case 0: 
      return handleContinuationFrame(frame);
    case 1: 
      return handleTextFrame(frame);
    case 2: 
      return handleBinaryFrame(frame);
    case 8: 
      return handleCloseFrame(frame);
    case 9: 
      return handlePingFrame(frame);
    case 10: 
      return handlePongFrame(frame);
    }
    return true;
  }
  
  private boolean handleContinuationFrame(WebSocketFrame frame)
  {
    callOnContinuationFrame(frame);
    
    this.mContinuation.add(frame);
    if (!frame.getFin()) {
      return true;
    }
    byte[] data = getMessage(this.mContinuation);
    if (data == null) {
      return false;
    }
    if (((WebSocketFrame)this.mContinuation.get(0)).isTextFrame()) {
      callOnTextMessage(data);
    } else {
      callOnBinaryMessage(data);
    }
    this.mContinuation.clear();
    
    return true;
  }
  
  private byte[] getMessage(List<WebSocketFrame> frames)
  {
    byte[] data = concatenatePayloads(this.mContinuation);
    if (data == null) {
      return null;
    }
    if ((this.mPMCE != null) && (((WebSocketFrame)frames.get(0)).getRsv1())) {
      data = decompress(data);
    }
    return data;
  }
  
  private byte[] concatenatePayloads(List<WebSocketFrame> frames)
  {
    Throwable cause;
    try
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      for (WebSocketFrame frame : frames)
      {
        byte[] payload = frame.getPayload();
        if ((payload != null) && (payload.length != 0)) {
          baos.write(payload);
        }
      }
      return baos.toByteArray();
    }
    catch (IOException e)
    {
      cause = e;
    }
    catch (OutOfMemoryError e)
    {
      cause = e;
    }
    WebSocketException wse = new WebSocketException(WebSocketError.MESSAGE_CONSTRUCTION_ERROR, "Failed to concatenate payloads of multiple frames to construct a message: " + cause.getMessage(), cause);
    
    callOnError(wse);
    callOnMessageError(wse, frames);
    
    WebSocketFrame frame = WebSocketFrame.createCloseFrame(1009, wse.getMessage());
    
    this.mWebSocket.sendFrame(frame);
    
    return null;
  }
  
  private byte[] getMessage(WebSocketFrame frame)
  {
    byte[] payload = frame.getPayload();
    if ((this.mPMCE != null) && (frame.getRsv1())) {
      payload = decompress(payload);
    }
    return payload;
  }
  
  private byte[] decompress(byte[] input)
  {
    try
    {
      return this.mPMCE.decompress(input);
    }
    catch (WebSocketException e)
    {
      WebSocketException wse = e;
      
      callOnError(wse);
      callOnMessageDecompressionError(wse, input);
      
      WebSocketFrame frame = WebSocketFrame.createCloseFrame(1003, wse.getMessage());
      
      this.mWebSocket.sendFrame(frame);
    }
    return null;
  }
  
  private boolean handleTextFrame(WebSocketFrame frame)
  {
    callOnTextFrame(frame);
    if (!frame.getFin())
    {
      this.mContinuation.add(frame);
      
      return true;
    }
    byte[] payload = getMessage(frame);
    
    callOnTextMessage(payload);
    
    return true;
  }
  
  private boolean handleBinaryFrame(WebSocketFrame frame)
  {
    callOnBinaryFrame(frame);
    if (!frame.getFin())
    {
      this.mContinuation.add(frame);
      
      return true;
    }
    byte[] payload = getMessage(frame);
    
    callOnBinaryMessage(payload);
    
    return true;
  }
  
  private boolean handleCloseFrame(WebSocketFrame frame)
  {
    StateManager manager = this.mWebSocket.getStateManager();
    
    this.mCloseFrame = frame;
    
    boolean stateChanged = false;
    synchronized (manager)
    {
      WebSocketState state = manager.getState();
      if ((state != WebSocketState.CLOSING) && (state != WebSocketState.CLOSED))
      {
        manager.changeToClosing(StateManager.CloseInitiator.SERVER);
        
        this.mWebSocket.sendFrame(frame);
        
        stateChanged = true;
      }
    }
    if (stateChanged) {
      this.mWebSocket.getListenerManager().callOnStateChanged(WebSocketState.CLOSING);
    }
    callOnCloseFrame(frame);
    
    return false;
  }
  
  private boolean handlePingFrame(WebSocketFrame frame)
  {
    callOnPingFrame(frame);
    
    WebSocketFrame pong = WebSocketFrame.createPongFrame(frame.getPayload());
    
    this.mWebSocket.sendFrame(pong);
    
    return true;
  }
  
  private boolean handlePongFrame(WebSocketFrame frame)
  {
    callOnPongFrame(frame);
    
    return true;
  }
  
  private void waitForCloseFrame()
  {
    if (this.mCloseFrame != null) {
      return;
    }
    WebSocketFrame frame = null;
    
    Timer timer = scheduleInterruptionTimer();
    do
    {
      try
      {
        frame = this.mWebSocket.getInput().readFrame();
      }
      catch (Exception e)
      {
        break;
      }
    } while (!frame.isCloseFrame());
    this.mCloseFrame = frame;
    
    timer.cancel();
  }
  
  private Timer scheduleInterruptionTimer()
  {
    Timer timer = new Timer("ReadingThreadInterruptionTimer");
    
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        if (ReadingThread.this.isAlive()) {
          ReadingThread.this.interrupt();
        }
      }
    }, 60000L);
    
    return timer;
  }
  
  private void notifyFinished()
  {
    this.mWebSocket.onReadingThreadFinished(this.mCloseFrame);
  }
}
