package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.listener.server.ServerJoinListener;
import de.btobastian.javacord.utils.PacketHandler;
import de.btobastian.javacord.utils.ThreadPool;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildCreateHandler
  extends PacketHandler
{
  public GuildCreateHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_CREATE");
  }
  
  public void handle(JSONObject packet)
  {
    if ((packet.has("unavailable")) && (packet.getBoolean("unavailable"))) {
      return;
    }
    final Server server = new ImplServer(packet, this.api);
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<ServerJoinListener> listeners = GuildCreateHandler.this.api.getListeners(ServerJoinListener.class);
        synchronized (listeners)
        {
          for (ServerJoinListener listener : listeners) {
            listener.onServerJoin(GuildCreateHandler.this.api, server);
          }
        }
      }
    });
    this.api.getThreadPool().getExecutorService().submit(new Runnable()
    {
      public void run()
      {
        GuildCreateHandler.this.api.getInternalServerJoinListener().onServerJoin(GuildCreateHandler.this.api, server);
      }
    });
  }
}
