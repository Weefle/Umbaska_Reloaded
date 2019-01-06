package com.neovisionaries.ws.client;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class WebSocketFactory
{
  private final SocketFactorySettings mSocketFactorySettings;
  private final ProxySettings mProxySettings;
  private int mConnectionTimeout;
  
  public WebSocketFactory()
  {
    this.mSocketFactorySettings = new SocketFactorySettings();
    this.mProxySettings = new ProxySettings(this);
  }
  
  public SocketFactory getSocketFactory()
  {
    return this.mSocketFactorySettings.getSocketFactory();
  }
  
  public WebSocketFactory setSocketFactory(SocketFactory factory)
  {
    this.mSocketFactorySettings.setSocketFactory(factory);
    
    return this;
  }
  
  public SSLSocketFactory getSSLSocketFactory()
  {
    return this.mSocketFactorySettings.getSSLSocketFactory();
  }
  
  public WebSocketFactory setSSLSocketFactory(SSLSocketFactory factory)
  {
    this.mSocketFactorySettings.setSSLSocketFactory(factory);
    
    return this;
  }
  
  public SSLContext getSSLContext()
  {
    return this.mSocketFactorySettings.getSSLContext();
  }
  
  public WebSocketFactory setSSLContext(SSLContext context)
  {
    this.mSocketFactorySettings.setSSLContext(context);
    
    return this;
  }
  
  public ProxySettings getProxySettings()
  {
    return this.mProxySettings;
  }
  
  public int getConnectionTimeout()
  {
    return this.mConnectionTimeout;
  }
  
  public WebSocketFactory setConnectionTimeout(int timeout)
  {
    if (timeout < 0) {
      throw new IllegalArgumentException("timeout value cannot be negative.");
    }
    this.mConnectionTimeout = timeout;
    
    return this;
  }
  
  public WebSocket createSocket(String uri)
    throws IOException
  {
    return createSocket(uri, getConnectionTimeout());
  }
  
  public WebSocket createSocket(String uri, int timeout)
    throws IOException
  {
    if (uri == null) {
      throw new IllegalArgumentException("The given URI is null.");
    }
    if (timeout < 0) {
      throw new IllegalArgumentException("The given timeout value is negative.");
    }
    return createSocket(URI.create(uri), timeout);
  }
  
  public WebSocket createSocket(URL url)
    throws IOException
  {
    return createSocket(url, getConnectionTimeout());
  }
  
  public WebSocket createSocket(URL url, int timeout)
    throws IOException
  {
    if (url == null) {
      throw new IllegalArgumentException("The given URL is null.");
    }
    if (timeout < 0) {
      throw new IllegalArgumentException("The given timeout value is negative.");
    }
    try
    {
      return createSocket(url.toURI(), timeout);
    }
    catch (URISyntaxException e)
    {
      throw new IllegalArgumentException("Failed to convert the given URL into a URI.");
    }
  }
  
  public WebSocket createSocket(URI uri)
    throws IOException
  {
    return createSocket(uri, getConnectionTimeout());
  }
  
  public WebSocket createSocket(URI uri, int timeout)
    throws IOException
  {
    if (uri == null) {
      throw new IllegalArgumentException("The given URI is null.");
    }
    if (timeout < 0) {
      throw new IllegalArgumentException("The given timeout value is negative.");
    }
    String scheme = uri.getScheme();
    String userInfo = uri.getUserInfo();
    String host = uri.getHost();
    int port = uri.getPort();
    String path = uri.getRawPath();
    String query = uri.getRawQuery();
    
    return createSocket(scheme, userInfo, host, port, path, query, timeout);
  }
  
  private WebSocket createSocket(String scheme, String userInfo, String host, int port, String path, String query, int timeout)
    throws IOException
  {
    boolean secure = isSecureConnectionRequired(scheme);
    if ((host == null) || (host.length() == 0)) {
      throw new IllegalArgumentException("The host part is empty.");
    }
    path = determinePath(path);
    
    SocketConnector connector = createRawSocket(host, port, secure, timeout);
    
    return createWebSocket(secure, userInfo, host, port, path, query, connector);
  }
  
  private static boolean isSecureConnectionRequired(String scheme)
  {
    if ((scheme == null) || (scheme.length() == 0)) {
      throw new IllegalArgumentException("The scheme part is empty.");
    }
    if (("wss".equalsIgnoreCase(scheme)) || ("https".equalsIgnoreCase(scheme))) {
      return true;
    }
    if (("ws".equalsIgnoreCase(scheme)) || ("http".equalsIgnoreCase(scheme))) {
      return false;
    }
    throw new IllegalArgumentException("Bad scheme: " + scheme);
  }
  
  private static String determinePath(String path)
  {
    if ((path == null) || (path.length() == 0)) {
      return "/";
    }
    if (path.startsWith("/")) {
      return path;
    }
    return "/" + path;
  }
  
  private SocketConnector createRawSocket(String host, int port, boolean secure, int timeout)
    throws IOException
  {
    port = determinePort(port, secure);
    
    boolean proxied = this.mProxySettings.getHost() != null;
    if (proxied) {
      return createProxiedRawSocket(host, port, secure, timeout);
    }
    return createDirectRawSocket(host, port, secure, timeout);
  }
  
  private SocketConnector createProxiedRawSocket(String host, int port, boolean secure, int timeout)
    throws IOException
  {
    int proxyPort = determinePort(this.mProxySettings.getPort(), this.mProxySettings.isSecure());
    
    SocketFactory socketFactory = this.mProxySettings.selectSocketFactory();
    
    Socket socket = socketFactory.createSocket();
    
    Address address = new Address(this.mProxySettings.getHost(), proxyPort);
    
    ProxyHandshaker handshaker = new ProxyHandshaker(socket, host, port, this.mProxySettings);
    
    SSLSocketFactory sslSocketFactory = secure ? (SSLSocketFactory)this.mSocketFactorySettings.selectSocketFactory(secure) : null;
    
    return new SocketConnector(socket, address, timeout, handshaker, sslSocketFactory, host, port);
  }
  
  private SocketConnector createDirectRawSocket(String host, int port, boolean secure, int timeout)
    throws IOException
  {
    SocketFactory factory = this.mSocketFactorySettings.selectSocketFactory(secure);
    
    Socket socket = factory.createSocket();
    
    Address address = new Address(host, port);
    
    return new SocketConnector(socket, address, timeout);
  }
  
  private static int determinePort(int port, boolean secure)
  {
    if (0 <= port) {
      return port;
    }
    if (secure) {
      return 443;
    }
    return 80;
  }
  
  private WebSocket createWebSocket(boolean secure, String userInfo, String host, int port, String path, String query, SocketConnector connector)
  {
    if (0 <= port) {
      host = host + ":" + port;
    }
    if (query != null) {
      path = path + "?" + query;
    }
    return new WebSocket(this, secure, userInfo, host, path, connector);
  }
}
