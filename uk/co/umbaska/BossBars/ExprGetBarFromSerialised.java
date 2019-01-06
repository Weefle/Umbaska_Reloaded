package uk.co.umbaska.BossBars;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import com.google.gson.Gson;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetBarFromSerialised
  extends SimpleExpression<BossBar>
{
  private Expression<String> data;
  
  protected BossBar[] get(Event event)
  {
    Gson gson = new Gson();
    BossBar bar = (BossBar)gson.fromJson((String)this.data.getSingle(event), BossBar.class);
    return new BossBar[] { bar };
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends BossBar> getReturnType()
  {
    return BossBar.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "unserialise boss bar";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.data = (Expression<String>) expressions[0];
    
    return true;
  }
}
