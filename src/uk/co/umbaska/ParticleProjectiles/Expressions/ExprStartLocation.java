package uk.co.umbaska.ParticleProjectiles.Expressions;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;

public class ExprStartLocation extends SimplePropertyExpression<String, Location>
{
  public Location convert(String ent)
  {
    if (ent == null)
      return null;
    if (ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).getStartLocation();
    }
    return null;
  }
  

  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    String ent = (String)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (!ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return;
    }
    Location b = (Location)delta[0];
    ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).setStartLocation(b);
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Location.class });
    return null;
  }
  
  public Class<? extends Location> getReturnType()
  {
    return Location.class;
  }
  

  protected String getPropertyName()
  {
    return "set start location";
  }
}
