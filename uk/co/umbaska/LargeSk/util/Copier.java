package uk.co.umbaska.LargeSk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Copier
{
  public void copy(InputStream in, File file)
  {
    try
    {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte['Ѐ'];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      out.close();
      in.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
