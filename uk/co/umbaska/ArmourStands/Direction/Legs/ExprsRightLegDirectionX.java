package uk.co.umbaska.ArmourStands.Direction.Legs;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.util.EulerAngle;

public class ExprsRightLegDirectionX extends SimplePropertyExpression<Entity, Number>
{
  public Number convert(Entity ent)
  {
    if (ent == null)
      return null;
    return Double.valueOf(((ArmorStand)ent).getRightLegPose().getX());
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Entity ent = (Entity)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (ent.getType() != EntityType.ARMOR_STAND) {
      return;
    }
    Number b = (Number)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ArmorStand as = (ArmorStand)ent;
      ((ArmorStand)ent).setRightLegPose(as.getRightLegPose().setX(b.doubleValue()));
    }
    if (mode == Changer.ChangeMode.ADD) {
      ArmorStand as = (ArmorStand)ent;
      ((ArmorStand)ent).setRightLegPose(as.getRightLegPose().setX(as.getRightLegPose().getX() + b.doubleValue()));
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      ArmorStand as = (ArmorStand)ent;
      ((ArmorStand)ent).setRightLegPose(as.getRightLegPose().setX(as.getRightLegPose().getX() - b.doubleValue()));
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    if (mode == Changer.ChangeMode.ADD)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    if (mode == Changer.ChangeMode.REMOVE)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  

  protected String getPropertyName()
  {
    return "right leg angle X";
  }
}
