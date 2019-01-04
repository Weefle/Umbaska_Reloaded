package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.GattSk.Extras.WorldManagers;









public class ExprLastCreatedWorld
  extends SimpleExpression<World>
{
  public Class<? extends World> getReturnType()
  {
    return World.class;
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
  protected World[] get(Event arg0)
  {
    return (World[])Collect.asArray(new World[] { WorldManagers.lastCreatedWorld });
  }
}
