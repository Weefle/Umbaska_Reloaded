package com.neovisionaries.ws.client;

public class WebSocketFrame
{
  private boolean mFin;
  private boolean mRsv1;
  private boolean mRsv2;
  private boolean mRsv3;
  private int mOpcode;
  private boolean mMask;
  private byte[] mPayload;
  
  public boolean getFin()
  {
    return this.mFin;
  }
  
  public WebSocketFrame setFin(boolean fin)
  {
    this.mFin = fin;
    
    return this;
  }
  
  public boolean getRsv1()
  {
    return this.mRsv1;
  }
  
  public WebSocketFrame setRsv1(boolean rsv1)
  {
    this.mRsv1 = rsv1;
    
    return this;
  }
  
  public boolean getRsv2()
  {
    return this.mRsv2;
  }
  
  public WebSocketFrame setRsv2(boolean rsv2)
  {
    this.mRsv2 = rsv2;
    
    return this;
  }
  
  public boolean getRsv3()
  {
    return this.mRsv3;
  }
  
  public WebSocketFrame setRsv3(boolean rsv3)
  {
    this.mRsv3 = rsv3;
    
    return this;
  }
  
  public int getOpcode()
  {
    return this.mOpcode;
  }
  
  public WebSocketFrame setOpcode(int opcode)
  {
    this.mOpcode = opcode;
    
    return this;
  }
  
  public boolean isContinuationFrame()
  {
    return this.mOpcode == 0;
  }
  
  public boolean isTextFrame()
  {
    return this.mOpcode == 1;
  }
  
  public boolean isBinaryFrame()
  {
    return this.mOpcode == 2;
  }
  
  public boolean isCloseFrame()
  {
    return this.mOpcode == 8;
  }
  
  public boolean isPingFrame()
  {
    return this.mOpcode == 9;
  }
  
  public boolean isPongFrame()
  {
    return this.mOpcode == 10;
  }
  
  public boolean isDataFrame()
  {
    return (1 <= this.mOpcode) && (this.mOpcode <= 7);
  }
  
  public boolean isControlFrame()
  {
    return (8 <= this.mOpcode) && (this.mOpcode <= 15);
  }
  
  boolean getMask()
  {
    return this.mMask;
  }
  
  WebSocketFrame setMask(boolean mask)
  {
    this.mMask = mask;
    
    return this;
  }
  
  public boolean hasPayload()
  {
    return this.mPayload != null;
  }
  
  public int getPayloadLength()
  {
    if (this.mPayload == null) {
      return 0;
    }
    return this.mPayload.length;
  }
  
  public byte[] getPayload()
  {
    return this.mPayload;
  }
  
  public String getPayloadText()
  {
    if (this.mPayload == null) {
      return null;
    }
    return Misc.toStringUTF8(this.mPayload);
  }
  
  public WebSocketFrame setPayload(byte[] payload)
  {
    if ((payload != null) && (payload.length == 0)) {
      payload = null;
    }
    this.mPayload = payload;
    
    return this;
  }
  
  public WebSocketFrame setPayload(String payload)
  {
    if ((payload == null) || (payload.length() == 0)) {
      return setPayload((byte[])null);
    }
    return setPayload(Misc.getBytesUTF8(payload));
  }
  
  public WebSocketFrame setCloseFramePayload(int closeCode, String reason)
  {
    byte[] encodedCloseCode = { (byte)(closeCode >> 8 & 0xFF), (byte)(closeCode & 0xFF) };
    if ((reason == null) || (reason.length() == 0)) {
      return setPayload(encodedCloseCode);
    }
    byte[] encodedReason = Misc.getBytesUTF8(reason);
    
    byte[] payload = new byte[2 + encodedReason.length];
    System.arraycopy(encodedCloseCode, 0, payload, 0, 2);
    System.arraycopy(encodedReason, 0, payload, 2, encodedReason.length);
    
    return setPayload(payload);
  }
  
  public int getCloseCode()
  {
    if ((this.mPayload == null) || (this.mPayload.length < 2)) {
      return 1005;
    }
    int closeCode = (this.mPayload[0] & 0xFF) << 8 | this.mPayload[1] & 0xFF;
    
    return closeCode;
  }
  
  public String getCloseReason()
  {
    if ((this.mPayload == null) || (this.mPayload.length < 3)) {
      return null;
    }
    return Misc.toStringUTF8(this.mPayload, 2, this.mPayload.length - 2);
  }
  
