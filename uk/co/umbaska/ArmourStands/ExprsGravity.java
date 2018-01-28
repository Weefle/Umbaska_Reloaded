package uk.co.umbaska.ArmourStands;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

public class ExprsGravity
  extends SimplePropertyExpression<Entity, Boolean>
{
  public Boolean convert(Entity ent)
  {
    if (ent == null)
      return null;
    return Boolean.valueOf(((ArmorStand)ent).hasGravity());
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
      ((ArmorStand)ent).setGravity(b.booleanValue());
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
