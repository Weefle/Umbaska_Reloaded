package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprOffhandItem
  extends SimplePropertyExpression<Entity, ItemStack>
{
  public ItemStack convert(Entity ent)
  {
    if (ent == null) {
      return null;
    }
    return ((LivingEntity)ent).getEquipment().getItemInOffHand();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Entity ent = (Entity)getExpr().getSingle(e);
    if (ent == null) {
      return;
    }
    ItemStack b = (ItemStack)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ((LivingEntity)ent).getEquipment().setItemInOffHand(b);
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET) {
      return (Class[])CollectionUtils.array(new Class[] { ItemStack.class });
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      return (Class[])CollectionUtils.array(new Class[] { ItemStack.class });
    }
    return null;
  }
  
  public Class<? extends ItemStack> getReturnType()
  {
    return ItemStack.class;
  }
  
  protected String getPropertyName()
  {
    return "area effect cloud particle";
  }
}
