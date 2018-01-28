package uk.co.umbaska.Factions;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class EvtFactionDisbandEvent extends org.bukkit.event.Event
{
  Player player;
  String facname;
  
  public EvtFactionDisbandEvent(Player player, String facname)
  {
    this.player = player;
    this.facname = facname;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public String getFacName() {
    return this.facname;
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
