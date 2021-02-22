package uk.co.umbaska.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;







public class ExprBungeeServerCount
  extends SimpleExpression<Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return Bungee server count";
  }
  

  @Nullable
  protected Integer[] get(Event arg0)
  {
    Main.messenger.getServerCount("ALL");
    
    return new Integer[] { Integer.valueOf(Short.valueOf(Main.messenger.cache.playersOnline).intValue()) };
  }
}
