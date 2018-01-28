package uk.co.umbaska.ParticleProjectiles.Expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import java.util.HashMap;
import org.bukkit.event.Event;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;

public class ExprGravity extends SimplePropertyExpression<String, Number>
{
  public Number convert(String ent)
  {
    if (ent == null)
      return null;
    if (ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).getGravity();
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
    Number b = (Number)delta[0];
    ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).setGravity(Double.valueOf(b.doubleValue()));
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  

  protected String getPropertyName()
  {
    return "set Number";
  }
}
