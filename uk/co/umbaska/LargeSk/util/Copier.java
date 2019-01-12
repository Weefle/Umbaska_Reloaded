package uk.co.umbaska.LargeSk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Copier {

  // Some stuff to copy some files from jar
  public void copy(InputStream in, File file) {
    try {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      out.close();
      in.close();
    } catch (Exception e) {
      // It shouldn't happen
      Xlog.logError("Mate, I really tried to copy something, but I couldn't");
      Xlog.logError("Report it on https://github.com/Nicofisi/LargeSk/issues please.");
      e.printStackTrace();
    }
  }
}