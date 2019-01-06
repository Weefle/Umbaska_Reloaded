package uk.co.umbaska.Enums;

import org.bukkit.Particle;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

public enum ParticleEnumArea
{
  PARTICLE_TILE_DUST("tile dust", Particle.BLOCK_DUST),  PARTICLE_TILE_BREAK("tile break", Particle.BLOCK_CRACK),  PARTICLE_BLOCK_DUST("tile dust", Particle.BLOCK_DUST),  PARTICLE_BLOCK_BREAK("tile break", Particle.BLOCK_CRACK),  PARTICLE_ITEM_BREAK("item break", Particle.ITEM_CRACK),  PARTICLE_HUGE_EXPLOSION("hugeexplosion", Particle.EXPLOSION_HUGE),  PARTICLE_LARGE_EXPLODE("largeexplode", Particle.EXPLOSION_LARGE),  PARTICLE_FIREWORKS_SPARK("fireworksSpark", Particle.FIREWORKS_SPARK),  PARTICLE_SUSPENDED("suspended", Particle.SUSPENDED),  PARTICLE_DEPTH_SUSPEND("depthsuspend", Particle.SUSPENDED_DEPTH),  PARTICLE_CRIT("crit", Particle.CRIT),  PARTICLE_MAGIC_CRIT("magicCrit", Particle.CRIT_MAGIC),  PARTICLE_SMOKE("smoke", Particle.SMOKE_LARGE),  PARTICLE_MOB_SPELL("mobSpell", Particle.SPELL_MOB),  PARTICLE_MOB_SPELL_AMBIENT("mobSpellAmbient", Particle.SPELL_MOB_AMBIENT),  PARTICLE_SPELL("spell", Particle.SPELL),  PARTICLE_INSTANT_SPELL("instantSpell", Particle.SPELL_INSTANT),  PARTICLE_WITCH_MAGIC("witchMagic", Particle.SPELL_WITCH),  PARTICLE_NOTE("note", Particle.NOTE),  PARTICLE_PORTAL("portal", Particle.PORTAL),  PARTICLE_ENCHANTMENT_TABLE("enchantmenttable", Particle.ENCHANTMENT_TABLE),  PARTICLE_EXPLODE("explode", Particle.EXPLOSION_NORMAL),  PARTICLE_FLAME("flame", Particle.FLAME),  PARTICLE_LAVA("lava", Particle.LAVA),  PARTICLE_FOOTSTEP("footstep", Particle.FOOTSTEP),  PARTICLE_SPLASH("splash", Particle.WATER_SPLASH),  PARTICLE_LARGE_SMOKE("largesmoke", Particle.SMOKE_LARGE),  PARTICLE_CLOUD("cloud", Particle.CLOUD),  PARTICLE_RED_DUST("reddust", Particle.REDSTONE),  PARTICLE_SNOWBALL_POOF("snowballpoof", Particle.SNOWBALL),  PARTICLE_DRIP_WATER("dripWater", Particle.DRIP_WATER),  PARTICLE_DRIP_LAVA("dripLava", Particle.DRIP_LAVA),  PARTICLE_SNOW_SHOVEL("snowshovel", Particle.SNOW_SHOVEL),  PARTICLE_SLIME("slime", Particle.SLIME),  PARTICLE_HEART("heart", Particle.HEART),  PARTICLE_ANGRY_VILLAGER("angryVillager", Particle.VILLAGER_ANGRY),  PARTICLE_HAPPY_VILLAGER("happyVillager", Particle.VILLAGER_HAPPY),  PARTICLE_BARRIER("barrier", Particle.BARRIER),  PARTICLE_DAMAGE_INDICATOR("damageIndicator", Particle.DAMAGE_INDICATOR),  PARTICLE_DRAGON_BREATH("dragonBreath", Particle.DRAGON_BREATH),  PARTICLE_END_ROD("endRod", Particle.END_ROD),  PARTICLE_ITEM_TAKE("itemTake", Particle.ITEM_TAKE),  PARTICLE_MOB_APPEARANCE("mobAppearance", Particle.MOB_APPEARANCE),  PARTICLE_SWEEP_ATTACK("sweepAttack", Particle.SWEEP_ATTACK),  PARTICLE_TOWN_AURA("townAura", Particle.TOWN_AURA);
  
  private final String id;
  private final Particle effect;
  
  public static ParticleEnumArea getParticleFromBukkit(Particle particle)
  {
    for (ParticleEnumArea p : values()) {
      if (p.getEffect() == particle) {
        return p;
      }
    }
    return null;
  }
  
  private ParticleEnumArea(String id, @Nullable Particle effect)
  {
    this.id = id;
    this.effect = effect;
  }
  
  public Particle getEffect()
  {
    return this.effect;
  }
  
  public String getId()
  {
    return this.id;
  }
}
