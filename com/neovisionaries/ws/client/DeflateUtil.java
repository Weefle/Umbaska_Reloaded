package com.neovisionaries.ws.client;

class DeflateUtil
{
  private static int[] INDICES_FROM_CODE_LENGTH_ORDER = { 16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 };
  
  public static void readDynamicTables(ByteArray input, int[] bitIndex, Huffman[] tables)
    throws FormatException
  {
    int hlit = input.readBits(bitIndex, 5) + 257;
    
    int hdist = input.readBits(bitIndex, 5) + 1;
    
    int hclen = input.readBits(bitIndex, 4) + 4;
    
    int[] codeLengthsFromCodeLengthValue = new int[19];
    for (int i = 0; i < hclen; i++)
    {
      byte codeLengthOfCodeLengthValue = (byte)input.readBits(bitIndex, 3);
      
      int index = codeLengthOrderToIndex(i);
      
      codeLengthsFromCodeLengthValue[index] = codeLengthOfCodeLengthValue;
    }
    Huffman codeLengthHuffman = new Huffman(codeLengthsFromCodeLengthValue);
    
    int[] codeLengthsFromLiteralLengthCode = new int[hlit];
    readCodeLengths(input, bitIndex, codeLengthsFromLiteralLengthCode, codeLengthHuffman);
    
    Huffman literalLengthHuffman = new Huffman(codeLengthsFromLiteralLengthCode);
    
    int[] codeLengthsFromDistanceCode = new int[hdist];
    readCodeLengths(input, bitIndex, codeLengthsFromDistanceCode, codeLengthHuffman);
    
    Huffman distanceHuffman = new Huffman(codeLengthsFromDistanceCode);
    
    tables[0] = literalLengthHuffman;
    tables[1] = distanceHuffman;
  }
  
  private static void readCodeLengths(ByteArray input, int[] bitIndex, int[] codeLengths, Huffman codeLengthHuffman)
    throws FormatException
  {
    for (int i = 0; i < codeLengths.length; i++)
    {
      int codeLength = codeLengthHuffman.readSym(input, bitIndex);
      if ((0 <= codeLength) && (codeLength <= 15))
      {
        codeLengths[i] = codeLength;
      }
      else
      {
        int repeatCount;
        int repeatCount;
        int repeatCount;
        switch (codeLength)
        {
        case 16: 
          codeLength = codeLengths[(i - 1)];
          repeatCount = input.readBits(bitIndex, 2) + 3;
          break;
        case 17: 
          codeLength = 0;
          repeatCount = input.readBits(bitIndex, 3) + 3;
          break;
        case 18: 
          codeLength = 0;
          repeatCount = input.readBits(bitIndex, 7) + 11;
          break;
        default: 
          String message = String.format("[%s] Bad code length '%d' at the bit index '%d'.", new Object[] {DeflateUtil.class
          
            .getSimpleName(), Integer.valueOf(codeLength), bitIndex });
          
          throw new FormatException(message);
        }
        int repeatCount;
        for (int j = 0; j < repeatCount; j++) {
          codeLengths[(i + j)] = codeLength;
        }
        i += repeatCount - 1;
      }
    }
  }
  
  private static int codeLengthOrderToIndex(int order)
  {
    return INDICES_FROM_CODE_LENGTH_ORDER[order];
  }
  
