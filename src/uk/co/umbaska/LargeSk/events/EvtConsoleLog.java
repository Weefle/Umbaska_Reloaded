package uk.co.umbaska.LargeSk.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtConsoleLog
  extends Event
  implements Cancellable
{
  private String logMsg;
  private boolean cancelled;
  
  public EvtConsoleLog(String logMsg)
  {
    this.logMsg = logMsg;
  }
  
  public String getMessage()
  {
    return this.logMsg;
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
  
  public boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public void setCancelled(boolean b)
  {
    this.cancelled = b;
  }
}
