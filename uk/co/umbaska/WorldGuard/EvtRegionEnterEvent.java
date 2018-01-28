package uk.co.umbaska.WorldGuard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtRegionEnterEvent
  extends Event implements Cancellable
{
  Player player;
  ProtectedRegion region;
  private boolean cancelled;
  
  public EvtRegionEnterEvent(ProtectedRegion region, Player player)
  {
    this.player = player;
    this.region = region;
    this.cancelled = false;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public ProtectedRegion getRegion() {
    return this.region;
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  
  public boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel)
  {
    this.cancelled = cancel;
  }
}
