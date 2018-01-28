package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.ItemMeta.Spigot;

public class ExprUnbreakable extends SimplePropertyExpression<ItemStack, ItemStack>
{
  public String getPropertyName()
  {
    return "unbreakable";
  }
  
  public ItemStack convert(ItemStack itemStack)
  {
    ItemStack item = new ItemStack(itemStack);
    ItemMeta im = item.getItemMeta();
    
    im.spigot().setUnbreakable(true);
    
    item.setItemMeta(im);
    
    return item;
  }
  
  public Class<? extends ItemStack> getReturnType() {
    return ItemStack.class;
  }
}
