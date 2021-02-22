package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;








public class ExprMaxPlayers
  extends SimpleExpression<Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "faction of player";
  }
  
  @Nullable
  protected Integer[] get(Event arg0)
  {
    return new Integer[] { Integer.valueOf(Bukkit.getMaxPlayers()) };
  }
}
