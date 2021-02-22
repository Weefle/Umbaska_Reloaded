package uk.co.umbaska.Misc.UM2_0;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;


public class ExprClosestEntity
  extends SimpleExpression<Entity>
{
  private Expression<Entity> entity;
  
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
    this.entity = (Expression<Entity>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return closest entity";
  }
  
  @Nullable
  protected Entity[] get(Event arg0) {
    Location st = ((Entity)this.entity.getSingle(arg0)).getLocation();
    World w = st.getWorld();
    Entity closest = null;
    
    for (Entity e : w.getEntities()) {
      if (e != this.entity.getSingle(arg0)) {
        if (closest == null) {
          closest = e;
        }
        else if (e.getLocation().distance(st) < closest.getLocation().distance(st)) {
          closest = e;
        }
      }
    }
    
    return new Entity[] { closest };
  }
}
