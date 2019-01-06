package de.btobastian.javacord.entities.message;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract interface MessageHistory
{
  public abstract Message getMessageById(String paramString);
  
  public abstract Iterator<Message> iterator();
  
  public abstract Collection<Message> getMessages();
  
  public abstract Message getNewestMessage();
  
  public abstract Message getOldestMessage();
  
  public abstract List<Message> getMessagesSorted();
}
