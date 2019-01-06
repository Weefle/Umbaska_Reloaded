package uk.co.umbaska.UmbAccess;

public class UParam
{
  public Class<?> clazz;
  public Object value;
  
  public UParam(Class<?> cl, Object val)
  {
    this.clazz = cl;
    this.value = val;
  }
  
  public Class<?> getClassType()
  {
    return this.clazz;
  }
  
  public Object getValue()
  {
    return this.value;
  }
}
