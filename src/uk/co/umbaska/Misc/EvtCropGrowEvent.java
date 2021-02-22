package uk.co.umbaska.Misc;

import ch.njol.skript.util.BlockStateBlock;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtCropGrowEvent
  extends Event implements Cancellable
{
  BlockState block;
  private boolean cancelled;
  
  public EvtCropGrowEvent(BlockState block)
  {
    this.block = block;
    this.cancelled = false;
  }
  


  public BlockStateBlock getBlock()
  {
    return new BlockStateBlock(this.block);
  }
  
  private static final HandlerList handlers = new HandlerList();
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
}
