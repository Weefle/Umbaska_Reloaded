package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.expressions.base.SimplePropertyExpression;



public class ExprItemEntity
  extends SimplePropertyExpression<ItemStack, Entity>
{
  public String getPropertyName()
  {
    return "entity item";
  }
  
  public Entity convert(ItemStack itemStack)
  {
    Item itemEnt = ((World)Bukkit.getWorlds().get(0)).dropItem(((World)Bukkit.getWorlds().get(0)).getSpawnLocation(), itemStack);
    return itemEnt;
  }
  
  public Class<? extends Entity> getReturnType() {
    return Entity.class;
  }
}
