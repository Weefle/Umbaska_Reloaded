package uk.co.umbaska.ArmourStands.Direction.Arms;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprsRightArmDirectionX extends SimplePropertyExpression<Entity, Number>
{
  public Number convert(Entity ent)
  {
    if (ent == null)
      return null;
    return Double.valueOf(((ArmorStand)ent).getRightArmPose().getX());
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
      ((ArmorStand)ent).setRightArmPose(as.getRightArmPose().setX(b.doubleValue()));
    }
    if (mode == Changer.ChangeMode.ADD) {
      ArmorStand as = (ArmorStand)ent;
      ((ArmorStand)ent).setRightArmPose(as.getRightArmPose().setX(as.getRightArmPose().getX() + b.doubleValue()));
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      ArmorStand as = (ArmorStand)ent;
      ((ArmorStand)ent).setRightArmPose(as.getRightArmPose().setX(as.getRightArmPose().getX() - b.doubleValue()));
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    if (mode == Changer.ChangeMode.REMOVE)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    if (mode == Changer.ChangeMode.ADD)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  

  protected String getPropertyName()
  {
    return "right arm angle X";
  }
}
