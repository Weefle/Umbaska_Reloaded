package uk.co.umbaska.Utils.Disguise;

import org.bukkit.entity.EntityType;



public enum EntityDisguise
{
  ZOMBIE(EntityType.ZOMBIE),  WITHER_SKELETON(EntityType.SKELETON),  SKELETON(EntityType.SKELETON),  ZOMBIEPIG(EntityType.PIG_ZOMBIE),  BLAZE(EntityType.BLAZE), 
  ENDERMAN(EntityType.ENDERMAN),  CREEPER(EntityType.CREEPER),  SPIDER(EntityType.SPIDER),  WITCH(EntityType.WITCH),  WITHER_BOSS(EntityType.WITHER), 
  GHAST(EntityType.GHAST),  GIANT(EntityType.GIANT),  SLIME(EntityType.SLIME),  CAVE_SPIDER(EntityType.CAVE_SPIDER),  SILVERFISH(EntityType.SILVERFISH), 
  MAGMA_CUBE(EntityType.MAGMA_CUBE),  BAT(EntityType.BAT),  PIG(EntityType.PIG),  SHEEP(EntityType.SHEEP),  COW(EntityType.COW),  CHICKEN(EntityType.CHICKEN), 
  SQUID(EntityType.SQUID),  WOLF(EntityType.WOLF),  OCELOT(EntityType.OCELOT),  HORSE(EntityType.HORSE),  VILLAGER(EntityType.VILLAGER),  IRON_GOLEM(EntityType.IRON_GOLEM), 
  SNOWMAN(EntityType.SNOWMAN),  ENDER_DRAGON(EntityType.ENDER_DRAGON),  MOOSHROOM(EntityType.MUSHROOM_COW),  UNKNOWN(EntityType.UNKNOWN),  ARMOR_STAND(EntityType.ARMOR_STAND), 
  GUARDIAN(EntityType.GUARDIAN),  ENDERMITE(EntityType.ENDERMITE),  ARROW(EntityType.ARROW),  BOAT(EntityType.BOAT),  EGG(EntityType.EGG),  ENDER_CRYSTAL(EntityType.ENDER_CRYSTAL), 
  ENDER_PEARL(EntityType.ENDER_PEARL),  EXPERIENCE_ORB(EntityType.EXPERIENCE_ORB),  FALLING_BLOCK(EntityType.FALLING_BLOCK),  PAINTING(EntityType.PAINTING),  ITEM_FRAME(EntityType.ITEM_FRAME);
  
  private final EntityType cls;
  
  private EntityDisguise(EntityType cls) { this.cls = cls; }
  





  public EntityType getEntityType()
  {
    return this.cls;
  }
}
