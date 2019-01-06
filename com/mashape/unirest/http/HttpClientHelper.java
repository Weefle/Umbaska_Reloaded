package com.mashape.unirest.http;

import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.async.utils.AsyncIdleConnectionMonitorThread;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.http.utils.ClientFactory;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.Body;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.nio.entity.NByteArrayEntity;

public class HttpClientHelper
{
  private static final String CONTENT_TYPE = "content-type";
  private static final String ACCEPT_ENCODING_HEADER = "accept-encoding";
  private static final String USER_AGENT_HEADER = "user-agent";
  private static final String USER_AGENT = "unirest-java/1.3.11";
  
  private static <T> FutureCallback<org.apache.http.HttpResponse> prepareCallback(final Class<T> responseClass, Callback<T> callback)
  {
    if (callback == null) {
      return null;
    }
    new FutureCallback()
    {
      public void cancelled()
      {
        this.val$callback.cancelled();
      }
      
      public void completed(org.apache.http.HttpResponse arg0)
      {
        this.val$callback.completed(new HttpResponse(arg0, responseClass));
      }
      
      public void failed(Exception arg0)
      {
        this.val$callback.failed(new UnirestException(arg0));
      }
    };
  }
  
  public static <T> Future<HttpResponse<T>> requestAsync(HttpRequest request, final Class<T> responseClass, Callback<T> callback)
  {
    HttpUriRequest requestObj = prepareRequest(request, true);
    
    CloseableHttpAsyncClient asyncHttpClient = ClientFactory.getAsyncHttpClient();
    if (!asyncHttpClient.isRunning())
    {
      asyncHttpClient.start();
      AsyncIdleConnectionMonitorThread asyncIdleConnectionMonitorThread = (AsyncIdleConnectionMonitorThread)Options.getOption(Option.ASYNC_MONITOR);
      asyncIdleConnectionMonitorThread.start();
    }
    Future<org.apache.http.HttpResponse> future = asyncHttpClient.execute(requestObj, prepareCallback(responseClass, callback));
    
    new Future()
    {
      public boolean cancel(boolean mayInterruptIfRunning)
      {
        return this.val$future.cancel(mayInterruptIfRunning);
      }
      
      public boolean isCancelled()
      {
        return this.val$future.isCancelled();
      }
      
      public boolean isDone()
      {
        return this.val$future.isDone();
      }
      
      public HttpResponse<T> get()
        throws InterruptedException, ExecutionException
      {
        org.apache.http.HttpResponse httpResponse = (org.apache.http.HttpResponse)this.val$future.get();
        return new HttpResponse(httpResponse, responseClass);
      }
      
      public HttpResponse<T> get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException
      {
        org.apache.http.HttpResponse httpResponse = (org.apache.http.HttpResponse)this.val$future.get(timeout, unit);
        return new HttpResponse(httpResponse, responseClass);
      }
    };
  }
  
  public static <T> HttpResponse<T> request(HttpRequest request, Class<T> responseClass)
    throws UnirestException
  {
    HttpRequestBase requestObj = prepareRequest(request, false);
    HttpClient client = ClientFactory.getHttpClient();
    try
    {
      org.apache.http.HttpResponse response = client.execute(requestObj);
      HttpResponse<T> httpResponse = new HttpResponse(response, responseClass);
      requestObj.releaseConnection();
      return httpResponse;
    }
    catch (Exception e)
    {
      throw new UnirestException(e);
    }
    finally
    {
      requestObj.releaseConnection();
    }
  }
  
  private static HttpRequestBase prepareRequest(HttpRequest request, boolean async)
  {
    Object defaultHeaders = Options.getOption(Option.DEFAULT_HEADERS);
    if (defaultHeaders != null)
    {
      Set<Map.Entry<String, String>> entrySet = ((Map)defaultHeaders).entrySet();
      for (Map.Entry<String, String> entry : entrySet) {
        request.header((String)entry.getKey(), (String)entry.getValue());
      }
    }
    if (!request.getHeaders().containsKey("user-agent")) {
      request.header("user-agent", "unirest-java/1.3.11");
    }
    if (!request.getHeaders().containsKey("accept-encoding")) {
      request.header("accept-encoding", "gzip");
    }
    HttpRequestBase reqObj = null;
    
    String urlToRequest = null;
    try
    {
      URL url = new URL(request.getUrl());
      uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), URLDecoder.decode(url.getPath(), "UTF-8"), "", url.getRef());
      urlToRequest = uri.toURL().toString();
      if ((url.getQuery() != null) && (!url.getQuery().trim().equals("")))
      {
        if (!urlToRequest.substring(urlToRequest.length() - 1).equals("?")) {
          urlToRequest = urlToRequest + "?";
        }
        urlToRequest = urlToRequest + url.getQuery();
      }
      else if (urlToRequest.substring(urlToRequest.length() - 1).equals("?"))
      {
        urlToRequest = urlToRequest.substring(0, urlToRequest.length() - 1);
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    switch (request.getHttpMethod())
    {
    case GET: 
      reqObj = new HttpGet(urlToRequest);
      break;
    case POST: 
      reqObj = new HttpPost(urlToRequest);
      break;
    case PUT: 
      reqObj = new HttpPut(urlToRequest);
      break;
    case DELETE: 
      reqObj = new HttpDeleteWithBody(urlToRequest);
      break;
    case PATCH: 
      reqObj = new HttpPatchWithBody(urlToRequest);
      break;
    case OPTIONS: 
      reqObj = new HttpOptions(urlToRequest);
      break;
    case HEAD: 
      reqObj = new HttpHead(urlToRequest);
    }
    Set<Map.Entry<String, List<String>>> entrySet = request.getHeaders().entrySet();
    for (URI uri = entrySet.iterator(); uri.hasNext();)
    {
      entry = (Map.Entry)uri.next();
      List<String> values = (List)entry.getValue();
      if (values != null) {
        for (String value : values) {
          reqObj.addHeader((String)entry.getKey(), value);
        }
      }
    }
    Map.Entry<String, List<String>> entry;
    if ((request.getHttpMethod() != HttpMethod.GET) && (request.getHttpMethod() != HttpMethod.HEAD) && 
      (request.getBody() != null))
    {
      HttpEntity entity = request.getBody().getEntity();
      if (async)
      {
        if ((reqObj.getHeaders("content-type") == null) || (reqObj.getHeaders("content-type").length == 0)) {
          reqObj.setHeader(entity.getContentType());
        }
        try
        {
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          entity.writeTo(output);
          NByteArrayEntity en = new NByteArrayEntity(output.toByteArray());
          ((HttpEntityEnclosingRequestBase)reqObj).setEntity(en);
        }
        catch (IOException e)
        {
          throw new RuntimeException(e);
        }
      }
      else
      {
        ((HttpEntityEnclosingRequestBase)reqObj).setEntity(entity);
      }
    }
    return reqObj;
  }
}
