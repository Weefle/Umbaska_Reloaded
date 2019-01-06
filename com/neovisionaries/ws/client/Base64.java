package com.neovisionaries.ws.client;

class Base64
{
  private static final byte[] INDEX_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  
  public static String encode(String data)
  {
    if (data == null) {
      return null;
    }
    return encode(Misc.getBytesUTF8(data));
  }
  
  public static String encode(byte[] data)
  {
    if (data == null) {
      return null;
    }
    int capacity = ((data.length * 8 + 5) / 6 + 3) / 4 * 4;
    
    StringBuilder builder = new StringBuilder(capacity);
    for (int bitIndex = 0;; bitIndex += 6)
    {
      int bits = extractBits(data, bitIndex);
      if (bits < 0) {
        break;
      }
      builder.append((char)INDEX_TABLE[bits]);
    }
    for (int i = builder.length(); i < capacity; i++) {
      builder.append('=');
    }
    return builder.toString();
  }
  
  private static int extractBits(byte[] data, int bitIndex)
  {
    int byteIndex = bitIndex / 8;
    if (data.length <= byteIndex) {
      return -1;
    }
    byte nextByte;
    byte nextByte;
    if (data.length - 1 == byteIndex) {
      nextByte = 0;
    } else {
      nextByte = data[(byteIndex + 1)];
    }
    switch (bitIndex % 24 / 6)
    {
    case 0: 
      return data[byteIndex] >> 2 & 0x3F;
    case 1: 
      return data[byteIndex] << 4 & 0x30 | nextByte >> 4 & 0xF;
    case 2: 
      return data[byteIndex] << 2 & 0x3C | nextByte >> 6 & 0x3;
    case 3: 
      return data[byteIndex] & 0x3F;
    }
    return 0;
  }
}
