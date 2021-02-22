package uk.co.umbaska.Factions;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtFactionNameChangeEvent extends Event
{
  Player player;
  Faction fac;
  
  public EvtFactionNameChangeEvent(Player player, Faction fac)
  {
    this.player = player;
    this.fac = fac;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Faction getFac() {
    return this.fac;
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
