package ca.thederpygolems.armourequip;

import org.bukkit.inventory.ItemStack;






public enum ArmourType
{
  HELMET(5),  CHESTPLATE(6),  LEGGINGS(7),  BOOTS(8);
  
  private final int slot;
  
  private ArmourType(int slot) {
    this.slot = slot;
  }
  





  public static final ArmourType matchType(ItemStack itemStack)
  {
    if (itemStack == null) return null;
    switch (itemStack.getType()) {
    case DIAMOND_HELMET: 
    case GOLD_HELMET: 
    case IRON_HELMET: 
    case CHAINMAIL_HELMET: 
    case LEATHER_HELMET: 
      return HELMET;
    case DIAMOND_CHESTPLATE: 
    case GOLD_CHESTPLATE: 
    case IRON_CHESTPLATE: 
    case CHAINMAIL_CHESTPLATE: 
    case LEATHER_CHESTPLATE: 
      return CHESTPLATE;
    case DIAMOND_LEGGINGS: 
    case GOLD_LEGGINGS: 
    case IRON_LEGGINGS: 
    case CHAINMAIL_LEGGINGS: 
    case LEATHER_LEGGINGS: 
      return LEGGINGS;
    case DIAMOND_BOOTS: 
    case GOLD_BOOTS: 
    case IRON_BOOTS: 
    case CHAINMAIL_BOOTS: 
    case LEATHER_BOOTS: 
      return BOOTS;
    }
    return null;
  }
  
  public int getSlot()
  {
    return this.slot;
  }
}
