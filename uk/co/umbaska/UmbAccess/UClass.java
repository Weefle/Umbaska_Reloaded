package uk.co.umbaska.UmbAccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ch.njol.skript.Skript;

public class UClass
{
  public String sclass;
  public Class<?> cclass;
  public UMethod umethod;
  
  public UClass(String c)
  {
    this.sclass = c;
    try
    {
      this.cclass = Class.forName(c);
    }
    catch (ClassNotFoundException e)
    {
      Skript.error("[Umbaska] The class: " + c + "is not valid!");
      this.cclass = null;
    }
  }
  
  public void setUMethod(Object method)
  {
    if ((method instanceof String)) {
      this.umethod = new UMethod(getMethod((String)method));
    } else if ((method instanceof Method)) {
      this.umethod = new UMethod((Method)method);
    } else {}
  }
  
  public void addParam(String clazz, Object value)
  {
    if (this.umethod == null)
    {
      Skript.error("[Umbaska] Attempted to add a parameter before method is set!");
      return;
    }
    try
    {
      this.umethod.addParam(Class.forName(clazz), value);
    }
    catch (ClassNotFoundException e)
    {
      Skript.error("[Umbaska] The class: " + clazz + "is not valid!");
    }
  }
  
  public void accessMethod()
  {
    if (this.umethod == null)
    {
      Skript.error("[Umbaska] Attempted to access a method before method is set!");
      return;
    }
    UParam[] uparams = this.umethod.toArray();
    Class<?>[] params = new Class[uparams.length];
    Object[] values = new Object[uparams.length];
    Integer i = Integer.valueOf(0);
    for (UParam up : uparams)
    {
      params[i.intValue()] = up.getClassType();
      values[i.intValue()] = up.getValue();
    }
    try
    {
      Object instance = this.cclass.newInstance();
      Method m = this.cclass.getDeclaredMethod(this.umethod.getMethod().getName(), params);
      m.invoke(instance, (Object[])values);
    }
    catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|InstantiationException e)
    {
      e.printStackTrace();
    }
  }
  
  public String[] getMethodsAsStrings()
  {
    if (this.cclass == null) {
      return null;
    }
    Method[] m = this.cclass.getDeclaredMethods();
    String[] s = new String[m.length];
    Integer i = Integer.valueOf(0);
    for (Method c : m)
    {
      s[i.intValue()] = c.getName();
    }
    return s;
  }
  
  public Method[] getMethods()
  {
    if (this.cclass == null) {
      return null;
    }
    return this.cclass.getDeclaredMethods();
  }
  
  public String getMethodAsString(String method)
  {
    String[] s = getMethodsAsStrings();
    if (s == null) {
      return null;
    }
    String found = null;
    for (String c : s) {
      if (c == method)
      {
        found = c;
        break;
      }
    }
    if (found != null) {
      return found;
    }
    return null;
  }
  
  public Method getMethod(String method)
  {
    Method[] s = getMethods();
    if (s == null) {
      return null;
    }
    Method found = null;
    for (Method c : s) {
      if (c.getName() == method)
      {
        found = c;
        break;
      }
    }
    if (found != null) {
      return found;
    }
    return null;
  }
}
