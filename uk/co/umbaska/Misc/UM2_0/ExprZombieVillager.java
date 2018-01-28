package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;

public class ExprZombieVillager
  extends SimplePropertyExpression<Entity, Boolean>
{
  public Boolean convert(Entity ent)
  {
    if (ent == null)
      return null;
    return Boolean.valueOf(((Zombie)ent).isVillager());
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Entity ent = (Entity)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (ent.getType() != EntityType.ZOMBIE) {
      return;
    }
    Boolean b = (Boolean)delta[0];
    ((Zombie)ent).setVillager(b.booleanValue());
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
    return null;
  }
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  

  protected String getPropertyName()
  {
    return "Zombie is villager";
  }
}
