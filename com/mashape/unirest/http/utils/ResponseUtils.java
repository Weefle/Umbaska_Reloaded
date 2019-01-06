package com.mashape.unirest.http.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;

public class ResponseUtils
{
  private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");
  
  public static String getCharsetFromContentType(String contentType)
  {
    if (contentType == null) {
      return null;
    }
    Matcher m = charsetPattern.matcher(contentType);
    if (m.find()) {
      return m.group(1).trim().toUpperCase();
    }
    return null;
  }
  
  public static byte[] getBytes(InputStream is)
    throws IOException
  {
    int size = 1024;
    int len;
    byte[] buf;
    if ((is instanceof ByteArrayInputStream))
    {
      size = is.available();
      byte[] buf = new byte[size];
      len = is.read(buf, 0, size);
    }
    else
    {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      buf = new byte[size];
      int len;
      while ((len = is.read(buf, 0, size)) != -1) {
        bos.write(buf, 0, len);
      }
      buf = bos.toByteArray();
    }
    return buf;
  }
  
  public static boolean isGzipped(Header contentEncoding)
  {
    if (contentEncoding != null)
    {
      String value = contentEncoding.getValue();
      if ((value != null) && ("gzip".equals(value.toLowerCase().trim()))) {
        return true;
      }
    }
    return false;
  }
}
