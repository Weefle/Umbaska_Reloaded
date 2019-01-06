package com.mashape.unirest.http;

public abstract interface ObjectMapper
{
  public abstract <T> T readValue(String paramString, Class<T> paramClass);
  
  public abstract String writeValue(Object paramObject);
}
