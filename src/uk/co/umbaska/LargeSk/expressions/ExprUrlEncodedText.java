package uk.co.umbaska.LargeSk.expressions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprUrlEncodedText
  extends SimpleExpression<String>
{
  private Expression<String> string;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.string = (Expression<String>) expr[0];
    return true;
  }
  
  @Nullable
  protected String[] get(Event e)
  {
    try
    {
      return new String[] { URLEncoder.encode(((String)this.string.getSingle(e)).toString(), "UTF-8") };
    }
    catch (UnsupportedEncodingException e1)
    {
      e1.printStackTrace();
    }
    return null;
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Url Encoded Text";
}
}
