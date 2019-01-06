package com.mashape.unirest.http;

import java.util.HashMap;
import java.util.List;

public class Headers
  extends HashMap<String, List<String>>
{
  private static final long serialVersionUID = 71310341388734766L;
  
  public String getFirst(Object key)
  {
    List<String> list = (List)get(key);
    if ((list != null) && (list.size() > 0)) {
      return (String)list.get(0);
    }
    return null;
  }
}
