package uk.co.umbaska.ParticleProjectiles.Expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.Enums.ParticleEnum;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;

public class ExprParticleType extends SimplePropertyExpression<String, ParticleEnum>
{
  public ParticleEnum convert(String ent)
  {
    if (ent == null)
      return null;
    if (ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).getParticleType();
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
    ParticleEnum b = (ParticleEnum)delta[0];
    ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).setParticleType(b);
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { ParticleEnum.class });
    return null;
  }
  
  public Class<? extends ParticleEnum> getReturnType()
  {
    return ParticleEnum.class;
  }
  

  protected String getPropertyName()
  {
    return "set ParticleEnum";
  }
}
