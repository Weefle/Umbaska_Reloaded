package com.mashape.unirest.http;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

class HttpPatchWithBody
  extends HttpEntityEnclosingRequestBase
{
  public static final String METHOD_NAME = "PATCH";
  
  public String getMethod()
  {
    return "PATCH";
  }
  
  public HttpPatchWithBody(String uri)
  {
    setURI(URI.create(uri));
  }
  
  public HttpPatchWithBody(URI uri)
  {
    setURI(uri);
  }
  
  public HttpPatchWithBody() {}
}
