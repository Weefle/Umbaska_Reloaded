package uk.co.umbaska.Misc.UM2_0;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;


public class ExprItemStackSkullOwner
  extends SimplePropertyExpression<ItemStack, String>
{
  public String convert(ItemStack ent)
  {
    if (ent == null)
      return null;
    SkullMeta sk = (SkullMeta)ent.getItemMeta();
    return sk.getOwner();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ItemStack ent = (ItemStack)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (ent.getType() != Material.SKULL) {
      return;
    }
    String b = (String)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      SkullMeta sk = (SkullMeta)ent.getItemMeta();
      sk.setOwner(b);
      ent.setItemMeta(sk);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    return null;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  

  protected String getPropertyName()
  {
    return "skull owner ItemStack";
  }
}
