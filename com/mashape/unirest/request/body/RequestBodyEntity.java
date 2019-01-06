package com.mashape.unirest.request.body;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

public class RequestBodyEntity
  extends BaseRequest
  implements Body
{
  private Object body;
  
  public RequestBodyEntity(HttpRequest httpRequest)
  {
    super(httpRequest);
  }
  
  public RequestBodyEntity body(String body)
  {
    this.body = body;
    return this;
  }
  
  public RequestBodyEntity body(JsonNode body)
  {
    this.body = body.toString();
    return this;
  }
  
  public Object getBody()
  {
    return this.body;
  }
  
  public HttpEntity getEntity()
  {
    return new StringEntity(this.body.toString(), "UTF-8");
  }
}
