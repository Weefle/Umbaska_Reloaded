package com.neovisionaries.ws.client;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class SocketConnector
{
  private Socket mSocket;
  private final Address mAddress;
  private final int mConnectionTimeout;
  private final ProxyHandshaker mProxyHandshaker;
  private final SSLSocketFactory mSSLSocketFactory;
  private final String mHost;
  private final int mPort;
  
  SocketConnector(Socket socket, Address address, int timeout)
  {
    this(socket, address, timeout, null, null, null, 0);
  }
  
  SocketConnector(Socket socket, Address address, int timeout, ProxyHandshaker handshaker, SSLSocketFactory sslSocketFactory, String host, int port)
  {
    this.mSocket = socket;
    this.mAddress = address;
    this.mConnectionTimeout = timeout;
    this.mProxyHandshaker = handshaker;
    this.mSSLSocketFactory = sslSocketFactory;
    this.mHost = host;
    this.mPort = port;
  }
  
  public Socket getSocket()
  {
    return this.mSocket;
  }
  
  public int getConnectionTimeout()
  {
    return this.mConnectionTimeout;
  }
  
  public void connect()
    throws WebSocketException
  {
    try
    {
      doConnect();
    }
    catch (WebSocketException e)
    {
      try
      {
        this.mSocket.close();
      }
      catch (IOException ioe) {}
      throw e;
    }
  }
  
  private void doConnect()
    throws WebSocketException
  {
    boolean proxied = this.mProxyHandshaker != null;
    try
    {
      this.mSocket.connect(this.mAddress.toInetSocketAddress(), this.mConnectionTimeout);
    }
    catch (IOException e)
    {
      String message = String.format("Failed to connect to %s'%s': %s", new Object[] { proxied ? "the proxy " : "", this.mAddress, e
        .getMessage() });
      
      throw new WebSocketException(WebSocketError.SOCKET_CONNECT_ERROR, message, e);
    }
    if (proxied) {
      handshake();
    }
  }
  
  private void handshake()
    throws WebSocketException
  {
    try
    {
      this.mProxyHandshaker.perform();
    }
    catch (IOException e)
    {
      String message = String.format("Handshake with the proxy server (%s) failed: %s", new Object[] { this.mAddress, e
        .getMessage() });
      
      throw new WebSocketException(WebSocketError.PROXY_HANDSHAKE_ERROR, message, e);
    }
    if (this.mSSLSocketFactory == null) {
      return;
    }
    try
    {
      this.mSocket = this.mSSLSocketFactory.createSocket(this.mSocket, this.mHost, this.mPort, true);
    }
    catch (IOException e)
    {
      String message = "Failed to overlay an existing socket: " + e.getMessage();
      
      throw new WebSocketException(WebSocketError.SOCKET_OVERLAY_ERROR, message, e);
    }
    try
    {
      ((SSLSocket)this.mSocket).startHandshake();
    }
    catch (IOException e)
    {
      String message = String.format("SSL handshake with the WebSocket endpoint (%s) failed: %s", new Object[] { this.mAddress, e
        .getMessage() });
      
      throw new WebSocketException(WebSocketError.SSL_HANDSHAKE_ERROR, message, e);
    }
  }
}
