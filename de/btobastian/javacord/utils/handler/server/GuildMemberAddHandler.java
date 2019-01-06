package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.listener.server.ServerMemberAddListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildMemberAddHandler
  extends PacketHandler
{
  public GuildMemberAddHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_MEMBER_ADD");
  }
  
  public void handle(JSONObject packet)
  {
    final Server server = this.api.getServerById(packet.getString("guild_id"));
    final User user = this.api.getOrCreateUser(packet.getJSONObject("user"));
    if (server != null)
    {
      ((ImplServer)server).addMember(user);
      ((ImplServer)server).incrementMemberCount();
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ServerMemberAddListener> listeners = GuildMemberAddHandler.this.api.getListeners(ServerMemberAddListener.class);
          synchronized (listeners)
          {
            for (ServerMemberAddListener listener : listeners) {
              listener.onServerMemberAdd(GuildMemberAddHandler.this.api, user, server);
            }
          }
        }
      });
    }
  }
}
