package uk.co.umbaska.Misc;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;




public class WorldChangeEvent
  extends Event
{
  private World oldWorld;
  private World newWorld;
  private Entity e;
  
  public WorldChangeEvent(World oldWorld, World newWorld, Entity e)
  {
    this.oldWorld = oldWorld;
    this.newWorld = newWorld;
    this.e = e;
  }
  

  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  
  public World getOldWorld() {
    return this.oldWorld;
  }
  
  public World getNewWorld() {
    return this.newWorld;
  }
  
  public Entity getE() {
    return this.e;
  }
}
