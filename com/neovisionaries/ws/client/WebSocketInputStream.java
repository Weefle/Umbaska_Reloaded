package com.neovisionaries.ws.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class WebSocketInputStream
  extends FilterInputStream
{
  public WebSocketInputStream(InputStream in)
  {
    super(in);
  }
  
  public String readLine()
    throws IOException
  {
    return Misc.readLine(this, "UTF-8");
  }
  
  public WebSocketFrame readFrame()
    throws IOException, WebSocketException
  {
    byte[] buffer = new byte[8];
    
    readBytes(buffer, 2);
    
    boolean fin = (buffer[0] & 0x80) != 0;
    
    boolean rsv1 = (buffer[0] & 0x40) != 0;
    boolean rsv2 = (buffer[0] & 0x20) != 0;
    boolean rsv3 = (buffer[0] & 0x10) != 0;
    
    int opcode = buffer[0] & 0xF;
    
    boolean mask = (buffer[1] & 0x80) != 0;
    
    long payloadLength = buffer[1] & 0x7F;
    if (payloadLength == 126L)
    {
      readBytes(buffer, 2);
      
      payloadLength = (buffer[0] & 0xFF) << 8 | buffer[1] & 0xFF;
    }
    else if (payloadLength == 127L)
    {
      readBytes(buffer, 8);
      if ((buffer[0] & 0x80) != 0) {
        throw new WebSocketException(WebSocketError.INVALID_PAYLOAD_LENGTH, "The payload length of a frame is invalid.");
      }
      payloadLength = (buffer[0] & 0xFF) << 56 | (buffer[1] & 0xFF) << 48 | (buffer[2] & 0xFF) << 40 | (buffer[3] & 0xFF) << 32 | (buffer[4] & 0xFF) << 24 | (buffer[5] & 0xFF) << 16 | (buffer[6] & 0xFF) << 8 | buffer[7] & 0xFF;
    }
    byte[] maskingKey = null;
    if (mask)
    {
      maskingKey = new byte[4];
      readBytes(maskingKey, 4);
    }
    if (2147483647L < payloadLength)
    {
      skipQuietly(payloadLength);
      throw new WebSocketException(WebSocketError.TOO_LONG_PAYLOAD, "The payload length of a frame exceeds the maximum array size in Java.");
    }
    byte[] payload = readPayload(payloadLength, mask, maskingKey);
    
    return new WebSocketFrame().setFin(fin).setRsv1(rsv1).setRsv2(rsv2).setRsv3(rsv3).setOpcode(opcode).setMask(mask).setPayload(payload);
  }
  
  void readBytes(byte[] buffer, int length)
    throws IOException, WebSocketException
  {
    int total = 0;
    while (total < length)
    {
      int count = read(buffer, total, length - total);
      if (count <= 0) {
        throw new WebSocketException(WebSocketError.INSUFFICENT_DATA, "The end of the stream has been reached unexpectedly.");
      }
      total += count;
    }
  }
  
  private void skipQuietly(long length)
  {
    try
    {
      skip(length);
    }
    catch (IOException e) {}
  }
  
  private byte[] readPayload(long payloadLength, boolean mask, byte[] maskingKey)
    throws IOException, WebSocketException
  {
    if (payloadLength == 0L) {
      return null;
    }
    byte[] payload;
    try
    {
      payload = new byte[(int)payloadLength];
    }
    catch (OutOfMemoryError e)
    {
      skipQuietly(payloadLength);
      
      throw new WebSocketException(WebSocketError.INSUFFICIENT_MEMORY_FOR_PAYLOAD, "OutOfMemoryError occurred during a trial to allocate a memory area for a frame's payload: " + e.getMessage(), e);
    }
    readBytes(payload, payload.length);
    if (mask) {
      WebSocketFrame.mask(maskingKey, payload);
    }
    return payload;
  }
}
