package uk.co.umbaska.LargeSk.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.LargeSk.util.Xlog;

public class ExprJsonString
  extends SimpleExpression<String>
{
  private Expression<String> json;
  private Expression<String> string;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.string = (Expression<String>) expr[0];
    this.json = (Expression<String>) expr[1];
    return true;
  }
  
  protected String[] get(Event e)
  {
    String j = (String)this.json.getSingle(e);
    String s = (String)this.string.getSingle(e);
    JSONObject o = null;
    Xlog.logInfo("trying JSONObject");
    try
    {
      o = new JSONObject(j);
    }
    catch (JSONException ex)
    {
      ex.printStackTrace();
      Xlog.logInfo("error");
      Xlog.logInfo("trying JSONArray");
      try
      {
        new JSONArray(j);
      }
      catch (JSONException ex2)
      {
        ex2.printStackTrace();
        Xlog.logInfo("array failed");
      }
    }
    return new String[] { o == null ? null : o.getString(s) };
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Json String";
}
}
