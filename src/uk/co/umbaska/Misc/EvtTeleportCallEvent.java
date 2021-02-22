package uk.co.umbaska.Misc;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class EvtTeleportCallEvent extends org.bukkit.event.Event
{
  Player player;
  
  public EvtTeleportCallEvent(Player player)
  {
    this.player = player;
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
}
