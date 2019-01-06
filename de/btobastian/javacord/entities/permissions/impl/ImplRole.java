package de.btobastian.javacord.entities.permissions.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.role.RoleChangeColorListener;
import de.btobastian.javacord.listener.role.RoleChangeHoistListener;
import de.btobastian.javacord.listener.role.RoleChangeNameListener;
import de.btobastian.javacord.listener.role.RoleChangePermissionsListener;
import de.btobastian.javacord.listener.role.RoleDeleteListener;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplRole
  implements Role
{
  private static final Logger logger = LoggerUtil.getLogger(ImplRole.class);
  private static final Permissions emptyPermissions = new ImplPermissions(0, 0);
  private final ConcurrentHashMap<String, Permissions> overwrittenPermissions = new ConcurrentHashMap();
  private final ImplDiscordAPI api;
  private final String id;
  private String name;
  private final ImplServer server;
  private ImplPermissions permissions;
  private int position;
  private Color color;
  private boolean hoist;
  private final List<User> users = new ArrayList();
  
  public ImplRole(JSONObject data, ImplServer server, ImplDiscordAPI api)
  {
    this.server = server;
    this.api = api;
    
    this.id = data.getString("id");
    this.name = data.getString("name");
    this.permissions = new ImplPermissions(data.getInt("permissions"));
    this.position = data.getInt("position");
    this.color = new Color(data.getInt("color"));
    this.hoist = data.getBoolean("hoist");
    
    server.addRole(this);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Server getServer()
  {
    return this.server;
  }
  
  public Permissions getPermissions()
  {
    return this.permissions;
  }
  
  public Permissions getOverwrittenPermissions(Channel channel)
  {
    Permissions overwrittenPermissions = (Permissions)this.overwrittenPermissions.get(channel.getId());
    if (overwrittenPermissions == null) {
      overwrittenPermissions = emptyPermissions;
    }
    return overwrittenPermissions;
  }
  
  public Permissions getOverwrittenPermissions(VoiceChannel channel)
  {
    Permissions overwrittenPermissions = (Permissions)this.overwrittenPermissions.get(channel.getId());
    if (overwrittenPermissions == null) {
      overwrittenPermissions = emptyPermissions;
    }
    return overwrittenPermissions;
  }
  
  public List<User> getUsers()
  {
    return new ArrayList(this.users);
  }
  
  public int getPosition()
  {
    return this.position;
  }
  
  public boolean getHoist()
  {
    return this.hoist;
  }
  
  public Color getColor()
  {
    return this.color;
  }
  
  public Future<Exception> updatePermissions(Permissions permissions)
  {
    return update(this.name, this.color, this.hoist, permissions);
  }
  
  public Future<Exception> updateName(String name)
  {
    return update(name, this.color, this.hoist, this.permissions);
  }
  
  public Future<Exception> updateColor(Color color)
  {
    return update(this.name, color, this.hoist, this.permissions);
  }
  
  public Future<Exception> updateHoist(boolean hoist)
  {
    return update(this.name, this.color, hoist, this.permissions);
  }
  
  public Future<Exception> update(String name, Color color, boolean hoist, Permissions permissions)
  {
    if (name == null) {
      name = getName();
    }
    if (color == null) {
      color = getColor();
    }
    if (permissions == null) {
      permissions = getPermissions();
    }
    return update(name, color.getRGB(), hoist, ((ImplPermissions)permissions).getAllowed());
  }
  
  private Future<Exception> update(final String name, final int color, final boolean hoist, final int allow)
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplRole.logger.debug("Trying to update role {} (new name: {}, old name: {}, new color: {}, old color: {}, new hoist: {}, old hoist: {}, new allow: {}, old allow: {})", new Object[] { ImplRole.this, name, ImplRole.this.getName(), Integer.valueOf(color), Integer.valueOf(ImplRole.this.getColor().getRGB()), Boolean.valueOf(hoist), Boolean.valueOf(ImplRole.this.getHoist()), Integer.valueOf(allow), Integer.valueOf(ImplRole.this.permissions.getAllowed()) });
        try
        {
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/guilds/" + ImplRole.this.server.getId() + "/roles/" + ImplRole.this.id).header("authorization", ImplRole.this.api.getToken()).header("Content-Type", "application/json").body(new JSONObject().put("name", name).put("color", color).put("hoist", hoist).put("permissions", allow).toString()).asJson();
          
          ImplRole.this.api.checkResponse(response);
          
          ImplRole.logger.info("Updated role {} (new name: {}, old name: {}, new color: {}, old color: {}, new hoist: {}, old hoist: {}, new allow: {}, old allow: {})", new Object[] { ImplRole.this, name, ImplRole.this.getName(), Integer.valueOf(color), Integer.valueOf(ImplRole.this.getColor().getRGB()), Boolean.valueOf(hoist), Boolean.valueOf(ImplRole.this.getHoist()), Integer.valueOf(allow), Integer.valueOf(ImplRole.this.permissions.getAllowed()) });
          if (ImplRole.this.permissions.getAllowed() != allow)
          {
            final ImplPermissions oldPermissions = ImplRole.this.permissions;
            ImplRole.this.permissions = new ImplPermissions(allow);
            
            ImplRole.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<RoleChangePermissionsListener> listeners = ImplRole.this.api.getListeners(RoleChangePermissionsListener.class);
                synchronized (listeners)
                {
                  for (RoleChangePermissionsListener listener : listeners) {
                    listener.onRoleChangePermissions(ImplRole.this.api, ImplRole.this, oldPermissions);
                  }
                }
              }
            });
          }
          if (ImplRole.this.name.equals(name))
          {
            final String oldName = ImplRole.this.name;
            ImplRole.this.name = name;
            
            ImplRole.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<RoleChangeNameListener> listeners = ImplRole.this.api.getListeners(RoleChangeNameListener.class);
                synchronized (listeners)
                {
                  for (RoleChangeNameListener listener : listeners) {
                    listener.onRoleChangeName(ImplRole.this.api, ImplRole.this, oldName);
                  }
                }
              }
            });
          }
          if (ImplRole.this.color.getRGB() != new Color(color).getRGB())
          {
            final Color oldColor = ImplRole.this.color;
            ImplRole.this.color = new Color(color);
            
            ImplRole.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<RoleChangeColorListener> listeners = ImplRole.this.api.getListeners(RoleChangeColorListener.class);
                synchronized (listeners)
                {
                  for (RoleChangeColorListener listener : listeners) {
                    listener.onRoleChangeColor(ImplRole.this.api, ImplRole.this, oldColor);
                  }
                }
              }
            });
          }
          if (ImplRole.this.hoist != hoist)
          {
            ImplRole.this.hoist = hoist;
            
            ImplRole.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<RoleChangeHoistListener> listeners = ImplRole.this.api.getListeners(RoleChangeHoistListener.class);
                synchronized (listeners)
                {
                  for (RoleChangeHoistListener listener : listeners) {
                    listener.onRoleChangeHoist(ImplRole.this.api, ImplRole.this, !ImplRole.this.hoist);
                  }
                }
              }
            });
          }
          return null;
        }
        catch (Exception e)
        {
          return e;
        }
      }
    });
  }
  
  public Future<Exception> delete()
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplRole.logger.debug("Trying to delete role {}", ImplRole.this);
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/guilds/" + ImplRole.this.getServer().getId() + "/roles/" + ImplRole.this.getId()).header("authorization", ImplRole.this.api.getToken()).asJson();
          
          ImplRole.this.api.checkResponse(response);
          ImplRole.this.server.removeRole(ImplRole.this);
          ImplRole.logger.info("Deleted role {}", ImplRole.this);
          ImplRole.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<RoleDeleteListener> listeners = ImplRole.this.api.getListeners(RoleDeleteListener.class);
              synchronized (listeners)
              {
                for (RoleDeleteListener listener : listeners) {
                  listener.onRoleDelete(ImplRole.this.api, ImplRole.this);
                }
              }
            }
          });
        }
        catch (Exception e)
        {
          return e;
        }
        return null;
      }
    });
  }
  
  public Future<Exception> removeUser(User user)
  {
    List<Role> roles = new ArrayList(user.getRoles(getServer()));
    roles.remove(this);
    return getServer().updateRoles(user, (Role[])roles.toArray(new Role[roles.size()]));
  }
  
  public Future<Exception> addUser(User user)
  {
    List<Role> roles = new ArrayList(user.getRoles(getServer()));
    roles.add(this);
    return getServer().updateRoles(user, (Role[])roles.toArray(new Role[roles.size()]));
  }
  
  public void addUserNoUpdate(User user)
  {
    synchronized (this.users)
    {
      this.users.add(user);
    }
  }
  
  public void removeUserNoUpdate(User user)
  {
    synchronized (this.users)
    {
      this.users.remove(user);
    }
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setPermissions(ImplPermissions permissions)
  {
    this.permissions = permissions;
  }
  
  public void setPosition(int position)
  {
    this.position = position;
  }
  
  public void setOverwrittenPermissions(Channel channel, Permissions permissions)
  {
    this.overwrittenPermissions.put(channel.getId(), permissions);
  }
  
  public void setOverwrittenPermissions(VoiceChannel channel, Permissions permissions)
  {
    this.overwrittenPermissions.put(channel.getId(), permissions);
  }
  
  public void setColor(Color color)
  {
    this.color = color;
  }
  
  public void setHoist(boolean hoist)
  {
    this.hoist = hoist;
  }
  
  public String toString()
  {
    return getName() + " (id: " + getId() + ")";
  }
}
