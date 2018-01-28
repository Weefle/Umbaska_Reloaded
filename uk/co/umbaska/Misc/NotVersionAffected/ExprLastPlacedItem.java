package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;


public class ExprLastPlacedItem
  extends SimpleExpression<Entity>
{
  public static Entity lastItem;
  
  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
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
    return "return Bungee players on server";
  }
  

  @Nullable
  protected Entity[] get(Event arg0)
  {
    if (lastItem != null) {
      return new Entity[] { lastItem };
    }
    return null;
  }
}
