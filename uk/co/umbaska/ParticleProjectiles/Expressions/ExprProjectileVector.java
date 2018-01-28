package uk.co.umbaska.ParticleProjectiles.Expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import java.util.HashMap;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;

public class ExprProjectileVector extends SimplePropertyExpression<String, Vector>
{
  public Vector convert(String ent)
  {
    if (ent == null)
      return null;
    if (ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).getVector();
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
    Vector b = (Vector)delta[0];
    ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).setVector(b);
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Vector.class });
    return null;
  }
  
  public Class<? extends Vector> getReturnType()
  {
    return Vector.class;
  }
  

  protected String getPropertyName()
  {
    return "set Vector";
  }
}
