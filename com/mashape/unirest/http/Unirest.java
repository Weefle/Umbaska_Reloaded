package com.mashape.unirest.http;

import com.mashape.unirest.http.async.utils.AsyncIdleConnectionMonitorThread;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.http.utils.SyncIdleConnectionMonitorThread;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

public class Unirest
{
  public static void setHttpClient(HttpClient httpClient)
  {
    Options.setOption(Option.HTTPCLIENT, httpClient);
    Options.customClientSet();
  }
  
  public static void setAsyncHttpClient(CloseableHttpAsyncClient asyncHttpClient)
  {
    Options.setOption(Option.ASYNCHTTPCLIENT, asyncHttpClient);
    Options.customClientSet();
  }
  
  public static void setProxy(HttpHost proxy)
  {
    Options.setOption(Option.PROXY, proxy);
    
    Options.refresh();
  }
  
  public static void setObjectMapper(ObjectMapper objectMapper)
  {
    Options.setOption(Option.OBJECT_MAPPER, objectMapper);
    
    Options.refresh();
  }
  
  public static void setTimeouts(long connectionTimeout, long socketTimeout)
  {
    Options.setOption(Option.CONNECTION_TIMEOUT, Long.valueOf(connectionTimeout));
    Options.setOption(Option.SOCKET_TIMEOUT, Long.valueOf(socketTimeout));
    
    Options.refresh();
  }
  
  public static void setConcurrency(int maxTotal, int maxPerRoute)
  {
    Options.setOption(Option.MAX_TOTAL, Integer.valueOf(maxTotal));
    Options.setOption(Option.MAX_PER_ROUTE, Integer.valueOf(maxPerRoute));
    
    Options.refresh();
  }
  
  public static void clearDefaultHeaders()
  {
    Options.setOption(Option.DEFAULT_HEADERS, null);
  }
  
  public static void setDefaultHeader(String name, String value)
  {
    Object headers = Options.getOption(Option.DEFAULT_HEADERS);
    if (headers == null) {
      headers = new HashMap();
    }
    ((Map)headers).put(name, value);
    Options.setOption(Option.DEFAULT_HEADERS, headers);
  }
  
  public static void shutdown()
    throws IOException
  {
    CloseableHttpClient syncClient = (CloseableHttpClient)Options.getOption(Option.HTTPCLIENT);
    if (syncClient != null) {
      syncClient.close();
    }
    SyncIdleConnectionMonitorThread syncIdleConnectionMonitorThread = (SyncIdleConnectionMonitorThread)Options.getOption(Option.SYNC_MONITOR);
    if (syncIdleConnectionMonitorThread != null) {
      syncIdleConnectionMonitorThread.interrupt();
    }
    CloseableHttpAsyncClient asyncClient = (CloseableHttpAsyncClient)Options.getOption(Option.ASYNCHTTPCLIENT);
    if ((asyncClient != null) && (asyncClient.isRunning())) {
      asyncClient.close();
    }
    AsyncIdleConnectionMonitorThread asyncMonitorThread = (AsyncIdleConnectionMonitorThread)Options.getOption(Option.ASYNC_MONITOR);
    if (asyncMonitorThread != null) {
      asyncMonitorThread.interrupt();
    }
  }
  
  public static GetRequest get(String url)
  {
    return new GetRequest(HttpMethod.GET, url);
  }
  
  public static GetRequest head(String url)
  {
    return new GetRequest(HttpMethod.HEAD, url);
  }
  
  public static HttpRequestWithBody options(String url)
  {
    return new HttpRequestWithBody(HttpMethod.OPTIONS, url);
  }
  
  public static HttpRequestWithBody post(String url)
  {
    return new HttpRequestWithBody(HttpMethod.POST, url);
  }
  
  public static HttpRequestWithBody delete(String url)
  {
    return new HttpRequestWithBody(HttpMethod.DELETE, url);
  }
  
  public static HttpRequestWithBody patch(String url)
  {
    return new HttpRequestWithBody(HttpMethod.PATCH, url);
  }
  
  public static HttpRequestWithBody put(String url)
  {
    return new HttpRequestWithBody(HttpMethod.PUT, url);
  }
}
