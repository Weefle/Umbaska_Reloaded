package uk.co.umbaska.BossBars;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;



public class CondBarHasFlag
  extends Condition
{
  private Expression<BossBar> bar;
  private Expression<BarFlag> flag;
  
  public boolean check(Event event)
  {
    return ((BossBar)this.bar.getSingle(event)).hasFlag((BarFlag)this.flag.getSingle(event));
  }
  
  public String toString(Event event, boolean b)
  {
    return "bar has flag";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) expressions[0];
    this.flag = (Expression<BarFlag>) expressions[1];
    return true;
  }
}
