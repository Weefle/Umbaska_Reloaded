package uk.co.umbaska.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;





public class ExprEntityFromUUID
  extends SimpleExpression<Entity>
{
  private Expression<String> uuid;
  
  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.uuid = (Expression<String>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return entity from uuid";
  }
  


  @Nullable
  protected Entity[] get(Event arg0)
  {
    for (World w : Bukkit.getWorlds()) {
      for (Entity e : w.getEntities()) {
        if (e.getUniqueId().toString() == this.uuid.getSingle(arg0)) {
          return new Entity[] { e };
        }
      }
    }
    return null;
  }
}
