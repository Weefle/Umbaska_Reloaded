package com.mashape.unirest.request.body;

import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

public class RawBody
  extends BaseRequest
  implements Body
{
  private byte[] body;
  
  public RawBody(HttpRequest httpRequest)
  {
    super(httpRequest);
  }
  
  public RawBody body(byte[] body)
  {
    this.body = body;
    return this;
  }
  
  public Object getBody()
  {
    return this.body;
  }
  
  public HttpEntity getEntity()
  {
    return new ByteArrayEntity(this.body);
  }
}
