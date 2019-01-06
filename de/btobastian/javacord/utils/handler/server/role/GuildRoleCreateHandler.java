package de.btobastian.javacord.utils.handler.server.role;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.role.RoleCreateListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildRoleCreateHandler
  extends PacketHandler
{
  public GuildRoleCreateHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_ROLE_CREATE");
  }
  
  public void handle(JSONObject packet)
  {
    String guildId = packet.getString("guild_id");
    JSONObject roleJson = packet.getJSONObject("role");
    
    Server server = this.api.getServerById(guildId);
    final Role role = new ImplRole(roleJson, (ImplServer)server, this.api);
    
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<RoleCreateListener> listeners = GuildRoleCreateHandler.this.api.getListeners(RoleCreateListener.class);
        synchronized (listeners)
        {
          for (RoleCreateListener listener : listeners) {
            listener.onRoleCreate(GuildRoleCreateHandler.this.api, role);
          }
        }
      }
    });
  }
}
