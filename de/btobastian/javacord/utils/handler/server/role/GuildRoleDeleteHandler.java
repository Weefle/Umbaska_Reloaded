package de.btobastian.javacord.utils.handler.server.role;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.role.RoleDeleteListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildRoleDeleteHandler
  extends PacketHandler
{
  public GuildRoleDeleteHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_ROLE_DELETE");
  }
  
  public void handle(JSONObject packet)
  {
    String guildId = packet.getString("guild_id");
    String roleId = packet.getString("role_id");
    
    Server server = this.api.getServerById(guildId);
    final Role role = server.getRoleById(roleId);
    if (role == null) {
      return;
    }
    ((ImplServer)server).removeRole(role);
    
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<RoleDeleteListener> listeners = GuildRoleDeleteHandler.this.api.getListeners(RoleDeleteListener.class);
        synchronized (listeners)
        {
          for (RoleDeleteListener listener : listeners) {
            listener.onRoleDelete(GuildRoleDeleteHandler.this.api, role);
          }
        }
      }
    });
  }
}
