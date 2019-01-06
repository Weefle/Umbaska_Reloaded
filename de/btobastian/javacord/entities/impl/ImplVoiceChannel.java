package de.btobastian.javacord.entities.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.InviteBuilder;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplPermissions;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelChangeNameListener;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelDeleteListener;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplVoiceChannel
  implements VoiceChannel
{
  private static final Logger logger = LoggerUtil.getLogger(ImplVoiceChannel.class);
  private static final Permissions emptyPermissions = new ImplPermissions(0, 0);
  private final ImplDiscordAPI api;
  private final String id;
  private String name;
  private int position;
  private final ImplServer server;
  private final ConcurrentHashMap<String, Permissions> overwrittenPermissions = new ConcurrentHashMap();
  
  public ImplVoiceChannel(JSONObject data, ImplServer server, ImplDiscordAPI api)
  {
    this.api = api;
    this.server = server;
    
    this.id = data.getString("id");
    this.name = data.getString("name");
    this.position = data.getInt("position");
    
    JSONArray permissionOverwrites = data.getJSONArray("permission_overwrites");
    for (int i = 0; i < permissionOverwrites.length(); i++)
    {
      JSONObject permissionOverwrite = permissionOverwrites.getJSONObject(i);
      String id = permissionOverwrite.getString("id");
      int allow = permissionOverwrite.getInt("allow");
      int deny = permissionOverwrite.getInt("deny");
      String type = permissionOverwrite.getString("type");
      if (type.equals("role"))
      {
        Role role = server.getRoleById(id);
        if (role != null) {
          ((ImplRole)role).setOverwrittenPermissions(this, new ImplPermissions(allow, deny));
        }
      }
      if (type.equals("member")) {
        this.overwrittenPermissions.put(id, new ImplPermissions(allow, deny));
      }
    }
    server.addVoiceChannel(this);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getPosition()
  {
    return this.position;
  }
  
  public Server getServer()
  {
    return this.server;
  }
  
  public Future<Exception> delete()
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplVoiceChannel.logger.debug("Trying to delete voice channel {}", ImplVoiceChannel.this);
        try
        {
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/channels/" + ImplVoiceChannel.this.id).header("authorization", ImplVoiceChannel.this.api.getToken()).asJson();
          
          ImplVoiceChannel.this.api.checkResponse(response);
          ImplVoiceChannel.this.server.removeVoiceChannel(ImplVoiceChannel.this);
          ImplVoiceChannel.logger.info("Deleted voice channel {}", ImplVoiceChannel.this);
          
          ImplVoiceChannel.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<VoiceChannelDeleteListener> listeners = ImplVoiceChannel.this.api.getListeners(VoiceChannelDeleteListener.class);
              synchronized (listeners)
              {
                for (VoiceChannelDeleteListener listener : listeners) {
                  listener.onVoiceChannelDelete(ImplVoiceChannel.this.api, ImplVoiceChannel.this);
                }
              }
            }
          });
          return null;
        }
        catch (Exception e)
        {
          return e;
        }
      }
    });
  }
  
  public InviteBuilder getInviteBuilder()
  {
    return new ImplInviteBuilder(this, this.api);
  }
  
  public Permissions getOverwrittenPermissions(User user)
  {
    Permissions permissions = (Permissions)this.overwrittenPermissions.get(user.getId());
    return permissions == null ? emptyPermissions : permissions;
  }
  
  public Permissions getOverwrittenPermissions(Role role)
  {
    return role.getOverwrittenPermissions(this);
  }
  
  public Future<Exception> updateName(final String newName)
  {
    final JSONObject params = new JSONObject().put("name", newName);
    
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplVoiceChannel.logger.debug("Trying to update voice channel {} (new name: {}, old name: {})", new Object[] { ImplVoiceChannel.this, newName, ImplVoiceChannel.this.getName() });
        try
        {
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/channels/" + ImplVoiceChannel.this.getId()).header("authorization", ImplVoiceChannel.this.api.getToken()).header("Content-Type", "application/json").body(params.toString()).asJson();
          
          ImplVoiceChannel.this.api.checkResponse(response);
          String updatedName = ((JsonNode)response.getBody()).getObject().getString("name");
          ImplVoiceChannel.logger.debug("Updated voice channel {} (new name: {}, old name: {})", new Object[] { ImplVoiceChannel.this, updatedName, ImplVoiceChannel.this.getName() });
          if (!updatedName.equals(ImplVoiceChannel.this.getName()))
          {
            final String oldName = ImplVoiceChannel.this.getName();
            ImplVoiceChannel.this.setName(updatedName);
            ImplVoiceChannel.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<VoiceChannelChangeNameListener> listeners = ImplVoiceChannel.this.api.getListeners(VoiceChannelChangeNameListener.class);
                synchronized (listeners)
                {
                  for (VoiceChannelChangeNameListener listener : listeners) {
                    listener.onVoiceChannelChangeName(ImplVoiceChannel.this.api, ImplVoiceChannel.this, oldName);
                  }
                }
              }
            });
          }
        }
        catch (Exception e)
        {
          return e;
        }
        return null;
      }
    });
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setPosition(int position)
  {
    this.position = position;
  }
  
  public void setOverwrittenPermissions(User user, Permissions permissions)
  {
    this.overwrittenPermissions.put(user.getId(), permissions);
  }
  
  public String toString()
  {
    return getName() + " (id: " + getId() + ")";
  }
  
  public int hashCode()
  {
    return getId().hashCode();
  }
}
