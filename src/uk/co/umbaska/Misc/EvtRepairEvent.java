package uk.co.umbaska.Misc;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class EvtRepairEvent extends Event implements Cancellable
{
  Player player;
  ItemStack item;
  private boolean cancelled;
  
  public EvtRepairEvent(Player player, ItemStack item)
  {
    this.player = player;
    this.item = item;
    this.cancelled = false;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public ItemStack getItem() {
    return this.item;
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
