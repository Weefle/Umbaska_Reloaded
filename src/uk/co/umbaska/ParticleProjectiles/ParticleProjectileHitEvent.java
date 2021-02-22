package uk.co.umbaska.ParticleProjectiles;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class ParticleProjectileHitEvent
  extends Event
  implements Cancellable
{
  private String particleName;
  private Entity victim;
  private boolean isCancelled;
  private static final HandlerList handlers = new HandlerList();
  
  public ParticleProjectileHitEvent(Entity victim, String particleName) {
    this.victim = victim;
    this.particleName = particleName;
    this.isCancelled = false;
  }
  
  public Entity getVictim() {
    return this.victim;
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
