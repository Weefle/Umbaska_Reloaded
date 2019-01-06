package uk.co.umbaska.UmbAccess;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UMethod
{
  public List<UParam> params = new ArrayList<>();
  public Method meth;
  
  public UMethod(Method m)
  {
    this.meth = m;
  }
  
  public void addParam(Class<?> param, Object val)
  {
    UParam up = new UParam(param, val);
    if (this.params.contains(up)) {
      return;
    }
    this.params.add(up);
  }
  
  public Method getMethod()
  {
    return this.meth;
  }
  
  public UParam[] toArray()
  {
    return (UParam[])this.params.toArray(new UParam[this.params.size()]);
  }
}
