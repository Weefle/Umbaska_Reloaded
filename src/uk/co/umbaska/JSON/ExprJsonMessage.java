package uk.co.umbaska.JSON;

import ch.njol.skript.expressions.base.SimplePropertyExpression;


public class ExprJsonMessage
  extends SimplePropertyExpression<String, JSONMessage>
{
  protected String getPropertyName()
  {
    return "JSON equivalent";
  }
  
  public JSONMessage convert(String s)
  {
    return new JSONMessage(s);
  }
  
  public Class<? extends JSONMessage> getReturnType() {
    return JSONMessage.class;
  }
}
