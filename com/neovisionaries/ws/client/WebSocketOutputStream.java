package com.neovisionaries.ws.client;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class WebSocketOutputStream
  extends FilterOutputStream
{
  public WebSocketOutputStream(OutputStream out)
  {
    super(out);
  }
  
  public void write(String string)
    throws IOException
  {
    byte[] bytes = Misc.getBytesUTF8(string);
    
    write(bytes);
  }
  
  public void write(WebSocketFrame frame)
    throws IOException
  {
    writeFrame0(frame);
    writeFrame1(frame);
    writeFrameExtendedPayloadLength(frame);
    
    byte[] maskingKey = Misc.nextBytes(4);
    
    write(maskingKey);
    
    writeFramePayload(frame, maskingKey);
  }
  
  private void writeFrame0(WebSocketFrame frame)
    throws IOException
  {
    int b = (frame.getFin() ? 128 : 0) | (frame.getRsv1() ? 64 : 0) | (frame.getRsv2() ? 32 : 0) | (frame.getRsv3() ? 16 : 0) | frame.getOpcode() & 0xF;
    
    write(b);
  }
  
  private void writeFrame1(WebSocketFrame frame)
    throws IOException
  {
    int b = 128;
    
    int len = frame.getPayloadLength();
    if (len <= 125) {
      b |= len;
    } else if (len <= 65535) {
      b |= 0x7E;
    } else {
      b |= 0x7F;
    }
    write(b);
  }
  
  private void writeFrameExtendedPayloadLength(WebSocketFrame frame)
    throws IOException
  {
    int len = frame.getPayloadLength();
    if (len <= 125) {
      return;
    }
    if (len <= 65535)
    {
      write(len >> 8 & 0xFF);
      write(len & 0xFF);
      return;
    }
    write(0);
    write(0);
    write(0);
    write(0);
    write(len >> 24 & 0xFF);
    write(len >> 16 & 0xFF);
    write(len >> 8 & 0xFF);
    write(len & 0xFF);
  }
  
  private void writeFramePayload(WebSocketFrame frame, byte[] maskingKey)
    throws IOException
  {
    byte[] payload = frame.getPayload();
    if (payload == null) {
      return;
    }
    for (int i = 0; i < payload.length; i++)
    {
      int b = (payload[i] ^ maskingKey[(i % 4)]) & 0xFF;
      
      write(b);
    }
  }
}
