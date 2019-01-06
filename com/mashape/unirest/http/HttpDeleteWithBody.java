package com.mashape.unirest.http;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

class HttpDeleteWithBody
  extends HttpEntityEnclosingRequestBase
{
  public static final String METHOD_NAME = "DELETE";
  
  public String getMethod()
  {
    return "DELETE";
  }
  
  public HttpDeleteWithBody(String uri)
  {
    setURI(URI.create(uri));
  }
  
  public HttpDeleteWithBody(URI uri)
  {
    setURI(uri);
  }
  
  public HttpDeleteWithBody() {}
}
