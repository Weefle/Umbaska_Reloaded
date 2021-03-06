package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprEffectCloudWaitTime
  extends SimplePropertyExpression<Entity, Number>
{
  public Number convert(Entity ent)
  {
    if (ent == null) {
      return null;
    }
    if (ent.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return null;
    }
    return Integer.valueOf(((AreaEffectCloud)ent).getWaitTime());
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
    Integer b = Integer.valueOf(((Number)delta[0]).intValue());
    if (mode == Changer.ChangeMode.SET) {
      ((AreaEffectCloud)ent).setWaitTime(b.intValue());
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET) {
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    }
    return null;
  }
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  
  protected String getPropertyName()
  {
    return "area effect cloud wait time";
  }
}
