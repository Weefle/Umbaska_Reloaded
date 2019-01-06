package com.mashape.unirest.request.body;

import com.mashape.unirest.http.utils.MapUtil;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequest;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

public class MultipartBody
  extends BaseRequest
  implements Body
{
  private Map<String, List<Object>> parameters = new LinkedHashMap();
  private Map<String, ContentType> contentTypes = new HashMap();
  private boolean hasFile;
  private HttpRequest httpRequestObj;
  private HttpMultipartMode mode;
  
  public MultipartBody(HttpRequest httpRequest)
  {
    super(httpRequest);
    this.httpRequestObj = httpRequest;
  }
  
  public MultipartBody field(String name, String value)
  {
    return field(name, value, false, null);
  }
  
  public MultipartBody field(String name, String value, String contentType)
  {
    return field(name, value, false, contentType);
  }
  
  public MultipartBody field(String name, Collection<?> collection)
  {
    for (Object current : collection)
    {
      boolean isFile = current instanceof File;
      field(name, current, isFile, null);
    }
    return this;
  }
  
  public MultipartBody field(String name, Object value)
  {
    return field(name, value, false, null);
  }
  
  public MultipartBody field(String name, Object value, boolean file)
  {
    return field(name, value, file, null);
  }
  
  public MultipartBody field(String name, Object value, boolean file, String contentType)
  {
    List<Object> list = (List)this.parameters.get(name);
    if (list == null) {
      list = new LinkedList();
    }
    list.add(value);
    this.parameters.put(name, list);
    
    ContentType type = null;
    if ((contentType != null) && (contentType.length() > 0)) {
      type = ContentType.parse(contentType);
    } else if (file) {
      type = ContentType.APPLICATION_OCTET_STREAM;
    } else {
      type = ContentType.APPLICATION_FORM_URLENCODED.withCharset("UTF-8");
    }
    this.contentTypes.put(name, type);
    if ((!this.hasFile) && (file)) {
      this.hasFile = true;
    }
    return this;
  }
  
  public MultipartBody field(String name, File file)
  {
    return field(name, file, true, null);
  }
  
  public MultipartBody field(String name, File file, String contentType)
  {
    return field(name, file, true, contentType);
  }
  
  public MultipartBody field(String name, InputStream stream, ContentType contentType, String fileName)
  {
    return field(name, new InputStreamBody(stream, contentType, fileName), true, contentType.getMimeType());
  }
  
  public MultipartBody field(String name, InputStream stream, String fileName)
  {
    return field(name, new InputStreamBody(stream, ContentType.APPLICATION_OCTET_STREAM, fileName), true, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
  }
  
  public MultipartBody field(String name, byte[] bytes, ContentType contentType, String fileName)
  {
    return field(name, new ByteArrayBody(bytes, contentType, fileName), true, contentType.getMimeType());
  }
  
  public MultipartBody field(String name, byte[] bytes, String fileName)
  {
    return field(name, new ByteArrayBody(bytes, ContentType.APPLICATION_OCTET_STREAM, fileName), true, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
  }
  
  public MultipartBody basicAuth(String username, String password)
  {
    this.httpRequestObj.basicAuth(username, password);
    return this;
  }
  
  public MultipartBody mode(String mode)
  {
    this.mode = HttpMultipartMode.valueOf(mode);
    return this;
  }
  
  public HttpEntity getEntity()
  {
    if (this.hasFile)
    {
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      if (this.mode != null) {
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      }
      for (Iterator localIterator1 = this.parameters.keySet().iterator(); localIterator1.hasNext();)
      {
        key = (String)localIterator1.next();
        List<Object> value = (List)this.parameters.get(key);
        contentType = (ContentType)this.contentTypes.get(key);
        for (Object cur : value) {
          if ((cur instanceof File))
          {
            File file = (File)cur;
            builder.addPart(key, new FileBody(file, contentType, file.getName()));
          }
          else if ((cur instanceof InputStreamBody))
          {
            builder.addPart(key, (ContentBody)cur);
          }
          else if ((cur instanceof ByteArrayBody))
          {
            builder.addPart(key, (ContentBody)cur);
          }
          else
          {
            builder.addPart(key, new StringBody(cur.toString(), contentType));
          }
        }
      }
      String key;
      ContentType contentType;
      return builder.build();
    }
    try
    {
      return new UrlEncodedFormEntity(MapUtil.getList(this.parameters), "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      throw new RuntimeException(e);
    }
  }
}
