package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.Color;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprEffectCloudColor
  extends SimplePropertyExpression<Entity, Color>
{
  public Color convert(Entity ent)
  {
    if (ent == null) {
      return null;
    }
    if (ent.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return null;
    }
    return ((AreaEffectCloud)ent).getColor();
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
    Color b = (Color)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ((AreaEffectCloud)ent).setColor(b);
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET) {
      return (Class[])CollectionUtils.array(new Class[] { Color.class });
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      return (Class[])CollectionUtils.array(new Class[] { Color.class });
    }
    return null;
  }
  
  public Class<? extends Color> getReturnType()
  {
    return Color.class;
  }
  
  protected String getPropertyName()
  {
    return "area effect cloud color";
  }
}
