package de.btobastian.javacord.exceptions;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class BadResponseException
  extends Exception
{
  private final int status;
  private final String statusText;
  private final HttpResponse<JsonNode> response;
  
  public BadResponseException(String message, int status, String statusText, HttpResponse<JsonNode> response)
  {
    super(message);
    this.status = status;
    this.statusText = statusText;
    this.response = response;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public String getStatusText()
  {
    return this.statusText;
  }
  
  public HttpResponse<JsonNode> getResponse()
  {
    return this.response;
  }
}
