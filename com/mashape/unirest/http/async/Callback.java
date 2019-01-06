package com.mashape.unirest.http.async;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract interface Callback<T>
{
  public abstract void completed(HttpResponse<T> paramHttpResponse);
  
  public abstract void failed(UnirestException paramUnirestException);
  
  public abstract void cancelled();
}
