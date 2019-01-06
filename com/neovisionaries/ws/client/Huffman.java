package com.neovisionaries.ws.client;

class Huffman
{
  private final int mMinCodeLen;
  private final int mMaxCodeLen;
  private final int[] mMaxCodeValsFromCodeLen;
  private final int[] mSymsFromCodeVal;
  
  public Huffman(int[] codeLensFromSym)
  {
    this.mMinCodeLen = Math.max(Misc.min(codeLensFromSym), 1);
    this.mMaxCodeLen = Misc.max(codeLensFromSym);
    
    int[] countsFromCodeLen = createCountsFromCodeLen(codeLensFromSym, this.mMaxCodeLen);
    
    Object[] out = new Object[2];
    this.mMaxCodeValsFromCodeLen = createMaxCodeValsFromCodeLen(countsFromCodeLen, this.mMaxCodeLen, out);
    
    int[] codeValsFromCodeLen = (int[])out[0];
    int maxCodeVal = ((Integer)out[1]).intValue();
    this.mSymsFromCodeVal = createSymsFromCodeVal(codeLensFromSym, codeValsFromCodeLen, maxCodeVal);
  }
  
  private static int[] createIntArray(int size, int initialValue)
  {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = initialValue;
    }
    return array;
  }
  
  private static int[] createCountsFromCodeLen(int[] codeLensFromSym, int maxCodeLen)
  {
    int[] countsFromCodeLen = new int[maxCodeLen + 1];
    for (int symbol = 0; symbol < codeLensFromSym.length; symbol++)
    {
      int codeLength = codeLensFromSym[symbol];
      countsFromCodeLen[codeLength] += 1;
    }
    return countsFromCodeLen;
  }
  
  private static int[] createMaxCodeValsFromCodeLen(int[] countsFromCodeLen, int maxCodeLen, Object[] out)
  {
    int[] maxCodeValsFromCodeLen = createIntArray(maxCodeLen + 1, -1);
    
    int minCodeVal = 0;
    int maxCodeVal = 0;
    countsFromCodeLen[0] = 0;
    int[] codeValsFromCodeLen = new int[maxCodeLen + 1];
    for (int codeLen = 1; codeLen < countsFromCodeLen.length; codeLen++)
    {
      int prevCount = countsFromCodeLen[(codeLen - 1)];
      minCodeVal = minCodeVal + prevCount << 1;
      codeValsFromCodeLen[codeLen] = minCodeVal;
      
      maxCodeVal = minCodeVal + countsFromCodeLen[codeLen] - 1;
      maxCodeValsFromCodeLen[codeLen] = maxCodeVal;
    }
    out[0] = codeValsFromCodeLen;
    out[1] = Integer.valueOf(maxCodeVal);
    
    return maxCodeValsFromCodeLen;
  }
  
  private static int[] createSymsFromCodeVal(int[] codeLensFromSym, int[] codeValsFromCodeLen, int maxCodeVal)
  {
    int[] symsFromCodeVal = new int[maxCodeVal + 1];
    for (int sym = 0; sym < codeLensFromSym.length; sym++)
    {
      int codeLen = codeLensFromSym[sym];
      if (codeLen != 0)
      {
        int tmp33_31 = codeLen; int[] tmp33_30 = codeValsFromCodeLen; int tmp35_34 = tmp33_30[tmp33_31];tmp33_30[tmp33_31] = (tmp35_34 + 1);int codeVal = tmp35_34;
        symsFromCodeVal[codeVal] = sym;
      }
    }
    return symsFromCodeVal;
  }
  
  public int readSym(ByteArray data, int[] bitIndex)
    throws FormatException
  {
    for (int codeLen = this.mMinCodeLen; codeLen <= this.mMaxCodeLen; codeLen++)
    {
      int maxCodeVal = this.mMaxCodeValsFromCodeLen[codeLen];
      if (maxCodeVal >= 0)
      {
        int codeVal = data.getHuffmanBits(bitIndex[0], codeLen);
        if (maxCodeVal >= codeVal)
        {
          int sym = this.mSymsFromCodeVal[codeVal];
          
          bitIndex[0] += codeLen;
          
          return sym;
        }
      }
    }
    String message = String.format("[%s] Bad code at the bit index '%d'.", new Object[] {
    
      getClass().getSimpleName(), Integer.valueOf(bitIndex[0]) });
    
    throw new FormatException(message);
  }
}
