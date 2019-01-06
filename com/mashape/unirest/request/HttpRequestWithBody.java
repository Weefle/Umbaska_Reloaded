package com.mashape.unirest.request;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RawBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpRequestWithBody
  extends HttpRequest
{
  public HttpRequestWithBody(HttpMethod method, String url)
  {
    super(method, url);
  }
  
  public HttpRequestWithBody routeParam(String name, String value)
  {
    super.routeParam(name, value);
    return this;
  }
  
  public HttpRequestWithBody header(String name, String value)
  {
    return (HttpRequestWithBody)super.header(name, value);
  }
  
  public HttpRequestWithBody headers(Map<String, String> headers)
  {
    return (HttpRequestWithBody)super.headers(headers);
  }
  
  public HttpRequestWithBody basicAuth(String username, String password)
  {
    super.basicAuth(username, password);
    return this;
  }
  
  public HttpRequestWithBody queryString(Map<String, Object> parameters)
  {
    return (HttpRequestWithBody)super.queryString(parameters);
  }
  
  public HttpRequestWithBody queryString(String name, Object value)
  {
    return (HttpRequestWithBody)super.queryString(name, value);
  }
  
  public MultipartBody field(String name, Collection<?> value)
  {
    MultipartBody body = new MultipartBody(this).field(name, value);
    this.body = body;
    return body;
  }
  
  public MultipartBody field(String name, Object value)
  {
    return field(name, value, null);
  }
  
  public MultipartBody field(String name, File file)
  {
    return field(name, file, null);
  }
  
  public MultipartBody field(String name, Object value, String contentType)
  {
    MultipartBody body = new MultipartBody(this).field(name, value == null ? "" : value.toString(), contentType);
    this.body = body;
    return body;
  }
  
  public MultipartBody field(String name, File file, String contentType)
  {
    MultipartBody body = new MultipartBody(this).field(name, file, contentType);
    this.body = body;
    return body;
  }
  
  public MultipartBody fields(Map<String, Object> parameters)
  {
    MultipartBody body = new MultipartBody(this);
    if (parameters != null) {
      for (Map.Entry<String, Object> param : parameters.entrySet()) {
        if ((param.getValue() instanceof File)) {
          body.field((String)param.getKey(), (File)param.getValue());
        } else {
          body.field((String)param.getKey(), param.getValue() == null ? "" : param.getValue().toString());
        }
      }
    }
    this.body = body;
    return body;
  }
  
  public MultipartBody field(String name, InputStream stream, ContentType contentType, String fileName)
  {
    InputStreamBody inputStreamBody = new InputStreamBody(stream, contentType, fileName);
    MultipartBody body = new MultipartBody(this).field(name, inputStreamBody, true, contentType.toString());
    this.body = body;
    return body;
  }
  
  public MultipartBody field(String name, InputStream stream, String fileName)
  {
    MultipartBody body = field(name, stream, ContentType.APPLICATION_OCTET_STREAM, fileName);
    this.body = body;
    return body;
  }
  
  public RequestBodyEntity body(JsonNode body)
  {
    return body(body.toString());
  }
  
  public RequestBodyEntity body(String body)
  {
    RequestBodyEntity b = new RequestBodyEntity(this).body(body);
    this.body = b;
    return b;
  }
  
  public RequestBodyEntity body(Object body)
  {
    ObjectMapper objectMapper = (ObjectMapper)Options.getOption(Option.OBJECT_MAPPER);
    if (objectMapper == null) {
      throw new RuntimeException("Serialization Impossible. Can't find an ObjectMapper implementation.");
    }
    return body(objectMapper.writeValue(body));
  }
  
  public RawBody body(byte[] body)
  {
    RawBody b = new RawBody(this).body(body);
    this.body = b;
    return b;
  }
  
  public RequestBodyEntity body(JSONObject body)
  {
    return body(body.toString());
  }
  
  public RequestBodyEntity body(JSONArray body)
  {
    return body(body.toString());
  }
}
