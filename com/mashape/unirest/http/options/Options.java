package com.mashape.unirest.http.options;

import com.mashape.unirest.http.async.utils.AsyncIdleConnectionMonitorThread;
import com.mashape.unirest.http.utils.SyncIdleConnectionMonitorThread;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

public class Options
{
  public static final long CONNECTION_TIMEOUT = 10000L;
  private static final long SOCKET_TIMEOUT = 60000L;
  public static final int MAX_TOTAL = 200;
  public static final int MAX_PER_ROUTE = 20;
  private static Map<Option, Object> options = new HashMap();
  private static boolean customClientSet = false;
  
  public static void customClientSet()
  {
    customClientSet = true;
  }
  
  public static void setOption(Option option, Object value)
  {
    if (((option == Option.CONNECTION_TIMEOUT) || (option == Option.SOCKET_TIMEOUT)) && (customClientSet)) {
      throw new RuntimeException("You can't set custom timeouts when providing custom client implementations. Set the timeouts directly in your custom client configuration instead.");
    }
    options.put(option, value);
  }
  
  public static Object getOption(Option option)
  {
    return options.get(option);
  }
  
  static
  {
    refresh();
  }
  
  public static void refresh()
  {
    Object connectionTimeout = getOption(Option.CONNECTION_TIMEOUT);
    if (connectionTimeout == null) {
      connectionTimeout = Long.valueOf(10000L);
    }
    Object socketTimeout = getOption(Option.SOCKET_TIMEOUT);
    if (socketTimeout == null) {
      socketTimeout = Long.valueOf(60000L);
    }
    Object maxTotal = getOption(Option.MAX_TOTAL);
    if (maxTotal == null) {
      maxTotal = Integer.valueOf(200);
    }
    Object maxPerRoute = getOption(Option.MAX_PER_ROUTE);
    if (maxPerRoute == null) {
      maxPerRoute = Integer.valueOf(20);
    }
    HttpHost proxy = (HttpHost)getOption(Option.PROXY);
    
    RequestConfig clientConfig = RequestConfig.custom().setConnectTimeout(((Long)connectionTimeout).intValue()).setSocketTimeout(((Long)socketTimeout).intValue()).setConnectionRequestTimeout(((Long)socketTimeout).intValue()).setProxy(proxy).build();
    
    PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
    syncConnectionManager.setMaxTotal(((Integer)maxTotal).intValue());
    syncConnectionManager.setDefaultMaxPerRoute(((Integer)maxPerRoute).intValue());
    
    setOption(Option.HTTPCLIENT, HttpClientBuilder.create().setDefaultRequestConfig(clientConfig).setConnectionManager(syncConnectionManager).build());
    SyncIdleConnectionMonitorThread syncIdleConnectionMonitorThread = new SyncIdleConnectionMonitorThread(syncConnectionManager);
    setOption(Option.SYNC_MONITOR, syncIdleConnectionMonitorThread);
    syncIdleConnectionMonitorThread.start();
    try
    {
      DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor();
      PoolingNHttpClientConnectionManager asyncConnectionManager = new PoolingNHttpClientConnectionManager(ioreactor);
      asyncConnectionManager.setMaxTotal(((Integer)maxTotal).intValue());
      asyncConnectionManager.setDefaultMaxPerRoute(((Integer)maxPerRoute).intValue());
    }
    catch (IOReactorException e)
    {
      throw new RuntimeException(e);
    }
    PoolingNHttpClientConnectionManager asyncConnectionManager;
    DefaultConnectingIOReactor ioreactor;
    CloseableHttpAsyncClient asyncClient = HttpAsyncClientBuilder.create().setDefaultRequestConfig(clientConfig).setConnectionManager(asyncConnectionManager).build();
    setOption(Option.ASYNCHTTPCLIENT, asyncClient);
    setOption(Option.ASYNC_MONITOR, new AsyncIdleConnectionMonitorThread(asyncConnectionManager));
  }
}
