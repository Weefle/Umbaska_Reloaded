package com.neovisionaries.ws.client;

class DeflateDecompressor
{
  public static void decompress(ByteArray input, ByteArray output)
    throws FormatException
  {
    decompress(input, 0, output);
  }
  
  private static void decompress(ByteArray input, int index, ByteArray output)
    throws FormatException
  {
    int[] bitIndex = new int[1];
    bitIndex[0] = (index * 8);
    while (inflateBlock(input, bitIndex, output)) {}
  }
  
  private static boolean inflateBlock(ByteArray input, int[] bitIndex, ByteArray output)
    throws FormatException
  {
    boolean last = input.readBit(bitIndex);
    
    int type = input.readBits(bitIndex, 2);
    switch (type)
    {
    case 0: 
      inflatePlainBlock(input, bitIndex, output);
      break;
    case 1: 
      inflateFixedBlock(input, bitIndex, output);
      break;
    case 2: 
      inflateDynamicBlock(input, bitIndex, output);
      break;
    default: 
      String message = String.format("[%s] Bad compression type '11' at the bit index '%d'.", new Object[] {DeflateDecompressor.class
      
        .getSimpleName(), Integer.valueOf(bitIndex[0]) });
      
      throw new FormatException(message);
    }
    if (input.length() <= bitIndex[0] / 8) {
      last = true;
    }
    return !last;
  }
  
  private static void inflatePlainBlock(ByteArray input, int[] bitIndex, ByteArray output)
  {
    int bi = bitIndex[0] + 7 & 0xFFFFFFF8;
    
    int index = bi / 8;
    
    int len = (input.get(index) & 0xFF) + (input.get(index + 1) & 0xFF) * 256;
    
    index += 4;
    
    output.put(input, index, len);
    
    bitIndex[0] = ((index + len) * 8);
  }
  
  private static void inflateFixedBlock(ByteArray input, int[] bitIndex, ByteArray output)
    throws FormatException
  {
    inflateData(input, bitIndex, output, 
      FixedLiteralLengthHuffman.getInstance(), 
      FixedDistanceHuffman.getInstance());
  }
  
  private static void inflateDynamicBlock(ByteArray input, int[] bitIndex, ByteArray output)
    throws FormatException
  {
    Huffman[] tables = new Huffman[2];
    DeflateUtil.readDynamicTables(input, bitIndex, tables);
    
    inflateData(input, bitIndex, output, tables[0], tables[1]);
  }
  
  private static void inflateData(ByteArray input, int[] bitIndex, ByteArray output, Huffman literalLengthHuffman, Huffman distanceHuffman)
    throws FormatException
  {
    for (;;)
    {
      int literalLength = literalLengthHuffman.readSym(input, bitIndex);
      if (literalLength == 256) {
        break;
      }
      if ((0 <= literalLength) && (literalLength <= 255))
      {
        output.put(literalLength);
      }
      else
      {
        int length = DeflateUtil.readLength(input, bitIndex, literalLength);
        
        int distance = DeflateUtil.readDistance(input, bitIndex, distanceHuffman);
        
        duplicate(length, distance, output);
      }
    }
  }
  
  private static void duplicate(int length, int distance, ByteArray output)
  {
    int sourceLength = output.length();
    
    byte[] target = new byte[length];
    
    int initialPosition = sourceLength - distance;
    int sourceIndex = initialPosition;
    for (int targetIndex = 0; targetIndex < length; sourceIndex++)
    {
      if (sourceLength <= sourceIndex) {
        sourceIndex = initialPosition;
      }
      target[targetIndex] = output.get(sourceIndex);targetIndex++;
    }
    output.put(target);
  }
}
