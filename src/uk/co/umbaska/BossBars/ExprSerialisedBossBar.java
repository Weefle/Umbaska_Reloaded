package uk.co.umbaska.BossBars;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import com.google.gson.Gson;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSerialisedBossBar
  extends SimpleExpression<String>
{
  private Expression<BossBar> bar;
  
  protected String[] get(Event event)
  {
    Gson gson = new Gson();
    String returnStr = gson.toJson(this.bar.getSingle(event));
    return new String[] { returnStr };
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "serialise boss bar";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) expressions[0];
    return true;
  }
}
