package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprOffhandItemPlayer
  extends SimplePropertyExpression<Player, ItemStack>
{
  public ItemStack convert(Player ent)
  {
    if (ent == null) {
      return null;
    }
    return ent.getInventory().getItemInOffHand();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player ent = (Player)getExpr().getSingle(e);
    if (ent == null) {
      return;
    }
    ItemStack b = (ItemStack)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ent.getInventory().setItemInOffHand(b);
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
