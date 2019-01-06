package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Region;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.listener.server.ServerChangeNameListener;
import de.btobastian.javacord.listener.server.ServerChangeRegionListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildUpdateHandler
  extends PacketHandler
{
  public GuildUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_UPDATE");
  }
  
  public void handle(JSONObject packet)
  {
    if ((packet.has("unavailable")) && (packet.getBoolean("unavailable"))) {
      return;
    }
    final ImplServer server = (ImplServer)this.api.getServerById(packet.getString("id"));
    
    String name = packet.getString("name");
    if (!server.getName().equals(name))
    {
      final String oldName = server.getName();
      server.setName(name);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ServerChangeNameListener> listeners = GuildUpdateHandler.this.api.getListeners(ServerChangeNameListener.class);
          synchronized (listeners)
          {
            for (ServerChangeNameListener listener : listeners) {
              listener.onServerChangeName(GuildUpdateHandler.this.api, server, oldName);
            }
          }
        }
      });
    }
    Region region = Region.getRegionByKey(packet.getString("region"));
    if (server.getRegion() != region)
    {
      final Region oldRegion = server.getRegion();
      server.setRegion(region);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ServerChangeRegionListener> listeners = GuildUpdateHandler.this.api.getListeners(ServerChangeRegionListener.class);
          synchronized (listeners)
          {
            for (ServerChangeRegionListener listener : listeners) {
              listener.onServerChangeRegion(GuildUpdateHandler.this.api, server, oldRegion);
            }
          }
        }
      });
    }
  }
}
