package uk.co.umbaska.Factions;

import com.massivecraft.factions.entity.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtFactionDescriptionChangeEvent
  extends Event
{
  Player player;
  Faction fac;
  String newdesc;
  
  public EvtFactionDescriptionChangeEvent(Player player, Faction fac, String newdesc)
  {
    this.player = player;
    this.fac = fac;
    this.newdesc = newdesc;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Faction getFac() {
    return this.fac;
  }
  
  public String getNewDesc() {
    return this.newdesc;
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