  public static int readLength(ByteArray input, int[] bitIndex, int literalLength)
    throws FormatException
  {
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    switch (literalLength)
    {
    case 257: 
    case 258: 
    case 259: 
    case 260: 
    case 261: 
    case 262: 
    case 263: 
    case 264: 
      return literalLength - 254;
    case 265: 
      int baseValue = 11;nBits = 1; break;
    case 266: 
      int baseValue = 13;nBits = 1; break;
    case 267: 
      int baseValue = 15;nBits = 1; break;
    case 268: 
      int baseValue = 17;nBits = 1; break;
    case 269: 
      int baseValue = 19;nBits = 2; break;
    case 270: 
      int baseValue = 23;nBits = 2; break;
    case 271: 
      int baseValue = 27;nBits = 2; break;
    case 272: 
      int baseValue = 31;nBits = 2; break;
    case 273: 
      int baseValue = 35;nBits = 3; break;
    case 274: 
      int baseValue = 43;nBits = 3; break;
    case 275: 
      int baseValue = 51;nBits = 3; break;
    case 276: 
      int baseValue = 59;nBits = 3; break;
    case 277: 
      int baseValue = 67;nBits = 4; break;
    case 278: 
      int baseValue = 83;nBits = 4; break;
    case 279: 
      int baseValue = 99;nBits = 4; break;
    case 280: 
      int baseValue = 115;nBits = 4; break;
    case 281: 
      int baseValue = 131;nBits = 5; break;
    case 282: 
      int baseValue = 163;nBits = 5; break;
    case 283: 
      int baseValue = 195;nBits = 5; break;
    case 284: 
      int baseValue = 227;nBits = 5; break;
    case 285: 
      return 258;
    default: 
      String message = String.format("[%s] Bad literal/length code '%d' at the bit index '%d'.", new Object[] {DeflateUtil.class
      
        .getSimpleName(), Integer.valueOf(literalLength), Integer.valueOf(bitIndex[0]) });
      
      throw new FormatException(message);
    }
    int nBits;
    int baseValue;
    int n = input.readBits(bitIndex, nBits);
    
    return baseValue + n;
  }
  
  public static int readDistance(ByteArray input, int[] bitIndex, Huffman distanceHuffman)
    throws FormatException
  {
    int code = distanceHuffman.readSym(input, bitIndex);
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    int nBits;
    switch (code)
    {
    case 0: 
    case 1: 
    case 2: 
    case 3: 
      return code + 1;
    case 4: 
      int baseValue = 5;nBits = 1; break;
    case 5: 
      int baseValue = 7;nBits = 1; break;
    case 6: 
      int baseValue = 9;nBits = 2; break;
    case 7: 
      int baseValue = 13;nBits = 2; break;
    case 8: 
      int baseValue = 17;nBits = 3; break;
    case 9: 
      int baseValue = 25;nBits = 3; break;
    case 10: 
      int baseValue = 33;nBits = 4; break;
    case 11: 
      int baseValue = 49;nBits = 4; break;
    case 12: 
      int baseValue = 65;nBits = 5; break;
    case 13: 
      int baseValue = 97;nBits = 5; break;
    case 14: 
      int baseValue = 129;nBits = 6; break;
    case 15: 
      int baseValue = 193;nBits = 6; break;
    case 16: 
      int baseValue = 257;nBits = 7; break;
    case 17: 
      int baseValue = 385;nBits = 7; break;
    case 18: 
      int baseValue = 513;nBits = 8; break;
    case 19: 
      int baseValue = 769;nBits = 8; break;
    case 20: 
      int baseValue = 1025;nBits = 9; break;
    case 21: 
      int baseValue = 1537;nBits = 9; break;
    case 22: 
      int baseValue = 2049;nBits = 10; break;
    case 23: 
      int baseValue = 3073;nBits = 10; break;
    case 24: 
      int baseValue = 4097;nBits = 11; break;
    case 25: 
      int baseValue = 6145;nBits = 11; break;
    case 26: 
      int baseValue = 8193;nBits = 12; break;
    case 27: 
      int baseValue = 12289;nBits = 12; break;
    case 28: 
      int baseValue = 16385;nBits = 13; break;
    case 29: 
      int baseValue = 24577;nBits = 13; break;
    default: 
      String message = String.format("[%s] Bad distance code '%d' at the bit index '%d'.", new Object[] {DeflateUtil.class
      
        .getSimpleName(), Integer.valueOf(code), Integer.valueOf(bitIndex[0]) });
      
      throw new FormatException(message);
    }
    int nBits;
    int baseValue;
    int n = input.readBits(bitIndex, nBits);
    
    return baseValue + n;
  }
}
