package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.listener.server.ServerMemberUnbanListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildBanRemoveHandler
  extends PacketHandler
{
  public GuildBanRemoveHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_BAN_REMOVE");
  }
  
  public void handle(JSONObject packet)
  {
    final Server server = this.api.getServerById(packet.getString("guild_id"));
    final User user = this.api.getOrCreateUser(packet.getJSONObject("user"));
    if (server != null)
    {
      ((ImplServer)server).removeMember(user);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ServerMemberUnbanListener> listeners = GuildBanRemoveHandler.this.api.getListeners(ServerMemberUnbanListener.class);
          synchronized (listeners)
          {
            for (ServerMemberUnbanListener listener : listeners) {
              listener.onServerMemberUnban(GuildBanRemoveHandler.this.api, user.getId(), server);
            }
          }
        }
      });
    }
  }
}
