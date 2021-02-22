package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public abstract class Disguise
{
  private Entity player;
  private EntityType type;
  
  protected Disguise(Entity player, EntityType type)
  {
    this.player = player;
    this.type = type;
  }
  
  public abstract void applyDisguise(Collection<Player> paramCollection);
  
  public abstract void revertDisguise(Collection<Player> paramCollection);
  
  public abstract void move(Location paramLocation1, Location paramLocation2);
  
  public final Entity getPlayer() {
    return this.player;
  }
  
  public final EntityType getType() {
    return this.type;
  }
}
