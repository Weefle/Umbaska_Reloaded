package com.mashape.unirest.request;

import com.mashape.unirest.http.HttpMethod;
import java.util.Map;

public class GetRequest
  extends HttpRequest
{
  public GetRequest(HttpMethod method, String url)
  {
    super(method, url);
  }
  
  public GetRequest routeParam(String name, String value)
  {
    super.routeParam(name, value);
    return this;
  }
  
  public GetRequest header(String name, String value)
  {
    return (GetRequest)super.header(name, value);
  }
  
  public GetRequest headers(Map<String, String> headers)
  {
    return (GetRequest)super.headers(headers);
  }
  
  public GetRequest basicAuth(String username, String password)
  {
    super.basicAuth(username, password);
    return this;
  }
}
