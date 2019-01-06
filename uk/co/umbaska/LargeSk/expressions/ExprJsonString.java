package uk.co.umbaska.LargeSk.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.umbaska.LargeSk.util.Xlog;
import uk.co.umbaska.Registration.DontRegister;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Json String")
@Syntaxes({"[large[sk]] json (string|text) %string% from [json] %string%"})
@DontRegister
public class ExprJsonString
  extends SimpleUmbaskaExpression<String>
{
  private Expression<String> json;
  private Expression<String> string;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.string = expr[0];
    this.json = expr[1];
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
      JSONArray a = null;
      Xlog.logInfo("trying JSONArray");
      try
      {
        a = new JSONArray(j);
      }
      catch (JSONException ex2)
      {
        ex2.printStackTrace();
        Xlog.logInfo("array failed");
      }
    }
    return new String[] { o == null ? null : o.getString(s) };
  }
}
