package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.server.ServerMemberBanListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildBanAddHandler
  extends PacketHandler
{
  public GuildBanAddHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_BAN_ADD");
  }
  
  public void handle(JSONObject packet)
  {
    final Server server = this.api.getServerById(packet.getString("guild_id"));
    final User user = this.api.getOrCreateUser(packet.getJSONObject("user"));
    if (server != null) {
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ServerMemberBanListener> listeners = GuildBanAddHandler.this.api.getListeners(ServerMemberBanListener.class);
          synchronized (listeners)
          {
            for (ServerMemberBanListener listener : listeners) {
              listener.onServerMemberBan(GuildBanAddHandler.this.api, user, server);
            }
          }
        }
      });
    }
  }
}
