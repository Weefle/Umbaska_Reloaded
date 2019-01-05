package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.expressions.base.SimplePropertyExpression;


public class ExprSplashPotionEntity
  extends SimplePropertyExpression<ItemStack, Entity>
{
  public String getPropertyName()
  {
    return "splash potion entity";
  }
  
  public Entity convert(ItemStack itemStack)
  {
    ThrownPotion thrownPotion = (ThrownPotion)((World)Bukkit.getWorlds().get(0)).spawnEntity(((World)Bukkit.getWorlds().get(0)).getSpawnLocation().subtract(0.0D, -5000.0D, 0.0D), EntityType.SPLASH_POTION);
    if (itemStack.getType() == Material.POTION) {
      thrownPotion.setItem(itemStack);
    }
    thrownPotion.remove();
    return thrownPotion;
  }
  
  public Class<? extends Entity> getReturnType() {
    return Entity.class;
  }
}
