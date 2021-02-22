package uk.co.umbaska.ParticleProjectiles;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class ParticleProjectileLandEvent
  extends Event
  implements Cancellable
{
  private String particleName;
  private Location landLocation;
  private boolean isCancelled;
  private static final HandlerList handlers = new HandlerList();
  
  public ParticleProjectileLandEvent(Location land, String particleName) {
    this.landLocation = land;
    this.particleName = particleName;
    this.isCancelled = false;
  }
  
  public Location getLocation() {
    return this.landLocation;
  }
  
  public String getParticleName() {
    return this.particleName;
  }
  

  public boolean isCancelled()
  {
    return this.isCancelled;
  }
  
  public void setCancelled(boolean arg0)
  {
    this.isCancelled = arg0;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
