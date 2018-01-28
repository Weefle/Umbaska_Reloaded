package uk.co.umbaska.UUID;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;







public class ExprNamesOfPlayer
  extends SimpleExpression<String>
{
  private Expression<String> uuid;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.uuid = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return names of player";
  }
  
  @Nullable
  private static String NAME_HISTORY_URL = "https://api.mojang.com/user/profiles/";
  private static final JSONParser jsonParser = new JSONParser();
  
  protected String[] get(Event arg0) { String uuid = (String)this.uuid.getSingle(arg0);
    JSONArray array = null;
    try
    {
      HttpURLConnection connection = (HttpURLConnection)new URL(NAME_HISTORY_URL + uuid.toString().replace("-", "") + "/names").openConnection();
      array = (JSONArray)jsonParser.parse(new InputStreamReader(connection.getInputStream()));
    }
    catch (Exception ioe) {}
    

    String arr = array.toString();
    arr = arr.replace("{", "");
    arr = arr.replace("}", "");
    arr = arr.replace("[", "");
    arr = arr.replace("]", "");
    arr = arr.replace("\"", "");
    String[] ar = arr.split(",");
    Integer size = Integer.valueOf(ar.length);
    String a = null;
    Integer c = Integer.valueOf(-1);
    String[] name = null;
    for (int i = 0; i < size.intValue(); i++) {
      name = ar[i].split(":");
      if (name[0].toString().equalsIgnoreCase("name")) {
        c = Integer.valueOf(c.intValue() + 1);
        if (a == null) {
          a = name[1];
        } else {
          a = a + ", " + name[1];
        }
      }
    }
    return new String[] { a };
  }
}
