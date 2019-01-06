package com.mashape.unirest.http.utils;

public class URLParamEncoder
{
  public static String encode(String input)
  {
    StringBuilder resultStr = new StringBuilder();
    for (char ch : input.toCharArray()) {
      if (isUnsafe(ch))
      {
        resultStr.append('%');
        resultStr.append(toHex(ch / '\020'));
        resultStr.append(toHex(ch % '\020'));
      }
      else
      {
        resultStr.append(ch);
      }
    }
    return resultStr.toString();
  }
  
  private static char toHex(int ch)
  {
    return (char)(ch < 10 ? 48 + ch : 65 + ch - 10);
  }
  
  private static boolean isUnsafe(char ch)
  {
    if ((ch > '') || (ch < 0)) {
      return true;
    }
    return " %$&+,/:;=?@<>#%".indexOf(ch) >= 0;
  }
}
