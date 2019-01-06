package de.btobastian.javacord.utils.handler.channel;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.impl.ImplChannel;
import de.btobastian.javacord.entities.impl.ImplVoiceChannel;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplPermissions;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.channel.ChannelChangeNameListener;
import de.btobastian.javacord.listener.channel.ChannelChangePositionListener;
import de.btobastian.javacord.listener.channel.ChannelChangeTopicListener;
import de.btobastian.javacord.listener.role.RoleChangeOverwrittenPermissionsListener;
import de.btobastian.javacord.listener.user.UserChangeOverwrittenPermissionsListener;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelChangeNameListener;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelChangePositionListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChannelUpdateHandler
  extends PacketHandler
{
  public ChannelUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "CHANNEL_UPDATE");
  }
  
  public void handle(JSONObject packet)
  {
    boolean isPrivate = packet.getBoolean("is_private");
    if (isPrivate) {
      return;
    }
    Server server = this.api.getServerById(packet.getString("guild_id"));
    if (packet.getString("type").equals("text")) {
      handleServerTextChannel(packet, server);
    } else {
      handleServerVoiceChannel(packet, server);
    }
  }
  
  private void handleServerTextChannel(JSONObject packet, Server server)
  {
    final Channel channel = server.getChannelById(packet.getString("id"));
    if (channel == null) {
      return;
    }
    String name = packet.getString("name");
    if (!channel.getName().equals(name))
    {
      final String oldName = channel.getName();
      ((ImplChannel)channel).setName(name);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ChannelChangeNameListener> listeners = ChannelUpdateHandler.this.api.getListeners(ChannelChangeNameListener.class);
          synchronized (listeners)
          {
            for (ChannelChangeNameListener listener : listeners) {
              listener.onChannelChangeName(ChannelUpdateHandler.this.api, channel, oldName);
            }
          }
        }
      });
    }
    String topic = packet.get("topic") == null ? null : packet.get("topic").toString();
    if (((channel.getTopic() != null) && (topic == null)) || ((channel.getTopic() == null) && (topic != null)) || ((channel.getTopic() != null) && (!channel.getTopic().equals(topic))))
    {
      final String oldTopic = channel.getTopic();
      ((ImplChannel)channel).setTopic(topic);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ChannelChangeTopicListener> listeners = ChannelUpdateHandler.this.api.getListeners(ChannelChangeTopicListener.class);
          synchronized (listeners)
          {
            for (ChannelChangeTopicListener listener : listeners) {
              listener.onChannelChangeTopic(ChannelUpdateHandler.this.api, channel, oldTopic);
            }
          }
        }
      });
    }
    int position = packet.getInt("position");
    if (channel.getPosition() != position)
    {
      final int oldPosition = channel.getPosition();
      ((ImplChannel)channel).setPosition(position);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<ChannelChangePositionListener> listeners = ChannelUpdateHandler.this.api.getListeners(ChannelChangePositionListener.class);
          synchronized (listeners)
          {
            for (ChannelChangePositionListener listener : listeners) {
              listener.onChannelChangePosition(ChannelUpdateHandler.this.api, channel, oldPosition);
            }
          }
        }
      });
    }
    JSONArray permissionOverwrites = packet.getJSONArray("permission_overwrites");
    for (int i = 0; i < permissionOverwrites.length(); i++)
    {
      JSONObject permissionOverwrite = permissionOverwrites.getJSONObject(i);
      int allow = permissionOverwrite.getInt("allow");
      int deny = permissionOverwrite.getInt("deny");
      String id = permissionOverwrite.getString("id");
      String type = permissionOverwrite.getString("type");
      if (type.equals("member"))
      {
        final User user;
        try
        {
          user = (User)this.api.getUserById(id).get();
        }
        catch (InterruptedException|ExecutionException e)
        {
          continue;
        }
        ImplPermissions permissions = new ImplPermissions(allow, deny);
        final Permissions oldPermissions = channel.getOverwrittenPermissions(user);
        if (!oldPermissions.equals(permissions))
        {
          ((ImplChannel)channel).setOverwrittenPermissions(user, permissions);
          this.listenerExecutorService.submit(new Runnable()
          {
            public void run()
            {
              List<UserChangeOverwrittenPermissionsListener> listeners = ChannelUpdateHandler.this.api.getListeners(UserChangeOverwrittenPermissionsListener.class);
              synchronized (listeners)
              {
                for (UserChangeOverwrittenPermissionsListener listener : listeners) {
                  listener.onUserChangeOverwrittenPermissions(ChannelUpdateHandler.this.api, user, channel, oldPermissions);
                }
              }
            }
          });
        }
      }
      if (type.equals("role"))
      {
        final Role role = channel.getServer().getRoleById(id);
        ImplPermissions permissions = new ImplPermissions(allow, deny);
        final Permissions oldPermissions = role.getOverwrittenPermissions(channel);
        if (!permissions.equals(oldPermissions))
        {
          ((ImplRole)role).setOverwrittenPermissions(channel, permissions);
          this.listenerExecutorService.submit(new Runnable()
          {
            public void run()
            {
              List<RoleChangeOverwrittenPermissionsListener> listeners = ChannelUpdateHandler.this.api.getListeners(RoleChangeOverwrittenPermissionsListener.class);
              synchronized (listeners)
              {
                for (RoleChangeOverwrittenPermissionsListener listener : listeners) {
                  listener.onRoleChangeOverwrittenPermissions(ChannelUpdateHandler.this.api, role, channel, oldPermissions);
                }
              }
            }
          });
        }
      }
    }
  }
  
  private void handleServerVoiceChannel(JSONObject packet, Server server)
  {
    final VoiceChannel channel = server.getVoiceChannelById(packet.getString("id"));
    if (channel == null) {
      return;
    }
    String name = packet.getString("name");
    if (!channel.getName().equals(name))
    {
      final String oldName = channel.getName();
      ((ImplVoiceChannel)channel).setName(name);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<VoiceChannelChangeNameListener> listeners = ChannelUpdateHandler.this.api.getListeners(VoiceChannelChangeNameListener.class);
          synchronized (listeners)
          {
            for (VoiceChannelChangeNameListener listener : listeners) {
              listener.onVoiceChannelChangeName(ChannelUpdateHandler.this.api, channel, oldName);
            }
          }
        }
      });
    }
    int position = packet.getInt("position");
    if (channel.getPosition() != position)
    {
      final int oldPosition = channel.getPosition();
      ((ImplVoiceChannel)channel).setPosition(position);
      this.listenerExecutorService.submit(new Runnable()
      {
        public void run()
        {
          List<VoiceChannelChangePositionListener> listeners = ChannelUpdateHandler.this.api.getListeners(VoiceChannelChangePositionListener.class);
          synchronized (listeners)
          {
            for (VoiceChannelChangePositionListener listener : listeners) {
              listener.onVoiceChannelChangePosition(ChannelUpdateHandler.this.api, channel, oldPosition);
            }
          }
        }
      });
    }
    JSONArray permissionOverwrites = packet.getJSONArray("permission_overwrites");
    for (int i = 0; i < permissionOverwrites.length(); i++)
    {
      JSONObject permissionOverwrite = permissionOverwrites.getJSONObject(i);
      int allow = permissionOverwrite.getInt("allow");
      int deny = permissionOverwrite.getInt("deny");
      String id = permissionOverwrite.getString("id");
      String type = permissionOverwrite.getString("type");
      if (type.equals("member"))
      {
        final User user;
        try
        {
          user = (User)this.api.getUserById(id).get();
        }
        catch (InterruptedException|ExecutionException e)
        {
          continue;
        }
        ImplPermissions permissions = new ImplPermissions(allow, deny);
        final Permissions oldPermissions = channel.getOverwrittenPermissions(user);
        if (!oldPermissions.equals(permissions))
        {
          ((ImplVoiceChannel)channel).setOverwrittenPermissions(user, permissions);
          this.listenerExecutorService.submit(new Runnable()
          {
            public void run()
            {
              List<UserChangeOverwrittenPermissionsListener> listeners = ChannelUpdateHandler.this.api.getListeners(UserChangeOverwrittenPermissionsListener.class);
              synchronized (listeners)
              {
                for (UserChangeOverwrittenPermissionsListener listener : listeners) {
                  listener.onUserChangeOverwrittenPermissions(ChannelUpdateHandler.this.api, user, channel, oldPermissions);
                }
              }
            }
          });
        }
      }
      if (type.equals("role"))
      {
        final Role role = channel.getServer().getRoleById(id);
        ImplPermissions permissions = new ImplPermissions(allow, deny);
        final Permissions oldPermissions = role.getOverwrittenPermissions(channel);
        if (!permissions.equals(oldPermissions))
        {
          ((ImplRole)role).setOverwrittenPermissions(channel, permissions);
          this.listenerExecutorService.submit(new Runnable()
          {
            public void run()
            {
              List<RoleChangeOverwrittenPermissionsListener> listeners = ChannelUpdateHandler.this.api.getListeners(RoleChangeOverwrittenPermissionsListener.class);
              synchronized (listeners)
              {
                for (RoleChangeOverwrittenPermissionsListener listener : listeners) {
                  listener.onRoleChangeOverwrittenPermissions(ChannelUpdateHandler.this.api, role, channel, oldPermissions);
                }
              }
            }
          });
        }
      }
    }
  }
}
