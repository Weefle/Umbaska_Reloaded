package com.neovisionaries.ws.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WebSocket
{
  private final WebSocketFactory mWebSocketFactory;
  private final SocketConnector mSocketConnector;
  private final StateManager mStateManager;
  private HandshakeBuilder mHandshakeBuilder;
  private final ListenerManager mListenerManager;
  private final PingSender mPingSender;
  private final PongSender mPongSender;
  private final Object mThreadsLock = new Object();
  private WebSocketInputStream mInput;
  private WebSocketOutputStream mOutput;
  private ReadingThread mReadingThread;
  private WritingThread mWritingThread;
  private Map<String, List<String>> mServerHeaders;
  private List<WebSocketExtension> mAgreedExtensions;
  private String mAgreedProtocol;
  private boolean mExtended;
  private boolean mAutoFlush = true;
  private int mFrameQueueSize;
  private boolean mOnConnectedCalled;
  private boolean mReadingThreadStarted;
  private boolean mWritingThreadStarted;
  private boolean mReadingThreadFinished;
  private boolean mWritingThreadFinished;
  private WebSocketFrame mServerCloseFrame;
  private WebSocketFrame mClientCloseFrame;
  private PerMessageCompressionExtension mPerMessageCompressionExtension;
  
  WebSocket(WebSocketFactory factory, boolean secure, String userInfo, String host, String path, SocketConnector connector)
  {
    this.mWebSocketFactory = factory;
    this.mSocketConnector = connector;
    this.mStateManager = new StateManager();
    this.mHandshakeBuilder = new HandshakeBuilder(secure, userInfo, host, path);
    this.mListenerManager = new ListenerManager(this);
    this.mPingSender = new PingSender(this, new CounterPayloadGenerator());
    this.mPongSender = new PongSender(this, new CounterPayloadGenerator());
  }
  
  public WebSocket recreate()
    throws IOException
  {
    return recreate(this.mSocketConnector.getConnectionTimeout());
  }
  
  public WebSocket recreate(int timeout)
    throws IOException
  {
    if (timeout < 0) {
      throw new IllegalArgumentException("The given timeout value is negative.");
    }
    WebSocket instance = this.mWebSocketFactory.createSocket(getURI(), timeout);
    
    instance.mHandshakeBuilder = new HandshakeBuilder(this.mHandshakeBuilder);
    instance.setPingInterval(getPingInterval());
    instance.setPongInterval(getPongInterval());
    instance.setPingPayloadGenerator(getPingPayloadGenerator());
    instance.setPongPayloadGenerator(getPongPayloadGenerator());
    instance.mExtended = this.mExtended;
    instance.mAutoFlush = this.mAutoFlush;
    instance.mFrameQueueSize = this.mFrameQueueSize;
    
    List<WebSocketListener> listeners = this.mListenerManager.getListeners();
    synchronized (listeners)
    {
      instance.addListeners(listeners);
    }
    return instance;
  }
  
  protected void finalize()
    throws Throwable
  {
    if (isInState(WebSocketState.CREATED)) {
      finish();
    }
    super.finalize();
  }
  
  /* Error */
  public WebSocketState getState()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 10	com/neovisionaries/ws/client/WebSocket:mStateManager	Lcom/neovisionaries/ws/client/StateManager;
    //   4: dup
    //   5: astore_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 10	com/neovisionaries/ws/client/WebSocket:mStateManager	Lcom/neovisionaries/ws/client/StateManager;
    //   11: invokevirtual 48	com/neovisionaries/ws/client/StateManager:getState	()Lcom/neovisionaries/ws/client/WebSocketState;
    //   14: aload_1
    //   15: monitorexit
    //   16: areturn
    //   17: astore_2
    //   18: aload_1
    //   19: monitorexit
    //   20: aload_2
    //   21: athrow
    // Line number table:
    //   Java source line #1042	-> byte code offset #0
    //   Java source line #1044	-> byte code offset #7
    //   Java source line #1045	-> byte code offset #17
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	22	0	this	WebSocket
    //   5	14	1	Ljava/lang/Object;	Object
    //   17	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   7	16	17	finally
    //   17	20	17	finally
  }
  
  public boolean isOpen()
  {
    return isInState(WebSocketState.OPEN);
  }
  
  private boolean isInState(WebSocketState state)
  {
    synchronized (this.mStateManager)
    {
      return this.mStateManager.getState() == state;
    }
  }
  
  public WebSocket addProtocol(String protocol)
  {
    this.mHandshakeBuilder.addProtocol(protocol);
    
    return this;
  }
  
  public WebSocket removeProtocol(String protocol)
  {
    this.mHandshakeBuilder.removeProtocol(protocol);
    
    return this;
  }
  
  public WebSocket clearProtocols()
  {
    this.mHandshakeBuilder.clearProtocols();
    
    return this;
  }
  
  public WebSocket addExtension(WebSocketExtension extension)
  {
    this.mHandshakeBuilder.addExtension(extension);
    
    return this;
  }
  
  public WebSocket addExtension(String extension)
  {
    this.mHandshakeBuilder.addExtension(extension);
    
    return this;
  }
  
  public WebSocket removeExtension(WebSocketExtension extension)
  {
    this.mHandshakeBuilder.removeExtension(extension);
    
    return this;
  }
  
  public WebSocket removeExtensions(String name)
  {
    this.mHandshakeBuilder.removeExtensions(name);
    
    return this;
  }
  
  public WebSocket clearExtensions()
  {
    this.mHandshakeBuilder.clearExtensions();
    
    return this;
  }
  
  public WebSocket addHeader(String name, String value)
  {
    this.mHandshakeBuilder.addHeader(name, value);
    
    return this;
  }
  
  public WebSocket removeHeaders(String name)
  {
    this.mHandshakeBuilder.removeHeaders(name);
    
    return this;
  }
  
  public WebSocket clearHeaders()
  {
    this.mHandshakeBuilder.clearHeaders();
    
    return this;
  }
  
  public WebSocket setUserInfo(String userInfo)
  {
    this.mHandshakeBuilder.setUserInfo(userInfo);
    
    return this;
  }
  
  public WebSocket setUserInfo(String id, String password)
  {
    this.mHandshakeBuilder.setUserInfo(id, password);
    
    return this;
  }
  
  public WebSocket clearUserInfo()
  {
    this.mHandshakeBuilder.clearUserInfo();
    
    return this;
  }
  
  public boolean isExtended()
  {
    return this.mExtended;
  }
  
  public WebSocket setExtended(boolean extended)
  {
    this.mExtended = extended;
    
    return this;
  }
  
  public boolean isAutoFlush()
  {
    return this.mAutoFlush;
  }
  
  public WebSocket setAutoFlush(boolean auto)
  {
    this.mAutoFlush = auto;
    
    return this;
  }
  
  public WebSocket flush()
  {
    synchronized (this.mStateManager)
    {
      WebSocketState state = this.mStateManager.getState();
      if ((state != WebSocketState.OPEN) && (state != WebSocketState.CLOSING)) {
        return this;
      }
    }
    WritingThread wt = this.mWritingThread;
    if (wt != null) {
      wt.queueFlush();
    }
    return this;
  }
  
  public int getFrameQueueSize()
  {
    return this.mFrameQueueSize;
  }
  
  public WebSocket setFrameQueueSize(int size)
    throws IllegalArgumentException
  {
    if (size < 0) {
      throw new IllegalArgumentException("size must not be negative.");
    }
    this.mFrameQueueSize = size;
    
    return this;
  }
  
  public long getPingInterval()
  {
    return this.mPingSender.getInterval();
  }
  
  public WebSocket setPingInterval(long interval)
  {
    this.mPingSender.setInterval(interval);
    
    return this;
  }
  
  public long getPongInterval()
  {
    return this.mPongSender.getInterval();
  }
  
  public WebSocket setPongInterval(long interval)
  {
    this.mPongSender.setInterval(interval);
    
    return this;
  }
  
  public PayloadGenerator getPingPayloadGenerator()
  {
    return this.mPingSender.getPayloadGenerator();
  }
  
  public WebSocket setPingPayloadGenerator(PayloadGenerator generator)
  {
    this.mPingSender.setPayloadGenerator(generator);
    
    return this;
  }
  
  public PayloadGenerator getPongPayloadGenerator()
  {
    return this.mPongSender.getPayloadGenerator();
  }
  
  public WebSocket setPongPayloadGenerator(PayloadGenerator generator)
  {
    this.mPongSender.setPayloadGenerator(generator);
    
    return this;
  }
  
  public WebSocket addListener(WebSocketListener listener)
  {
    this.mListenerManager.addListener(listener);
    
    return this;
  }
  
  public WebSocket addListeners(List<WebSocketListener> listeners)
  {
    this.mListenerManager.addListeners(listeners);
    
    return this;
  }
  
  public WebSocket removeListener(WebSocketListener listener)
  {
    this.mListenerManager.removeListener(listener);
    
    return this;
  }
  
  public WebSocket removeListeners(List<WebSocketListener> listeners)
  {
    this.mListenerManager.removeListeners(listeners);
    
    return this;
  }
  
  public WebSocket clearListeners()
  {
    this.mListenerManager.clearListeners();
    
    return this;
  }
  
  public Socket getSocket()
  {
    return this.mSocketConnector.getSocket();
  }
  
  public URI getURI()
  {
    return this.mHandshakeBuilder.getURI();
  }
  
  public WebSocket connect()
    throws WebSocketException
  {
    changeStateOnConnect();
    try
    {
      this.mSocketConnector.connect();
      
      headers = shakeHands();
    }
    catch (WebSocketException e)
    {
      Map<String, List<String>> headers;
      this.mStateManager.setState(WebSocketState.CLOSED);
      
      this.mListenerManager.callOnStateChanged(WebSocketState.CLOSED);
      
      throw e;
    }
    Map<String, List<String>> headers;
    this.mServerHeaders = headers;
    
    this.mPerMessageCompressionExtension = findAgreedPerMessageCompressionExtension();
    
    this.mStateManager.setState(WebSocketState.OPEN);
    
    this.mListenerManager.callOnStateChanged(WebSocketState.OPEN);
    
    startThreads();
    
    return this;
  }
  
  public Future<WebSocket> connect(ExecutorService executorService)
  {
    return executorService.submit(connectable());
  }
  
  public Callable<WebSocket> connectable()
  {
    return new Connectable(this);
  }
  
  public WebSocket connectAsynchronously()
  {
    new ConnectThread(this).start();
    
    return this;
  }
  
  public WebSocket disconnect()
  {
    return disconnect(1000, null);
  }
  
  public WebSocket disconnect(int closeCode)
  {
    return disconnect(closeCode, null);
  }
  
  public WebSocket disconnect(String reason)
  {
    return disconnect(1000, reason);
  }
  
  public WebSocket disconnect(int closeCode, String reason)
  {
    synchronized (this.mStateManager)
    {
      switch (this.mStateManager.getState())
      {
      case CREATED: 
        finishAsynchronously();
        return this;
      case OPEN: 
        break;
      default: 
        return this;
      }
      this.mStateManager.changeToClosing(StateManager.CloseInitiator.CLIENT);
      
      WebSocketFrame frame = WebSocketFrame.createCloseFrame(closeCode, reason);
      
      sendFrame(frame);
    }
    this.mListenerManager.callOnStateChanged(WebSocketState.CLOSING);
    
    stopThreads();
    
    return this;
  }
  
  public List<WebSocketExtension> getAgreedExtensions()
  {
    return this.mAgreedExtensions;
  }
  
  public String getAgreedProtocol()
  {
    return this.mAgreedProtocol;
  }
  
  public WebSocket sendFrame(WebSocketFrame frame)
  {
    if (frame == null) {
      return this;
    }
    synchronized (this.mStateManager)
    {
      WebSocketState state = this.mStateManager.getState();
      if ((state != WebSocketState.OPEN) && (state != WebSocketState.CLOSING)) {
        return this;
      }
    }
    WritingThread wt = this.mWritingThread;
    if (wt != null) {
      wt.queueFrame(frame);
    }
    return this;
  }
  
  public WebSocket sendContinuation()
  {
    return sendFrame(WebSocketFrame.createContinuationFrame());
  }
  
  public WebSocket sendContinuation(boolean fin)
  {
    return sendFrame(WebSocketFrame.createContinuationFrame().setFin(fin));
  }
  
  public WebSocket sendContinuation(String payload)
  {
    return sendFrame(WebSocketFrame.createContinuationFrame(payload));
  }
  
  public WebSocket sendContinuation(String payload, boolean fin)
  {
    return sendFrame(WebSocketFrame.createContinuationFrame(payload).setFin(fin));
  }
  
  public WebSocket sendContinuation(byte[] payload)
  {
    return sendFrame(WebSocketFrame.createContinuationFrame(payload));
  }
  
  public WebSocket sendContinuation(byte[] payload, boolean fin)
  {
    return sendFrame(WebSocketFrame.createContinuationFrame(payload).setFin(fin));
  }
  
  public WebSocket sendText(String message)
  {
    return sendFrame(WebSocketFrame.createTextFrame(message));
  }
  
  public WebSocket sendText(String payload, boolean fin)
  {
    return sendFrame(WebSocketFrame.createTextFrame(payload).setFin(fin));
  }
  
  public WebSocket sendBinary(byte[] message)
  {
    return sendFrame(WebSocketFrame.createBinaryFrame(message));
  }
  
  public WebSocket sendBinary(byte[] payload, boolean fin)
  {
    return sendFrame(WebSocketFrame.createBinaryFrame(payload).setFin(fin));
  }
  
  public WebSocket sendClose()
  {
    return sendFrame(WebSocketFrame.createCloseFrame());
  }
  
  public WebSocket sendClose(int closeCode)
  {
    return sendFrame(WebSocketFrame.createCloseFrame(closeCode));
  }
  
  public WebSocket sendClose(int closeCode, String reason)
  {
    return sendFrame(WebSocketFrame.createCloseFrame(closeCode, reason));
  }
  
  public WebSocket sendPing()
  {
    return sendFrame(WebSocketFrame.createPingFrame());
  }
  
  public WebSocket sendPing(byte[] payload)
  {
    return sendFrame(WebSocketFrame.createPingFrame(payload));
  }
  
  public WebSocket sendPing(String payload)
  {
    return sendFrame(WebSocketFrame.createPingFrame(payload));
  }
  
  public WebSocket sendPong()
  {
    return sendFrame(WebSocketFrame.createPongFrame());
  }
  
  public WebSocket sendPong(byte[] payload)
  {
    return sendFrame(WebSocketFrame.createPongFrame(payload));
  }
  
  public WebSocket sendPong(String payload)
  {
    return sendFrame(WebSocketFrame.createPongFrame(payload));
  }
  
  private void changeStateOnConnect()
    throws WebSocketException
  {
    synchronized (this.mStateManager)
    {
      if (this.mStateManager.getState() != WebSocketState.CREATED) {
        throw new WebSocketException(WebSocketError.NOT_IN_CREATED_STATE, "The current state of the web socket is not CREATED.");
      }
      this.mStateManager.setState(WebSocketState.CONNECTING);
    }
    this.mListenerManager.callOnStateChanged(WebSocketState.CONNECTING);
  }
  
  private Map<String, List<String>> shakeHands()
    throws WebSocketException
  {
    Socket socket = this.mSocketConnector.getSocket();
    
    WebSocketInputStream input = openInputStream(socket);
    
    WebSocketOutputStream output = openOutputStream(socket);
    
    String key = generateWebSocketKey();
    
    writeHandshake(output, key);
    
    Map<String, List<String>> headers = readHandshake(input, key);
    
    this.mInput = input;
    this.mOutput = output;
    
    return headers;
  }
  
  private WebSocketInputStream openInputStream(Socket socket)
    throws WebSocketException
  {
    try
    {
      return new WebSocketInputStream(new BufferedInputStream(socket.getInputStream()));
    }
    catch (IOException e)
    {
      throw new WebSocketException(WebSocketError.SOCKET_INPUT_STREAM_FAILURE, "Failed to get the input stream of the raw socket: " + e.getMessage(), e);
    }
  }
  
  private WebSocketOutputStream openOutputStream(Socket socket)
    throws WebSocketException
  {
    try
    {
      return new WebSocketOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }
    catch (IOException e)
    {
      throw new WebSocketException(WebSocketError.SOCKET_OUTPUT_STREAM_FAILURE, "Failed to get the output stream from the raw socket: " + e.getMessage(), e);
    }
  }
  
  private static String generateWebSocketKey()
  {
    byte[] data = new byte[16];
    
    Misc.nextBytes(data);
    
    return Base64.encode(data);
  }
  
  private void writeHandshake(WebSocketOutputStream output, String key)
    throws WebSocketException
  {
    this.mHandshakeBuilder.setKey(key);
    String requestLine = this.mHandshakeBuilder.buildRequestLine();
    List<String[]> headers = this.mHandshakeBuilder.buildHeaders();
    String handshake = HandshakeBuilder.build(requestLine, headers);
    
    this.mListenerManager.callOnSendingHandshake(requestLine, headers);
    try
    {
      output.write(handshake);
      output.flush();
    }
    catch (IOException e)
    {
      throw new WebSocketException(WebSocketError.OPENING_HAHDSHAKE_REQUEST_FAILURE, "Failed to send an opening handshake request to the server: " + e.getMessage(), e);
    }
  }
  
  private Map<String, List<String>> readHandshake(WebSocketInputStream input, String key)
    throws WebSocketException
  {
    return new HandshakeReader(this).readHandshake(input, key);
  }
  
  private void startThreads()
  {
    ReadingThread readingThread = new ReadingThread(this);
    WritingThread writingThread = new WritingThread(this);
    synchronized (this.mThreadsLock)
    {
      this.mReadingThread = readingThread;
      this.mWritingThread = writingThread;
    }
    readingThread.start();
    writingThread.start();
  }
  
  private void stopThreads()
  {
    ReadingThread readingThread;
    WritingThread writingThread;
    synchronized (this.mThreadsLock)
    {
      readingThread = this.mReadingThread;
      writingThread = this.mWritingThread;
      
      this.mReadingThread = null;
      this.mWritingThread = null;
    }
    if (readingThread != null) {
      readingThread.requestStop();
    }
    if (writingThread != null) {
      writingThread.requestStop();
    }
  }
  
  WebSocketInputStream getInput()
  {
    return this.mInput;
  }
  
  WebSocketOutputStream getOutput()
  {
    return this.mOutput;
  }
  
  StateManager getStateManager()
  {
    return this.mStateManager;
  }
  
  ListenerManager getListenerManager()
  {
    return this.mListenerManager;
  }
  
  HandshakeBuilder getHandshakeBuilder()
  {
    return this.mHandshakeBuilder;
  }
  
  void setAgreedExtensions(List<WebSocketExtension> extensions)
  {
    this.mAgreedExtensions = extensions;
  }
  
  void setAgreedProtocol(String protocol)
  {
    this.mAgreedProtocol = protocol;
  }
  
  void onReadingThreadStarted()
  {
    synchronized (this.mThreadsLock)
    {
      this.mReadingThreadStarted = true;
      
      callOnConnectedIfNotYet();
      if (!this.mWritingThreadStarted) {
        return;
      }
    }
    onThreadsStarted();
  }
  
  void onWritingThreadStarted()
  {
    synchronized (this.mThreadsLock)
    {
      this.mWritingThreadStarted = true;
      
      callOnConnectedIfNotYet();
      if (!this.mReadingThreadStarted) {
        return;
      }
    }
    onThreadsStarted();
  }
  
  private void callOnConnectedIfNotYet()
  {
    if (this.mOnConnectedCalled) {
      return;
    }
    this.mListenerManager.callOnConnected(this.mServerHeaders);
    
    this.mOnConnectedCalled = true;
  }
  
  private void onThreadsStarted()
  {
    this.mPingSender.start();
    
    this.mPongSender.start();
  }
  
  void onReadingThreadFinished(WebSocketFrame closeFrame)
  {
    synchronized (this.mThreadsLock)
    {
      this.mReadingThreadFinished = true;
      this.mServerCloseFrame = closeFrame;
      if (!this.mWritingThreadFinished) {
        return;
      }
    }
    onThreadsFinished();
  }
  
  void onWritingThreadFinished(WebSocketFrame closeFrame)
  {
    synchronized (this.mThreadsLock)
    {
      this.mWritingThreadFinished = true;
      this.mClientCloseFrame = closeFrame;
      if (!this.mReadingThreadFinished) {
        return;
      }
    }
    onThreadsFinished();
  }
  
  private void onThreadsFinished()
  {
    finish();
  }
  
  private void finish()
  {
    this.mPingSender.stop();
    this.mPongSender.stop();
    try
    {
      this.mSocketConnector.getSocket().close();
    }
    catch (Throwable t) {}
    synchronized (this.mStateManager)
    {
      this.mStateManager.setState(WebSocketState.CLOSED);
    }
    this.mListenerManager.callOnStateChanged(WebSocketState.CLOSED);
    
    this.mListenerManager.callOnDisconnected(this.mServerCloseFrame, this.mClientCloseFrame, this.mStateManager
      .getClosedByServer());
  }
  
  private void finishAsynchronously()
  {
    new Thread()
    {
      public void run()
      {
        WebSocket.this.finish();
      }
    }.start();
  }
  
  private PerMessageCompressionExtension findAgreedPerMessageCompressionExtension()
  {
    if (this.mAgreedExtensions == null) {
      return null;
    }
    for (WebSocketExtension extension : this.mAgreedExtensions) {
      if ((extension instanceof PerMessageCompressionExtension)) {
        return (PerMessageCompressionExtension)extension;
      }
    }
    return null;
  }
  
  PerMessageCompressionExtension getPerMessageCompressionExtension()
  {
    return this.mPerMessageCompressionExtension;
  }
}
