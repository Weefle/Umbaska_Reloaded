package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;


public class ExprClosestEntityFromLocation
  extends SimpleExpression<Entity>
{
  private Expression<Location> l;
  
  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.l = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return closest entity";
  }
  
  @Nullable
  protected Entity[] get(Event arg0)
  {
    Location st = (Location)this.l.getSingle(arg0);
    World w = st.getWorld();
    Entity closest = null;
    
    for (Entity e : w.getEntities()) {
      if (closest == null) {
        closest = e;
      }
      else if (e.getLocation().distance(st) < closest.getLocation().distance(st)) {
        closest = e;
      }
    }
    
    return new Entity[] { closest };
  }
}
