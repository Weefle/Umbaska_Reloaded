package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.listener.server.ServerLeaveListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildDeleteHandler
  extends PacketHandler
{
  public GuildDeleteHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_DELETE");
  }
  
  public void handle(JSONObject packet)
  {
    final Server server = this.api.getServerById(packet.getString("id"));
    if (server == null) {
      return;
    }
    this.api.getServerMap().remove(server.getId());
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<ServerLeaveListener> listeners = GuildDeleteHandler.this.api.getListeners(ServerLeaveListener.class);
        synchronized (listeners)
        {
          for (ServerLeaveListener listener : listeners) {
            listener.onServerLeave(GuildDeleteHandler.this.api, server);
          }
        }
      }
    });
  }
}
