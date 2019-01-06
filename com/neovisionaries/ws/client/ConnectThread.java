package com.neovisionaries.ws.client;

class ConnectThread
  extends Thread
{
  private final WebSocket mWebSocket;
  
  public ConnectThread(WebSocket ws)
  {
    super("ConnectThread");
    
    this.mWebSocket = ws;
  }
  
  public void run()
  {
    try
    {
      this.mWebSocket.connect();
    }
    catch (WebSocketException e)
    {
      handleError(e);
    }
  }
  
  private void handleError(WebSocketException cause)
  {
    ListenerManager manager = this.mWebSocket.getListenerManager();
    
    manager.callOnError(cause);
    manager.callOnConnectError(cause);
  }
}
