package de.btobastian.javacord.utils.handler.message;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.message.TypingStartListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONObject;

public class TypingStartHandler
  extends PacketHandler
{
  public TypingStartHandler(ImplDiscordAPI api)
  {
    super(api, true, "TYPING_START");
  }
  
  public void handle(JSONObject packet)
  {
    Channel channelTemp = null;
    String channelId = packet.getString("channel_id");
    Iterator<Server> serverIterator = this.api.getServers().iterator();
    while (serverIterator.hasNext())
    {
      channelTemp = ((Server)serverIterator.next()).getChannelById(channelId);
      if (channelTemp != null) {
        break;
      }
    }
    final Channel channel = channelTemp;
    
    String userId = packet.getString("user_id");
    final User user;
    try
    {
      user = (User)this.api.getUserById(userId).get();
    }
    catch (InterruptedException|ExecutionException e)
    {
      return;
    }
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<TypingStartListener> listeners = TypingStartHandler.this.api.getListeners(TypingStartListener.class);
        synchronized (listeners)
        {
          for (TypingStartListener listener : listeners) {
            listener.onTypingStart(TypingStartHandler.this.api, user, channel);
          }
        }
      }
    });
  }
}
