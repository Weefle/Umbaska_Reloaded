package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;










public class ExprSpawnReason
  extends SimpleExpression<String>
{
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
    if (!ScriptLoader.isCurrentEvent(CreatureSpawnEvent.class)) {
      return false;
    }
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "entity spawn reason";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    return new String[] { ((CreatureSpawnEvent)arg0).getSpawnReason().toString() };
  }
}
