package uk.co.umbaska.Managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileManager
{
  public void loadScripts(String folder)
  {
    File in = new File(folder);
    ch.njol.skript.ScriptLoader.loadScripts(in);
  }
  
  public void createFile(String file) {
    File f = new File(file);
    if (f.exists()) {
      return;
    }
    try {
      f.getParentFile().mkdirs();
      f.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SecurityException s) {
      s.printStackTrace();
    }
  }
  
  public void deleteFile(String file)
  {
    File f = new File(file);
    if (f.exists()) {
      f.delete();
    } else {}
  }
  

  public String[] unrecursiveFileListing(String file)
  {
    List<String> files = new java.util.ArrayList();
    File folder = new File(file);
    for (File fileEntry : folder.listFiles()) {
      files.add(fileEntry.getName());
    }
    if (files.size() > 0) {
      String[] out = new String[files.size()];
      out = (String[])files.toArray(out);
      return out;
    }
    return null;
  }
  
  public String[] recursiveFileListing(String file)
  {
    List<String> files = new java.util.ArrayList();
    File folder = new File(file);
    for (File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory()) {
        for (String secondFile : recursiveFileListing(fileEntry.getPath())) {
          System.out.println(secondFile);
        }
      }
      files.add(fileEntry.getName());
    }
    if (files.size() > 0) {
      String[] out = new String[files.size()];
      out = (String[])files.toArray(out);
      return out;
    }
    return null;
  }
  
  public void setLineOfFile(String file, String text, Integer l) throws IOException
  {
    File f = new File(file);
    
    if (!f.exists())
      return;
    if (l.intValue() < 0) {
      return;
    }
    l = Integer.valueOf(l.intValue() - 1);
    List<String> sss = new java.util.ArrayList();
    BufferedReader br = new BufferedReader(new java.io.FileReader(file));
    try {
      String line;
      while ((line = br.readLine()) != null) {
        sss.add(line);
      }
    }
    finally {}
    
    br.close();
    String[] out = new String[sss.size()];
    out = (String[])sss.toArray(out);
    out[l.intValue()] = text;
    f.delete();
    f.createNewFile();
    for (String write : out) {
      writeToFile(file, write, Boolean.valueOf(false));
    }
  }
  
  public String getLineOfFile(String file, Integer l) throws java.io.FileNotFoundException, IOException
  {
    File f = new File(file);
    
    if (!f.exists())
      return null;
    if (l.intValue() < 0) {
      return null;
    }
    l = Integer.valueOf(l.intValue() - 1);
    List<String> sss = new java.util.ArrayList();
    BufferedReader br = new BufferedReader(new java.io.FileReader(file));Throwable localThrowable2 = null;
    try { String line;
      while ((line = br.readLine()) != null) {
        sss.add(line);
      }
    }
    catch (Throwable localThrowable1)
    {
      localThrowable2 = localThrowable1;throw localThrowable1;

    }
    finally
    {
      if (br != null) if (localThrowable2 != null) try { br.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else br.close(); }
    String[] out = new String[sss.size()];
    out = (String[])sss.toArray(out);
    return out[l.intValue()];
  }
  
  public String[] getLinesOfFile(String file) throws java.io.FileNotFoundException, IOException
  {
    File f = new File(file);
    
    if (!f.exists()) {
      return null;
    }
    List<String> sss = new java.util.ArrayList();
    BufferedReader br = new BufferedReader(new java.io.FileReader(file));Throwable localThrowable2 = null;
    try { String line;
      while ((line = br.readLine()) != null) {
        sss.add(line);
      }
    }
    catch (Throwable localThrowable1)
    {
      localThrowable2 = localThrowable1;throw localThrowable1;

    }
    finally
    {
      if (br != null) if (localThrowable2 != null) try { br.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else br.close(); }
    String[] out = new String[sss.size()];
    out = (String[])sss.toArray(out);
    return out;
  }
  

  public void writeToFile(String file, String text, Boolean overwrite)
  {
    if (fileExists(file)) {
      java.io.Writer output = null;
      try {
        if (overwrite.booleanValue() == true) {
          output = new java.io.BufferedWriter(new java.io.FileWriter(file, overwrite.booleanValue()));
        } else {
          output = new java.io.BufferedWriter(new java.io.FileWriter(file));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        output.append(text + System.getProperty("line.separator"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        output.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public boolean fileExists(String file)
  {
    File f = new File(file);
    if (f.exists()) {
      return true;
    }
    return false;
  }
}
