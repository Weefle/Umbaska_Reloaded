package com.mashape.unirest.request;

import com.mashape.unirest.http.HttpClientHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.InputStream;
import java.util.concurrent.Future;

public abstract class BaseRequest
{
  protected static final String UTF_8 = "UTF-8";
  protected HttpRequest httpRequest;
  
  protected BaseRequest(HttpRequest httpRequest)
  {
    this.httpRequest = httpRequest;
  }
  
  public HttpRequest getHttpRequest()
  {
    return this.httpRequest;
  }
  
  protected BaseRequest() {}
  
  public HttpResponse<String> asString()
    throws UnirestException
  {
    return HttpClientHelper.request(this.httpRequest, String.class);
  }
  
  public Future<HttpResponse<String>> asStringAsync()
  {
    return HttpClientHelper.requestAsync(this.httpRequest, String.class, null);
  }
  
  public Future<HttpResponse<String>> asStringAsync(Callback<String> callback)
  {
    return HttpClientHelper.requestAsync(this.httpRequest, String.class, callback);
  }
  
  public HttpResponse<JsonNode> asJson()
    throws UnirestException
  {
    return HttpClientHelper.request(this.httpRequest, JsonNode.class);
  }
  
  public Future<HttpResponse<JsonNode>> asJsonAsync()
  {
    return HttpClientHelper.requestAsync(this.httpRequest, JsonNode.class, null);
  }
  
  public Future<HttpResponse<JsonNode>> asJsonAsync(Callback<JsonNode> callback)
  {
    return HttpClientHelper.requestAsync(this.httpRequest, JsonNode.class, callback);
  }
  
  public <T> HttpResponse<T> asObject(Class<? extends T> responseClass)
    throws UnirestException
  {
    return HttpClientHelper.request(this.httpRequest, responseClass);
  }
  
  public <T> Future<HttpResponse<T>> asObjectAsync(Class<? extends T> responseClass)
  {
    return HttpClientHelper.requestAsync(this.httpRequest, responseClass, null);
  }
  
  public <T> Future<HttpResponse<T>> asObjectAsync(Class<? extends T> responseClass, Callback<T> callback)
  {
    return HttpClientHelper.requestAsync(this.httpRequest, responseClass, callback);
  }
  
  public HttpResponse<InputStream> asBinary()
    throws UnirestException
  {
    return HttpClientHelper.request(this.httpRequest, InputStream.class);
  }
  
  public Future<HttpResponse<InputStream>> asBinaryAsync()
  {
    return HttpClientHelper.requestAsync(this.httpRequest, InputStream.class, null);
  }
  
  public Future<HttpResponse<InputStream>> asBinaryAsync(Callback<InputStream> callback)
  {
    return HttpClientHelper.requestAsync(this.httpRequest, InputStream.class, callback);
  }
}
