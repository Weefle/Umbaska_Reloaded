package uk.co.umbaska.GattSk.Extras;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Collect
{
  public static String[] toStringArray(Object[] array)
  {
    String[] strings = new String[array.length];
    for (int i = 0; i < strings.length; i++) strings[i] = array[i].toString();
    return strings;
  }
  
  public static String[] toFriendlyStringArray(Object[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < strings.length; i++) strings[i] = array[i].toString().toLowerCase().replace("_", " ");
    return strings;
  }
  
  @SafeVarargs
  public static <T> T[] asArray(T... objects) {
    return objects;
  }
  
  public static <T> T[] newArray(Class<?> type, int size)
  {
    return (T[])java.lang.reflect.Array.newInstance(type, size);
  }
  
  public static String[] asSkriptProperty(String property, String fromType) {
    return (String[])asArray(new String[] { "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property });
  }
  
  public static <T> String toString(T[] array) {
    return toString(array, ',');
  }
  
  public static <T> String toString(T[] array, char separator) {
    return toString(array, separator, true);
  }
  
  public static <T> String toString(T[] array, char separator, boolean spaces)
  {
    String SEPARATOR = spaces ? " " : "";
    if (array == null)
      return "null";
    int max = array.length - 1;
    if (max == -1)
      return "";
    String b = "";
    for (int i = 0;; i++) {
      b = b + String.valueOf(array[i]);
      if (i == max)
        return b;
      b = b + separator + SEPARATOR;
    }
  }
  
  public static String getPaths(java.util.Collection<String> list) {
    StringBuilder builder = new StringBuilder();
    for (String s : list) {
      builder.append(s).append(".");
    }
    return builder.toString().substring(0, builder.length() - 1);
  }
  
  public static String textPart(InputStream is)
  {
    if (is == null) return "";
    Scanner s = new Scanner(is).useDelimiter("\\A");Throwable localThrowable2 = null;
    try { return s.hasNext() ? s.next() : "";
    }
    catch (Throwable localThrowable3)
    {
      localThrowable2 = localThrowable3;throw localThrowable3;
    } finally {
      if (s != null) if (localThrowable2 != null) try { s.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else s.close();
    }
  }
  
  private static ArrayList<File> getListFiles(File root, FilenameFilter filter, ArrayList<File> toAdd) { for (File f : root.listFiles(filter)) {
      if (f.isDirectory()) return getListFiles(f, filter, toAdd);
      toAdd.add(f);
    }
    return toAdd;
  }
  
  public static File[] getFiles(File root, FilenameFilter filter) {
    ArrayList<File> files = getListFiles(root, filter, new ArrayList());
    return (File[])files.toArray(new File[files.size()]);
  }
  
  public static File[] getFiles(File root) {
    return getFiles(root, null);
  }
}
