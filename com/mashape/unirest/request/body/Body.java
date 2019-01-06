package com.mashape.unirest.request.body;

import org.apache.http.HttpEntity;

public abstract interface Body
{
  public abstract HttpEntity getEntity();
}
