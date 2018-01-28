package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.Scanner;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;








public class ExprGetJSONString
  extends SimpleExpression<String>
{
  private Expression<String> input;
  private Expression<String> string;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.input = args[0];
    this.string = args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get JSON string";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    String in = (String)this.input.getSingle(arg0);
    String s = (String)this.string.getSingle(arg0);
    
    if (in == null)
      return null;
    if (s == null) {
      return null;
    }
    Scanner scanner = new Scanner(in);
    JSONArray array = new JSONArray(scanner.next());
    JSONObject object = array.getJSONObject(0);
    String out = object.getString(s);
    scanner.close();
    return new String[] { out };
  }
}
