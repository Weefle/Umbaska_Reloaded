package de.btobastian.javacord.utils.handler.server.role;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplPermissions;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.role.RoleChangeColorListener;
import de.btobastian.javacord.listener.role.RoleChangeHoistListener;
import de.btobastian.javacord.listener.role.RoleChangeNameListener;
import de.btobastian.javacord.listener.role.RoleChangePermissionsListener;
import de.btobastian.javacord.listener.role.RoleChangePositionListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class GuildRoleUpdateHandler
  extends PacketHandler
{
  public GuildRoleUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "GUILD_ROLE_UPDATE");
  }
  
  public void handle(JSONObject packet)
  {
    String guildId = packet.getString("guild_id");
    JSONObject roleJson = packet.getJSONObject("role");
    
    Server server = this.api.getServerById(guildId);
    final ImplRole role = (ImplRole)server.getRoleById(roleJson.getString("id"));
    
    String name = roleJson.getString("name");
    if (!role.getName().equals(name))
    {
      final String oldName = role.getName();
      role.setName(name);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<RoleChangeNameListener> listeners = GuildRoleUpdateHandler.this.api.getListeners(RoleChangeNameListener.class);
          synchronized (listeners)
          {
            for (RoleChangeNameListener listener : listeners) {
              listener.onRoleChangeName(GuildRoleUpdateHandler.this.api, role, oldName);
            }
          }
        }
      });
    }
    Permissions permissions = new ImplPermissions(roleJson.getInt("permissions"));
    if (!role.getPermissions().equals(permissions))
    {
      final Permissions oldPermissions = role.getPermissions();
      role.setPermissions((ImplPermissions)permissions);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<RoleChangePermissionsListener> listeners = GuildRoleUpdateHandler.this.api.getListeners(RoleChangePermissionsListener.class);
          synchronized (listeners)
          {
            for (RoleChangePermissionsListener listener : listeners) {
              listener.onRoleChangePermissions(GuildRoleUpdateHandler.this.api, role, oldPermissions);
            }
          }
        }
      });
    }
    Color color = new Color(roleJson.getInt("color"));
    if (role.getColor().getRGB() != color.getRGB())
    {
      final Color oldColor = role.getColor();
      role.setColor(color);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<RoleChangeColorListener> listeners = GuildRoleUpdateHandler.this.api.getListeners(RoleChangeColorListener.class);
          synchronized (listeners)
          {
            for (RoleChangeColorListener listener : listeners) {
              listener.onRoleChangeColor(GuildRoleUpdateHandler.this.api, role, oldColor);
            }
          }
        }
      });
    }
    if (role.getHoist() != roleJson.getBoolean("hoist"))
    {
      role.setHoist(!role.getHoist());
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<RoleChangeHoistListener> listeners = GuildRoleUpdateHandler.this.api.getListeners(RoleChangeHoistListener.class);
          synchronized (listeners)
          {
            for (RoleChangeHoistListener listener : listeners) {
              listener.onRoleChangeHoist(GuildRoleUpdateHandler.this.api, role, !role.getHoist());
            }
          }
        }
      });
    }
    synchronized (Role.class)
    {
      int position = roleJson.getInt("position");
      if (role.getPosition() != position)
      {
        final int oldPosition = role.getPosition();
        role.setPosition(position);
        this.listenerExecutorService.submit(new Runnable()
        {
          public void run()
          {
            List<RoleChangePositionListener> listeners = GuildRoleUpdateHandler.this.api.getListeners(RoleChangePositionListener.class);
            synchronized (listeners)
            {
              for (RoleChangePositionListener listener : listeners) {
                listener.onRoleChangePosition(GuildRoleUpdateHandler.this.api, role, oldPosition);
              }
            }
          }
        });
      }
    }
  }
}
