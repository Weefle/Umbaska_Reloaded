package de.btobastian.javacord.entities.message.impl;

import de.btobastian.javacord.entities.message.MessageAttachment;
import de.btobastian.javacord.utils.LoggerUtil;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;

public class ImplMessageAttachment
  implements MessageAttachment
{
  private static final Logger logger = LoggerUtil.getLogger(ImplMessageAttachment.class);
  private final String url;
  private final String proxyUrl;
  private final int size;
  private final String id;
  private final String name;
  
  public ImplMessageAttachment(String url, String proxyUrl, int size, String id, String name)
  {
    this.url = url;
    this.proxyUrl = proxyUrl;
    this.size = size;
    this.id = id;
    this.name = name;
  }
  
  public URL getUrl()
  {
    try
    {
      return this.url == null ? null : new URL(this.url);
    }
    catch (MalformedURLException e)
    {
      logger.warn("Malformed url of message attachment! Please contact the developer!", e);
    }
    return null;
  }
  
  public URL getProxyUrl()
  {
    try
    {
      return this.proxyUrl == null ? null : new URL(this.proxyUrl);
    }
    catch (MalformedURLException e)
    {
      logger.warn("Malformed proxy url of message attachment! Please contact the developer!", e);
    }
    return null;
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getFileName()
  {
    return this.name;
  }
  
  public String toString()
  {
    return getFileName() + " (id: " + getId() + ", url: " + getUrl() + ")";
  }
  
  public int hashCode()
  {
    return getId().hashCode();
  }
}
