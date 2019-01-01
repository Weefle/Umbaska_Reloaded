package uk.co.umbaska.BossBars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;




public class ExprFlagsOfBar
  extends SimpleExpression<BarFlag>
{
  Expression<BossBar> bar;
  
  protected BarFlag[] get(Event event)
  {
    List<BarFlag> flags = new ArrayList<>();
    BossBar bar = (BossBar)this.bar.getSingle(event);
    for (BarFlag barFlag : BarFlag.values()) {
      if (bar.hasFlag(barFlag)) {
        flags.add(barFlag);
      }
    }
    return (BarFlag[])flags.toArray(new BarFlag[flags.size()]);
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends BarFlag> getReturnType()
  {
    return BarFlag.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "flags of boss bar";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) expressions[0];
    return true;
  }
}
