package uk.co.umbaska.ParticleProjectiles.Expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;
import uk.co.umbaska.ParticleProjectiles.ProjectileType;

public class ExprProjectileType extends SimplePropertyExpression<String, ProjectileType>
{
  public ProjectileType convert(String ent)
  {
    if (ent == null)
      return null;
    if (ParticleProjectileHandler.particleProjectiles.containsKey(ent)) {
      return ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).getType();
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
    ProjectileType b = (ProjectileType)delta[0];
    ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(ent)).setType(b);
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { ProjectileType.class });
    return null;
  }
  
  public Class<? extends ProjectileType> getReturnType()
  {
    return ProjectileType.class;
  }
  

  protected String getPropertyName()
  {
    return "set ProjectileType";
  }
}
