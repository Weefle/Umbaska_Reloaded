package uk.co.umbaska.Discord;

import de.btobastian.javacord.entities.message.Message;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtDiscordMessage
  extends Event
  implements Cancellable
{
  private Message message;
  private boolean cancelled;
  
  public EvtDiscordMessage(Message message)
  {
    this.message = message;
  }
  
  public Message getMessage()
  {
    return this.message;
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
    if (b) {
      this.message.delete();
    }
  }
}
