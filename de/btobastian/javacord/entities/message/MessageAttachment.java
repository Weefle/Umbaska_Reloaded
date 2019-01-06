package de.btobastian.javacord.entities.message;

import java.net.URL;

public abstract interface MessageAttachment
{
  public abstract URL getUrl();
  
  public abstract URL getProxyUrl();
  
  public abstract int getSize();
  
  public abstract String getId();
  
  public abstract String getFileName();
}
