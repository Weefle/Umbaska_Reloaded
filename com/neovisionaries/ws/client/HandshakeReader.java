package com.neovisionaries.ws.client;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class HandshakeReader
{
  private static final String ACCEPT_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
  private final WebSocket mWebSocket;
  
  public HandshakeReader(WebSocket websocket)
  {
    this.mWebSocket = websocket;
  }
  
  public Map<String, List<String>> readHandshake(WebSocketInputStream input, String key)
    throws WebSocketException
  {
    StatusLine statusLine = readStatusLine(input);
    
    Map<String, List<String>> headers = readHttpHeaders(input);
    
    validateStatusLine(statusLine, headers, input);
    
    validateUpgrade(statusLine, headers);
    
    validateConnection(statusLine, headers);
    
    validateAccept(statusLine, headers, key);
    
    validateExtensions(statusLine, headers);
    
    validateProtocol(statusLine, headers);
    
    return headers;
  }
  
  private StatusLine readStatusLine(WebSocketInputStream input)
    throws WebSocketException
  {
    try
    {
      line = input.readLine();
    }
    catch (IOException e)
    {
      String line;
      throw new WebSocketException(WebSocketError.OPENING_HANDSHAKE_RESPONSE_FAILURE, "Failed to read an opening handshake response from the server: " + e.getMessage(), e);
    }
    String line;
    if ((line == null) || (line.length() == 0)) {
      throw new WebSocketException(WebSocketError.STATUS_LINE_EMPTY, "The status line of the opening handshake response is empty.");
    }
    try
    {
      return new StatusLine(line);
    }
    catch (Exception e)
    {
      throw new WebSocketException(WebSocketError.STATUS_LINE_BAD_FORMAT, "The status line of the opening handshake response is badly formatted. The status line is: " + line);
    }
  }
  
  private Map<String, List<String>> readHttpHeaders(WebSocketInputStream input)
    throws WebSocketException
  {
    Map<String, List<String>> headers = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    
    StringBuilder builder = null;
    for (;;)
    {
      try
      {
        line = input.readLine();
      }
      catch (IOException e)
      {
        String line;
        throw new WebSocketException(WebSocketError.HTTP_HEADER_FAILURE, "An error occurred while HTTP header section was being read: " + e.getMessage(), e);
      }
      String line;
      if ((line == null) || (line.length() == 0))
      {
        if (builder == null) {
          break;
        }
        parseHttpHeader(headers, builder.toString()); break;
      }
      char ch = line.charAt(0);
      if ((ch == ' ') || (ch == '\t'))
      {
        if (builder != null)
        {
          line = line.replaceAll("^[ \t]+", " ");
          
          builder.append(line);
        }
      }
      else
      {
        if (builder != null) {
          parseHttpHeader(headers, builder.toString());
        }
        builder = new StringBuilder(line);
      }
    }
    return headers;
  }
  
  private void parseHttpHeader(Map<String, List<String>> headers, String header)
  {
    String[] pair = header.split(":", 2);
    if (pair.length < 2) {
      return;
    }
    String name = pair[0].trim();
    
    String value = pair[1].trim();
    
    List<String> list = (List)headers.get(name);
    if (list == null)
    {
      list = new ArrayList();
      headers.put(name, list);
    }
    list.add(value);
  }
  
  private void validateStatusLine(StatusLine statusLine, Map<String, List<String>> headers, WebSocketInputStream input)
    throws WebSocketException
  {
    if (statusLine.getStatusCode() == 101) {
      return;
    }
    byte[] body = readBody(headers, input);
    
    throw new OpeningHandshakeException(WebSocketError.NOT_SWITCHING_PROTOCOLS, "The status code of the opening handshake response is not '101 Switching Protocols'. The status line is: " + statusLine, statusLine, headers, body);
  }
  
  private byte[] readBody(Map<String, List<String>> headers, WebSocketInputStream input)
  {
    int length = getContentLength(headers);
    if (length <= 0) {
      return null;
    }
    try
    {
      byte[] body = new byte[length];
      
      input.readBytes(body, length);
      
      return body;
    }
    catch (Throwable t) {}
    return null;
  }
  
  private int getContentLength(Map<String, List<String>> headers)
  {
    try
    {
      return Integer.parseInt((String)((List)headers.get("Content-Length")).get(0));
    }
    catch (Exception e) {}
    return -1;
  }
  
  private void validateUpgrade(StatusLine statusLine, Map<String, List<String>> headers)
    throws WebSocketException
  {
    List<String> values = (List)headers.get("Upgrade");
    if ((values == null) || (values.size() == 0)) {
      throw new OpeningHandshakeException(WebSocketError.NO_UPGRADE_HEADER, "The opening handshake response does not contain 'Upgrade' header.", statusLine, headers);
    }
    for (String value : values)
    {
      String[] elements = value.split("\\s*,\\s*");
      for (String element : elements) {
        if ("websocket".equalsIgnoreCase(element)) {
          return;
        }
      }
    }
    throw new OpeningHandshakeException(WebSocketError.NO_WEBSOCKET_IN_UPGRADE_HEADER, "'websocket' was not found in 'Upgrade' header.", statusLine, headers);
  }
  
  private void validateConnection(StatusLine statusLine, Map<String, List<String>> headers)
    throws WebSocketException
  {
    List<String> values = (List)headers.get("Connection");
    if ((values == null) || (values.size() == 0)) {
      throw new OpeningHandshakeException(WebSocketError.NO_CONNECTION_HEADER, "The opening handshake response does not contain 'Connection' header.", statusLine, headers);
    }
    for (String value : values)
    {
      String[] elements = value.split("\\s*,\\s*");
      for (String element : elements) {
        if ("Upgrade".equalsIgnoreCase(element)) {
          return;
        }
      }
    }
    throw new OpeningHandshakeException(WebSocketError.NO_UPGRADE_IN_CONNECTION_HEADER, "'Upgrade' was not found in 'Connection' header.", statusLine, headers);
  }
  
  private void validateAccept(StatusLine statusLine, Map<String, List<String>> headers, String key)
    throws WebSocketException
  {
    List<String> values = (List)headers.get("Sec-WebSocket-Accept");
    if (values == null) {
      throw new OpeningHandshakeException(WebSocketError.NO_SEC_WEBSOCKET_ACCEPT_HEADER, "The opening handshake response does not contain 'Sec-WebSocket-Accept' header.", statusLine, headers);
    }
    String actual = (String)values.get(0);
    
    String input = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    try
    {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      
      byte[] digest = md.digest(Misc.getBytesUTF8(input));
      
      expected = Base64.encode(digest);
    }
    catch (Exception e)
    {
      String expected;
      return;
    }
    String expected;
    if (!expected.equals(actual)) {
      throw new OpeningHandshakeException(WebSocketError.UNEXPECTED_SEC_WEBSOCKET_ACCEPT_HEADER, "The value of 'Sec-WebSocket-Accept' header is different from the expected one.", statusLine, headers);
    }
  }
  
  private void validateExtensions(StatusLine statusLine, Map<String, List<String>> headers)
    throws WebSocketException
  {
    List<String> values = (List)headers.get("Sec-WebSocket-Extensions");
    if ((values == null) || (values.size() == 0)) {
      return;
    }
    List<WebSocketExtension> extensions = new ArrayList();
    for (String value : values)
    {
      String[] elements = value.split("\\s*,\\s*");
      for (String element : elements)
      {
        WebSocketExtension extension = WebSocketExtension.parse(element);
        if (extension == null) {
          throw new OpeningHandshakeException(WebSocketError.EXTENSION_PARSE_ERROR, "The value in 'Sec-WebSocket-Extensions' failed to be parsed: " + element, statusLine, headers);
        }
        String name = extension.getName();
        if (!this.mWebSocket.getHandshakeBuilder().containsExtension(name)) {
          throw new OpeningHandshakeException(WebSocketError.UNSUPPORTED_EXTENSION, "The extension contained in the Sec-WebSocket-Extensions header is not supported: " + name, statusLine, headers);
        }
        extension.validate();
        
        extensions.add(extension);
      }
    }
    validateExtensionCombination(statusLine, headers, extensions);
    
    this.mWebSocket.setAgreedExtensions(extensions);
  }
  
  private void validateExtensionCombination(StatusLine statusLine, Map<String, List<String>> headers, List<WebSocketExtension> extensions)
    throws WebSocketException
  {
    WebSocketExtension pmce = null;
    for (WebSocketExtension extension : extensions) {
      if ((extension instanceof PerMessageCompressionExtension)) {
        if (pmce == null)
        {
          pmce = extension;
        }
        else
        {
          String message = String.format("'%s' extension and '%s' extension conflict with each other.", new Object[] {pmce
          
            .getName(), extension.getName() });
          
          throw new OpeningHandshakeException(WebSocketError.EXTENSIONS_CONFLICT, message, statusLine, headers);
        }
      }
    }
  }
  
  private void validateProtocol(StatusLine statusLine, Map<String, List<String>> headers)
    throws WebSocketException
  {
    List<String> values = (List)headers.get("Sec-WebSocket-Protocol");
    if (values == null) {
      return;
    }
    String protocol = (String)values.get(0);
    if ((protocol == null) || (protocol.length() == 0)) {
      return;
    }
    if (!this.mWebSocket.getHandshakeBuilder().containsProtocol(protocol)) {
      throw new OpeningHandshakeException(WebSocketError.UNSUPPORTED_PROTOCOL, "The protocol contained in the Sec-WebSocket-Protocol header is not supported: " + protocol, statusLine, headers);
    }
    this.mWebSocket.setAgreedProtocol(protocol);
  }
}