  public String toString()
  {
    StringBuilder builder = new StringBuilder().append("WebSocketFrame(FIN=").append(this.mFin ? "1" : "0").append(",RSV1=").append(this.mRsv1 ? "1" : "0").append(",RSV2=").append(this.mRsv2 ? "1" : "0").append(",RSV3=").append(this.mRsv3 ? "1" : "0").append(",Opcode=").append(Misc.toOpcodeName(this.mOpcode)).append(",Length=").append(getPayloadLength());
    switch (this.mOpcode)
    {
    case 1: 
      appendPayloadText(builder);
      break;
    case 2: 
      appendPayloadBinary(builder);
      break;
    case 8: 
      appendPayloadClose(builder);
    }
    return ")";
  }
  
  private boolean appendPayloadCommon(StringBuilder builder)
  {
    builder.append(",Payload=");
    if (this.mPayload == null)
    {
      builder.append("null");
      
      return true;
    }
    if (this.mRsv1)
    {
      builder.append("compressed");
      
      return true;
    }
    return false;
  }
  
  private void appendPayloadText(StringBuilder builder)
  {
    if (appendPayloadCommon(builder)) {
      return;
    }
    builder.append("\"");
    builder.append(getPayloadText());
    builder.append("\"");
  }
  
  private void appendPayloadClose(StringBuilder builder)
  {
    builder.append(",CloseCode=").append(getCloseCode()).append(",Reason=");
    
    String reason = getCloseReason();
    if (reason == null) {
      builder.append("null");
    } else {
      builder.append("\"").append(reason).append("\"");
    }
  }
  
  private void appendPayloadBinary(StringBuilder builder)
  {
    if (appendPayloadCommon(builder)) {
      return;
    }
    for (int i = 0; i < this.mPayload.length; i++) {
      builder.append(String.format("%02X ", new Object[] { Integer.valueOf(0xFF & this.mPayload[i]) }));
    }
    if (this.mPayload.length != 0) {
      builder.setLength(builder.length() - 1);
    }
  }
  
  public static WebSocketFrame createContinuationFrame()
  {
    return new WebSocketFrame().setOpcode(0);
  }
  
  public static WebSocketFrame createContinuationFrame(byte[] payload)
  {
    return createContinuationFrame().setPayload(payload);
  }
  
  public static WebSocketFrame createContinuationFrame(String payload)
  {
    return createContinuationFrame().setPayload(payload);
  }
  
  public static WebSocketFrame createTextFrame(String payload)
  {
    return new WebSocketFrame().setFin(true).setOpcode(1).setPayload(payload);
  }
  
  public static WebSocketFrame createBinaryFrame(byte[] payload)
  {
    return new WebSocketFrame().setFin(true).setOpcode(2).setPayload(payload);
  }
  
  public static WebSocketFrame createCloseFrame()
  {
    return new WebSocketFrame().setFin(true).setOpcode(8);
  }
  
  public static WebSocketFrame createCloseFrame(int closeCode)
  {
    return createCloseFrame().setCloseFramePayload(closeCode, null);
  }
  
  public static WebSocketFrame createCloseFrame(int closeCode, String reason)
  {
    return createCloseFrame().setCloseFramePayload(closeCode, reason);
  }
  
  public static WebSocketFrame createPingFrame()
  {
    return new WebSocketFrame().setFin(true).setOpcode(9);
  }
  
  public static WebSocketFrame createPingFrame(byte[] payload)
  {
    return createPingFrame().setPayload(payload);
  }
  
  public static WebSocketFrame createPingFrame(String payload)
  {
    return createPingFrame().setPayload(payload);
  }
  
  public static WebSocketFrame createPongFrame()
  {
    return new WebSocketFrame().setFin(true).setOpcode(10);
  }
  
  public static WebSocketFrame createPongFrame(byte[] payload)
  {
    return createPongFrame().setPayload(payload);
  }
  
  public static WebSocketFrame createPongFrame(String payload)
  {
    return createPongFrame().setPayload(payload);
  }
  
  static byte[] mask(byte[] maskingKey, byte[] payload)
  {
    if ((maskingKey == null) || (maskingKey.length < 4) || (payload == null)) {
      return payload;
    }
    for (int i = 0; i < payload.length; i++)
    {
      int tmp26_25 = i; byte[] tmp26_24 = payload;tmp26_24[tmp26_25] = ((byte)(tmp26_24[tmp26_25] ^ maskingKey[(i % 4)]));
    }
    return payload;
  }
}
