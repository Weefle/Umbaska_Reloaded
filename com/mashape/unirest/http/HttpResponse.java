package com.mashape.unirest.http;

import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.http.utils.ResponseUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

public class HttpResponse<T>
{
  private int statusCode;
  private String statusText;
  private Headers headers = new Headers();
  private InputStream rawBody;
  private T body;
  
  public HttpResponse(org.apache.http.HttpResponse response, Class<T> responseClass)
  {
    HttpEntity responseEntity = response.getEntity();
    ObjectMapper objectMapper = (ObjectMapper)Options.getOption(Option.OBJECT_MAPPER);
    
    Header[] allHeaders = response.getAllHeaders();
    for (Header header : allHeaders)
    {
      String headerName = header.getName();
      List<String> list = (List)this.headers.get(headerName);
      if (list == null) {
        list = new ArrayList();
      }
      list.add(header.getValue());
      this.headers.put(headerName, list);
    }
    StatusLine statusLine = response.getStatusLine();
    this.statusCode = statusLine.getStatusCode();
    this.statusText = statusLine.getReasonPhrase();
    if (responseEntity != null)
    {
      String charset = "UTF-8";
      
      Header contentType = responseEntity.getContentType();
      if (contentType != null)
      {
        String responseCharset = ResponseUtils.getCharsetFromContentType(contentType.getValue());
        if ((responseCharset != null) && (!responseCharset.trim().equals(""))) {
          charset = responseCharset;
        }
      }
      try
      {
        try
        {
          InputStream responseInputStream = responseEntity.getContent();
          if (ResponseUtils.isGzipped(responseEntity.getContentEncoding())) {
            responseInputStream = new GZIPInputStream(responseEntity.getContent());
          }
          rawBody = ResponseUtils.getBytes(responseInputStream);
        }
        catch (IOException e2)
        {
          byte[] rawBody;
          throw new RuntimeException(e2);
        }
        byte[] rawBody;
        this.rawBody = new ByteArrayInputStream(rawBody);
        if (JsonNode.class.equals(responseClass))
        {
          String jsonString = new String(rawBody, charset).trim();
          this.body = new JsonNode(jsonString);
        }
        else if (String.class.equals(responseClass))
        {
          this.body = new String(rawBody, charset);
        }
        else if (InputStream.class.equals(responseClass))
        {
          this.body = this.rawBody;
        }
        else if (objectMapper != null)
        {
          this.body = objectMapper.readValue(new String(rawBody, charset), responseClass);
        }
        else
        {
          throw new Exception("Only String, JsonNode and InputStream are supported, or an ObjectMapper implementation is required.");
        }
      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
    }
    try
    {
      EntityUtils.consume(responseEntity);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public int getStatus()
  {
    return this.statusCode;
  }
  
  public String getStatusText()
  {
    return this.statusText;
  }
  
  public Headers getHeaders()
  {
    return this.headers;
  }
  
  public InputStream getRawBody()
  {
    return this.rawBody;
  }
  
  public T getBody()
  {
    return (T)this.body;
  }
}
