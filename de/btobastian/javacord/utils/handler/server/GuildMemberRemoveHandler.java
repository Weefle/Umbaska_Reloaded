package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.listener.server.ServerMemberRemoveListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildMemberRemoveHandler
  extends PacketHandler
{
  public GuildMemberRemoveHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_MEMBER_REMOVE");
  }
  
  public void handle(JSONObject packet)
  {
    final Server server = this.api.getServerById(packet.getString("guild_id"));
    final User user = this.api.getOrCreateUser(packet.getJSONObject("user"));
    if (server != null)
    {
      ((ImplServer)server).removeMember(user);
      ((ImplServer)server).decrementMemberCount();
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ServerMemberRemoveListener> listeners = GuildMemberRemoveHandler.this.api.getListeners(ServerMemberRemoveListener.class);
          synchronized (listeners)
          {
            for (ServerMemberRemoveListener listener : listeners) {
              listener.onServerMemberRemove(GuildMemberRemoveHandler.this.api, user, server);
            }
          }
        }
      });
    }
  }
}
