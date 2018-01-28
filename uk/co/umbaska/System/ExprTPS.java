package uk.co.umbaska.System;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.TPSUtil;

public class ExprTPS
  extends SimpleExpression<Double>
{
  protected Double[] get(Event event)
  {
    return new Double[] { Double.valueOf(new TPSUtil(Main.plugin).getTPS()) };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends Double> getReturnType() {
    return Double.class;
  }
  
  public String toString(Event event, boolean b) {
    return getClass().getName();
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    return true;
  }
}
