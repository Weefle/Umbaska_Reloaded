package uk.co.umbaska.ParticleProjectiles.Expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;

public class ExprXOffset extends SimplePropertyExpression<String, Number>
{
  public Number convert(String ent)
  {
    if (ent == null)
      return null;
    if (ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).getXoff();
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
    ParticleProjectile p = (ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent);
    ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).setOffset(Double.valueOf(b.doubleValue()), p.getYoff(), p.getZoff());
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
