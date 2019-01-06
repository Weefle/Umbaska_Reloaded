package com.neovisionaries.ws.client;

class FixedLiteralLengthHuffman
  extends Huffman
{
  private static final FixedLiteralLengthHuffman INSTANCE = new FixedLiteralLengthHuffman();
  
  private FixedLiteralLengthHuffman()
  {
    super(buildCodeLensFromSym());
  }
  
  private static int[] buildCodeLensFromSym()
  {
    int[] codeLengths = new int['Ġ'];
    for (int symbol = 0; symbol < 144; symbol++) {
      codeLengths[symbol] = 8;
    }
    for (; symbol < 256; symbol++) {
      codeLengths[symbol] = 9;
    }
    for (; symbol < 280; symbol++) {
      codeLengths[symbol] = 7;
    }
    for (; symbol < 288; symbol++) {
      codeLengths[symbol] = 8;
    }
    return codeLengths;
  }
  
  public static FixedLiteralLengthHuffman getInstance()
  {
    return INSTANCE;
  }
}
