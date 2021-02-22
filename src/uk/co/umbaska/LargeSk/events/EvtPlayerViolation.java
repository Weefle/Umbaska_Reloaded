package uk.co.umbaska.LargeSk.events;

import me.konsolas.aac.api.HackType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtPlayerViolation
  extends Event
{
  Player player;
  HackType hack;
  String message;
  Integer violations;
  
  public EvtPlayerViolation(Player player, HackType hack, String message, Integer violations)
  {
    this.player = player;
    this.hack = hack;
    this.message = message;
    this.violations = violations;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public HackType getHack()
  {
    return this.hack;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public Integer getViolations()
  {
    return this.violations;
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
