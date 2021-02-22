package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.Enums.ParticleEnumArea;

public class ExprEffectCloudParticle
  extends SimplePropertyExpression<Entity, ParticleEnumArea>
{
  public ParticleEnumArea convert(Entity ent)
  {
    if (ent == null) {
      return null;
    }
    if (ent.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return null;
    }
    return ParticleEnumArea.getParticleFromBukkit(((AreaEffectCloud)ent).getParticle());
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Entity ent = (Entity)getExpr().getSingle(e);
    if (ent == null) {
      return;
    }
    if (ent.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return;
    }
    ParticleEnumArea b = (ParticleEnumArea)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ((AreaEffectCloud)ent).setParticle(b.getEffect());
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET) {
      return (Class[])CollectionUtils.array(new Class[] { ParticleEnumArea.class });
    }
    return null;
  }
  
  public Class<? extends ParticleEnumArea> getReturnType()
  {
    return ParticleEnumArea.class;
  }
  
  protected String getPropertyName()
  {
    return "area effect cloud particle";
  }
}
