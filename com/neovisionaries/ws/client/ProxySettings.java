package com.neovisionaries.ws.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class ProxySettings
{
  private final WebSocketFactory mWebSocketFactory;
  private final Map<String, List<String>> mHeaders;
  private final SocketFactorySettings mSocketFactorySettings;
  private boolean mSecure;
  private String mHost;
  private int mPort;
  private String mId;
  private String mPassword;
  
  ProxySettings(WebSocketFactory factory)
  {
    this.mWebSocketFactory = factory;
    this.mHeaders = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    this.mSocketFactorySettings = new SocketFactorySettings();
    
    reset();
  }
  
  public WebSocketFactory getWebSocketFactory()
  {
    return this.mWebSocketFactory;
  }
  
  public ProxySettings reset()
  {
    this.mSecure = false;
    this.mHost = null;
    this.mPort = -1;
    this.mId = null;
    this.mPassword = null;
    this.mHeaders.clear();
    
    return this;
  }
  
  public boolean isSecure()
  {
    return this.mSecure;
  }
  
  public ProxySettings setSecure(boolean secure)
  {
    this.mSecure = secure;
    
    return this;
  }
  
  public String getHost()
  {
    return this.mHost;
  }
  
  public ProxySettings setHost(String host)
  {
    this.mHost = host;
    
    return this;
  }
  
  public int getPort()
  {
    return this.mPort;
  }
  
  public ProxySettings setPort(int port)
  {
    this.mPort = port;
    
    return this;
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public ProxySettings setId(String id)
  {
    this.mId = id;
    
    return this;
  }
  
  public String getPassword()
  {
    return this.mPassword;
  }
  
  public ProxySettings setPassword(String password)
  {
    this.mPassword = password;
    
    return this;
  }
  
  public ProxySettings setCredentials(String id, String password)
  {
    return setId(id).setPassword(password);
  }
  
  public ProxySettings setServer(String uri)
  {
    if (uri == null) {
      return this;
    }
    return setServer(URI.create(uri));
  }
  
  public ProxySettings setServer(URL url)
  {
    if (url == null) {
      return this;
    }
    try
    {
      return setServer(url.toURI());
    }
    catch (URISyntaxException e)
    {
      throw new IllegalArgumentException(e);
    }
  }
  
  public ProxySettings setServer(URI uri)
  {
    if (uri == null) {
      return this;
    }
    String scheme = uri.getScheme();
    String userInfo = uri.getUserInfo();
    String host = uri.getHost();
    int port = uri.getPort();
    
    return setServer(scheme, userInfo, host, port);
  }
  
  private ProxySettings setServer(String scheme, String userInfo, String host, int port)
  {
    setByScheme(scheme);
    setByUserInfo(userInfo);
    this.mHost = host;
    this.mPort = port;
    
    return this;
  }
  
  private void setByScheme(String scheme)
  {
    if ("http".equalsIgnoreCase(scheme)) {
      this.mSecure = false;
    } else if ("https".equalsIgnoreCase(scheme)) {
      this.mSecure = true;
    }
  }
  
  private void setByUserInfo(String userInfo)
  {
    if (userInfo == null) {
      return;
    }
    String[] pair = userInfo.split(":", 2);
    String pw;
    String pw;
    switch (pair.length)
    {
    case 2: 
      String id = pair[0];
      pw = pair[1];
      break;
    case 1: 
      String id = pair[0];
      pw = null;
      break;
    default: 
      return;
    }
    String pw;
    String id;
    if (id.length() == 0) {
      return;
    }
    this.mId = id;
    this.mPassword = pw;
  }
  
  public Map<String, List<String>> getHeaders()
  {
    return this.mHeaders;
  }
  
  public ProxySettings addHeader(String name, String value)
  {
    if ((name == null) || (name.length() == 0)) {
      return this;
    }
    List<String> list = (List)this.mHeaders.get(name);
    if (list == null)
    {
      list = new ArrayList();
      this.mHeaders.put(name, list);
    }
    list.add(value);
    
    return this;
  }
  
  public SocketFactory getSocketFactory()
  {
    return this.mSocketFactorySettings.getSocketFactory();
  }
  
  public ProxySettings setSocketFactory(SocketFactory factory)
  {
    this.mSocketFactorySettings.setSocketFactory(factory);
    
    return this;
  }
  
  public SSLSocketFactory getSSLSocketFactory()
  {
    return this.mSocketFactorySettings.getSSLSocketFactory();
  }
  
  public ProxySettings setSSLSocketFactory(SSLSocketFactory factory)
  {
    this.mSocketFactorySettings.setSSLSocketFactory(factory);
    
    return this;
  }
  
  public SSLContext getSSLContext()
  {
    return this.mSocketFactorySettings.getSSLContext();
  }
  
  public ProxySettings setSSLContext(SSLContext context)
  {
    this.mSocketFactorySettings.setSSLContext(context);
    
    return this;
  }
  
  SocketFactory selectSocketFactory()
  {
    return this.mSocketFactorySettings.selectSocketFactory(this.mSecure);
  }
}
