package uk.co.umbaska.LargeSk.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtPlayerChunkChange
  extends Event
{
  Player player;
  Chunk from;
  Chunk to;
  
  public EvtPlayerChunkChange(Player player, Chunk from, Chunk to)
  {
    this.player = player;
    this.from = from;
    this.to = to;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public Chunk getFrom()
  {
    return this.from;
  }
  
  public Chunk getTo()
  {
    return this.to;
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
