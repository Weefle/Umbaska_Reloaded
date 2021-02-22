package uk.co.umbaska.System;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class ExprFreeMemory extends ch.njol.skript.lang.util.SimpleExpression<Integer>
{
  protected Integer[] get(Event event)
  {
    long l = Runtime.getRuntime().freeMemory();
    int i = 0;
    if ((l >= -2147483648L) && (l <= 2147483647L)) i = (int)l;
    return new Integer[] { Integer.valueOf(i) };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends Integer> getReturnType() {
    return Integer.class;
  }
  
  public String toString(Event event, boolean b) {
    return getClass().getName();
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    return true;
  }
}
