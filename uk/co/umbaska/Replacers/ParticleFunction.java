package uk.co.umbaska.Replacers;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import uk.co.umbaska.Enums.BukkitEffectEnum;
import uk.co.umbaska.Enums.ParticleEnum;

public class ParticleFunction
{
  public static Boolean spawnEffect(BukkitEffectEnum effect, Location[] locations, Integer data, Integer secondaryData)
  {
    Effect eff = effect.getEffect();
    if (eff == null) {
      return Boolean.valueOf(false);
    }
    for (Location location : locations)
    {
      if (data == null) {
        location.getWorld().spigot().playEffect(location, eff);
      } else {
        location.getWorld().spigot().playEffect(location, eff, data.intValue(), secondaryData.intValue(), 1.0F, 1.0F, 1.0F, 1.0F, 1, 500);
      }
    }
    return Boolean.valueOf(true);
  }
  
  public static Boolean spawnEffect(BukkitEffectEnum effect, Location[] locations, Integer data, Player[] players, Integer secondaryData) {
    Effect eff = effect.getEffect();
    if (eff == null) {
      return Boolean.valueOf(false);
    }
    for (Player p : players) {
      for (Location location : locations)
      {
        p.spigot().playEffect(location, eff, data.intValue(), secondaryData.intValue(), 1.0F, 1.0F, 1.0F, 1.0F, 1, 500);
      }
    }
    return Boolean.valueOf(true);
  }
  
  public static Boolean spawnParticle(Integer count, ParticleEnum effect, Number speed, Number offsetx, Number offsety, Number offsetz, Location[] locations, Integer data, Integer secondaryData) {
    Effect eff = effect.getEffect();
    if (eff == null) {
      return Boolean.valueOf(false);
    }
    for (Location location : locations)
    {
      if (data == null) {
        location.getWorld().spigot().playEffect(location, eff);
      } else {
        location.getWorld().spigot().playEffect(location, eff, data.intValue(), secondaryData.intValue(), offsetx.floatValue(), offsety.floatValue(), offsetz.floatValue(), speed.floatValue(), count.intValue(), 500);
      }
    }
    return Boolean.valueOf(true);
  }
  
  public static Boolean spawnParticle(Integer count, ParticleEnum effect, Number speed, Number offsetx, Number offsety, Number offsetz, Location[] locations, Integer data, Player[] players, Integer secondaryData) {
    Effect eff = effect.getEffect();
    if (eff == null) {
      return Boolean.valueOf(false);
    }
    for (Player p : players) {
      for (Location location : locations)
      {
        p.spigot().playEffect(location, eff, data.intValue(), secondaryData.intValue(), offsetx.floatValue(), offsety.floatValue(), offsetz.floatValue(), speed.floatValue(), count.intValue(), 500);
      }
    }
    return Boolean.valueOf(true);
  }
}
