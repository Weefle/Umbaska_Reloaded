package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprWorldOfLocation
  extends SimplePropertyExpression<Location, World>
{
  public World convert(Location loc)
  {
    if (loc == null)
      return null;
    return loc.getWorld();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Location l = (Location)getExpr().getSingle(e);
    if (l == null)
      return;
    World w = (World)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      l.setWorld(w);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { World.class });
    return null;
  }
  
  public Class<? extends World> getReturnType()
  {
    return World.class;
  }
  

  protected String getPropertyName()
  {
    return "World of Location";
  }
}
