package com.neovisionaries.ws.client;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WebSocketExtension
{
  public static final String PERMESSAGE_DEFLATE = "permessage-deflate";
  private final String mName;
  private final Map<String, String> mParameters;
  
  public WebSocketExtension(String name)
  {
    if (!Token.isValid(name)) {
      throw new IllegalArgumentException("'name' is not a valid token.");
    }
    this.mName = name;
    this.mParameters = new LinkedHashMap();
  }
  
  public WebSocketExtension(WebSocketExtension source)
  {
    if (source == null) {
      throw new IllegalArgumentException("'source' is null.");
    }
    this.mName = source.getName();
    this.mParameters = new LinkedHashMap(source.getParameters());
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public Map<String, String> getParameters()
  {
    return this.mParameters;
  }
  
  public boolean containsParameter(String key)
  {
    return this.mParameters.containsKey(key);
  }
  
  public String getParameter(String key)
  {
    return (String)this.mParameters.get(key);
  }
  
  public WebSocketExtension setParameter(String key, String value)
  {
    if (!Token.isValid(key)) {
      throw new IllegalArgumentException("'key' is not a valid token.");
    }
    if (value != null) {
      if (!Token.isValid(value)) {
        throw new IllegalArgumentException("'value' is not a valid token.");
      }
    }
    this.mParameters.put(key, value);
    
    return this;
  }
  
  public String toString()
  {
    StringBuilder builder = new StringBuilder(this.mName);
    for (Map.Entry<String, String> entry : this.mParameters.entrySet())
    {
      builder.append("; ").append((String)entry.getKey());
      
      String value = (String)entry.getValue();
      if ((value != null) && (value.length() != 0)) {
        builder.append("=").append(value);
      }
    }
    return builder.toString();
  }
  
  void validate()
    throws WebSocketException
  {}
  
  public static WebSocketExtension parse(String string)
  {
    if (string == null) {
      return null;
    }
    String[] elements = string.trim().split("\\s*;\\s*");
    if (elements.length == 0) {
      return null;
    }
    String name = elements[0];
    if (!Token.isValid(name)) {
      return null;
    }
    WebSocketExtension extension = createInstance(name);
    for (int i = 1; i < elements.length; i++)
    {
      String[] pair = elements[i].split("\\s*=\\s*", 2);
      if ((pair.length != 0) && (pair[0].length() != 0))
      {
        String key = pair[0];
        if (Token.isValid(key))
        {
          String value = extractValue(pair);
          if ((value == null) || 
          
            (Token.isValid(value))) {
            extension.setParameter(key, value);
          }
        }
      }
    }
    return extension;
  }
  
  private static String extractValue(String[] pair)
  {
    if (pair.length != 2) {
      return null;
    }
    return Token.unquote(pair[1]);
  }
  
  private static WebSocketExtension createInstance(String name)
  {
    if ("permessage-deflate".equals(name)) {
      return new PerMessageDeflateExtension(name);
    }
    return new WebSocketExtension(name);
  }
}
