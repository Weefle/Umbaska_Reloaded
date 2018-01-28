package uk.co.umbaska.Misc;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtHeadRotateEvent extends Event implements Cancellable
{
  Player player;
  private boolean cancelled;
  
  public EvtHeadRotateEvent(Player player)
  {
    this.player = player;
    this.cancelled = false;
  }
  
  public Player getPlayer() {
    return this.player;
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
