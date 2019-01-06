package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.user.UserRoleAddListener;
import de.btobastian.javacord.listener.user.UserRoleRemoveListener;
import de.btobastian.javacord.utils.PacketHandler;
import de.btobastian.javacord.utils.ThreadPool;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONObject;

public class GuildMemberUpdateHandler
  extends PacketHandler
{
  public GuildMemberUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_MEMBER_UPDATE");
  }
  
  public void handle(JSONObject packet)
  {
    Server server = this.api.getServerById(packet.getString("guild_id"));
    final User user = this.api.getOrCreateUser(packet.getJSONObject("user"));
    if (server != null)
    {
      JSONArray jsonRoles = packet.getJSONArray("roles");
      Role[] roles = new Role[jsonRoles.length()];
      for (int i = 0; i < jsonRoles.length(); i++) {
        roles[i] = server.getRoleById(jsonRoles.getString(i));
      }
      for (final Role role : user.getRoles(server))
      {
        boolean contains = false;
        for (Role r : roles) {
          if (role == r)
          {
            contains = true;
            break;
          }
        }
        if (!contains)
        {
          ((ImplRole)role).removeUserNoUpdate(user);
          this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<UserRoleRemoveListener> listeners = GuildMemberUpdateHandler.this.api.getListeners(UserRoleRemoveListener.class);
              synchronized (listeners)
              {
                for (UserRoleRemoveListener listener : listeners) {
                  listener.onUserRoleRemove(GuildMemberUpdateHandler.this.api, user, role);
                }
              }
            }
          });
        }
      }
      for (final Role role : roles) {
        if (!user.getRoles(server).contains(role))
        {
          ((ImplRole)role).addUserNoUpdate(user);
          this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<UserRoleAddListener> listeners = GuildMemberUpdateHandler.this.api.getListeners(UserRoleAddListener.class);
              synchronized (listeners)
              {
                for (UserRoleAddListener listener : listeners) {
                  listener.onUserRoleAdd(GuildMemberUpdateHandler.this.api, user, role);
                }
              }
            }
          });
        }
      }
    }
  }
}
