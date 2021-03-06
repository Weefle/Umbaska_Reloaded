package uk.co.umbaska.ArmourStands;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprsArms
  extends SimplePropertyExpression<Entity, Boolean>
{
  public Boolean convert(Entity ent)
  {
    if (ent == null)
      return null;
    return Boolean.valueOf(((ArmorStand)ent).hasArms());
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Entity ent = (Entity)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (ent.getType() != EntityType.ARMOR_STAND) {
      return;
    }
    Boolean b = (Boolean)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ((ArmorStand)ent).setArms(b.booleanValue());
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
    if (mode == Changer.ChangeMode.REMOVE)
      return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
    return null;
  }
  
  public Class<? extends Boolean> getReturnType() {
    return Boolean.class;
  }
  

  protected String getPropertyName()
  {
    return "gravity";
  }
}
