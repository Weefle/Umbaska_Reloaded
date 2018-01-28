package uk.co.umbaska;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class VariableChangeEvent
  extends Event
  implements Cancellable
{
  private String variableName;
  private Object newValue;
  private boolean isCancelled;
  private static final HandlerList handlers = new HandlerList();
  
  public VariableChangeEvent(String variableName, Object newValue) {
    this.variableName = variableName;
    this.newValue = newValue;
    this.isCancelled = false;
  }
  
  public String getVariable() {
    return this.variableName;
  }
  
  public Object getNewValue() {
    return this.newValue;
  }
  
  public boolean isCancelled()
  {
    return this.isCancelled;
  }
  
  public void setCancelled(boolean arg0)
  {
    this.isCancelled = arg0;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
