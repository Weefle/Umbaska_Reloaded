package de.btobastian.javacord.entities.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Invite;
import de.btobastian.javacord.entities.Region;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplPermissions;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.channel.ChannelCreateListener;
import de.btobastian.javacord.listener.role.RoleCreateListener;
import de.btobastian.javacord.listener.server.ServerChangeNameListener;
import de.btobastian.javacord.listener.server.ServerLeaveListener;
import de.btobastian.javacord.listener.server.ServerMemberBanListener;
import de.btobastian.javacord.listener.server.ServerMemberRemoveListener;
import de.btobastian.javacord.listener.server.ServerMemberUnbanListener;
import de.btobastian.javacord.listener.user.UserRoleAddListener;
import de.btobastian.javacord.listener.user.UserRoleRemoveListener;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelCreateListener;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplServer
  implements Server
{
  private static final Logger logger = LoggerUtil.getLogger(ImplServer.class);
  private final ImplDiscordAPI api;
  private final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap();
  private final ConcurrentHashMap<String, VoiceChannel> voiceChannels = new ConcurrentHashMap();
  private final ConcurrentHashMap<String, User> members = new ConcurrentHashMap();
  private final ConcurrentHashMap<String, Role> roles = new ConcurrentHashMap();
  private final String id;
  private String name;
  private Region region;
  private int memberCount;
  private final boolean large;
  
  public ImplServer(JSONObject data, ImplDiscordAPI api)
  {
    this.api = api;
    
    this.name = data.getString("name");
    this.id = data.getString("id");
    this.region = Region.getRegionByKey(data.getString("region"));
    this.memberCount = data.getInt("member_count");
    this.large = data.getBoolean("large");
    
    JSONArray roles = data.getJSONArray("roles");
    for (int i = 0; i < roles.length(); i++) {
      new ImplRole(roles.getJSONObject(i), this, api);
    }
    JSONArray channels = data.getJSONArray("channels");
    for (int i = 0; i < channels.length(); i++)
    {
      JSONObject channelJson = channels.getJSONObject(i);
      String type = channelJson.getString("type");
      if (type.equals("text")) {
        new ImplChannel(channels.getJSONObject(i), this, api);
      }
      if (type.equals("voice")) {
        new ImplVoiceChannel(channels.getJSONObject(i), this, api);
      }
    }
    JSONArray members = new JSONArray();
    if (data.has("members")) {
      members = data.getJSONArray("members");
    }
    for (int i = 0; i < members.length(); i++)
    {
      User member = api.getOrCreateUser(members.getJSONObject(i).getJSONObject("user"));
      this.members.put(member.getId(), member);
      
      JSONArray memberRoles = members.getJSONObject(i).getJSONArray("roles");
      for (int j = 0; j < memberRoles.length(); j++)
      {
        Role role = getRoleById(memberRoles.getString(j));
        if (role != null) {
          ((ImplRole)role).addUserNoUpdate(member);
        }
      }
    }
    JSONArray presences = new JSONArray();
    if (data.has("presences")) {
      presences = data.getJSONArray("presences");
    }
    for (int i = 0; i < presences.length(); i++)
    {
      JSONObject presence = presences.getJSONObject(i);
      User user = api.getCachedUserById(presence.getJSONObject("user").getString("id"));
      if ((user != null) && (presence.has("game")) && (!presence.isNull("game")) && 
        (presence.getJSONObject("game").has("name")) && (!presence.getJSONObject("game").isNull("name"))) {
        ((ImplUser)user).setGame(presence.getJSONObject("game").getString("name"));
      }
    }
    api.getServerMap().put(this.id, this);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
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
          ImplServer.logger.debug("Trying to delete server {}", ImplServer.this);
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/guilds/" + ImplServer.this.id).header("authorization", ImplServer.this.api.getToken()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          ImplServer.this.api.getServerMap().remove(ImplServer.this.id);
          ImplServer.logger.info("Deleted server {}", ImplServer.this);
          ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<ServerLeaveListener> listeners = ImplServer.this.api.getListeners(ServerLeaveListener.class);
              synchronized (listeners)
              {
                for (ServerLeaveListener listener : listeners) {
                  listener.onServerLeave(ImplServer.this.api, ImplServer.this);
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
  
  public Future<Exception> leave()
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplServer.logger.debug("Trying to leave server {}", ImplServer.this);
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/users/@me/guilds/" + ImplServer.this.id).header("authorization", ImplServer.this.api.getToken()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          ImplServer.this.api.getServerMap().remove(ImplServer.this.id);
          ImplServer.logger.info("Left server {}", ImplServer.this);
          ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<ServerLeaveListener> listeners = ImplServer.this.api.getListeners(ServerLeaveListener.class);
              synchronized (listeners)
              {
                for (ServerLeaveListener listener : listeners) {
                  listener.onServerLeave(ImplServer.this.api, ImplServer.this);
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
  
  public Channel getChannelById(String id)
  {
    return (Channel)this.channels.get(id);
  }
  
  public Collection<Channel> getChannels()
  {
    return Collections.unmodifiableCollection(this.channels.values());
  }
  
  public VoiceChannel getVoiceChannelById(String id)
  {
    return (VoiceChannel)this.voiceChannels.get(id);
  }
  
  public Collection<VoiceChannel> getVoiceChannels()
  {
    return Collections.unmodifiableCollection(this.voiceChannels.values());
  }
  
  public User getMemberById(String id)
  {
    return (User)this.members.get(id);
  }
  
  public Collection<User> getMembers()
  {
    return Collections.unmodifiableCollection(this.members.values());
  }
  
  public boolean isMember(User user)
  {
    return isMember(user.getId());
  }
  
  public boolean isMember(String userId)
  {
    return this.members.containsKey(userId);
  }
  
  public Collection<Role> getRoles()
  {
    return Collections.unmodifiableCollection(this.roles.values());
  }
  
  public Role getRoleById(String id)
  {
    return (Role)this.roles.get(id);
  }
  
  public Future<Channel> createChannel(String name)
  {
    return createChannel(name, null);
  }
  
  public Future<Channel> createChannel(final String name, FutureCallback<Channel> callback)
  {
    ListenableFuture<Channel> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Channel call()
        throws Exception
      {
        final Channel channel = (Channel)ImplServer.this.createChannelBlocking(name, false);
        ImplServer.logger.info("Created channel in server {} (name: {}, voice: {}, id: {})", new Object[] { ImplServer.this, channel.getName(), Boolean.valueOf(false), channel.getId() });
        
        ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
        {
          public void run()
          {
            List<ChannelCreateListener> listeners = ImplServer.this.api.getListeners(ChannelCreateListener.class);
            synchronized (listeners)
            {
              for (ChannelCreateListener listener : listeners) {
                listener.onChannelCreate(ImplServer.this.api, channel);
              }
            }
          }
        });
        return channel;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<VoiceChannel> createVoiceChannel(String name)
  {
    return createVoiceChannel(name, null);
  }
  
  public Future<VoiceChannel> createVoiceChannel(final String name, FutureCallback<VoiceChannel> callback)
  {
    ListenableFuture<VoiceChannel> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public VoiceChannel call()
        throws Exception
      {
        final VoiceChannel channel = (VoiceChannel)ImplServer.this.createChannelBlocking(name, true);
        ImplServer.logger.info("Created channel in server {} (name: {}, voice: {}, id: {})", new Object[] { ImplServer.this, channel.getName(), Boolean.valueOf(true), channel.getId() });
        
        ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
        {
          public void run()
          {
            List<VoiceChannelCreateListener> listeners = ImplServer.this.api.getListeners(VoiceChannelCreateListener.class);
            synchronized (listeners)
            {
              for (VoiceChannelCreateListener listener : listeners) {
                listener.onVoiceChannelCreate(ImplServer.this.api, channel);
              }
            }
          }
        });
        return channel;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Invite[]> getInvites()
  {
    return getInvites(null);
  }
  
  public Future<Invite[]> getInvites(FutureCallback<Invite[]> callback)
  {
    ListenableFuture<Invite[]> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Invite[] call()
        throws Exception
      {
        ImplServer.logger.debug("Trying to get invites for server {}", ImplServer.this);
        HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/guilds/" + ImplServer.this.getId() + "/invites").header("authorization", ImplServer.this.api.getToken()).asJson();
        
        ImplServer.this.api.checkResponse(response);
        Invite[] invites = new Invite[((JsonNode)response.getBody()).getArray().length()];
        for (int i = 0; i < ((JsonNode)response.getBody()).getArray().length(); i++) {
          invites[i] = new ImplInvite(ImplServer.this.api, ((JsonNode)response.getBody()).getArray().getJSONObject(i));
        }
        ImplServer.logger.debug("Got invites for server {} (amount: {})", ImplServer.this, Integer.valueOf(invites.length));
        return invites;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Exception> updateRoles(final User user, final Role[] roles)
  {
    final String[] roleIds = new String[roles.length];
    for (int i = 0; i < roles.length; i++) {
      roleIds[i] = roles[i].getId();
    }
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplServer.logger.debug("Trying to update roles in server {} (amount: {})", ImplServer.this, Integer.valueOf(roles.length));
        try
        {
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/guilds/" + ImplServer.this.getId() + "/members/" + user.getId()).header("authorization", ImplServer.this.api.getToken()).header("Content-Type", "application/json").body(new JSONObject().put("roles", roleIds).toString()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          for (final Role role : user.getRoles(ImplServer.this))
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
              ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
              {
                public void run()
                {
                  List<UserRoleRemoveListener> listeners = ImplServer.this.api.getListeners(UserRoleRemoveListener.class);
                  synchronized (listeners)
                  {
                    for (UserRoleRemoveListener listener : listeners) {
                      listener.onUserRoleRemove(ImplServer.this.api, ImplServer.6.this.val$user, role);
                    }
                  }
                }
              });
            }
          }
          for (final Role role : roles) {
            if (!user.getRoles(ImplServer.this).contains(role))
            {
              ((ImplRole)role).addUserNoUpdate(user);
              ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
              {
                public void run()
                {
                  List<UserRoleAddListener> listeners = ImplServer.this.api.getListeners(UserRoleAddListener.class);
                  synchronized (listeners)
                  {
                    for (UserRoleAddListener listener : listeners) {
                      listener.onUserRoleAdd(ImplServer.this.api, ImplServer.6.this.val$user, role);
                    }
                  }
                }
              });
            }
          }
          ImplServer.logger.debug("Updated roles in server {} (amount: {})", ImplServer.this, Integer.valueOf(ImplServer.this.getRoles().size()));
        }
        catch (Exception e)
        {
          return e;
        }
        return null;
      }
    });
  }
  
  public Future<Exception> banUser(User user)
  {
    return banUser(user.getId(), 0);
  }
  
  public Future<Exception> banUser(String userId)
  {
    return banUser(userId, 0);
  }
  
  public Future<Exception> banUser(User user, int deleteDays)
  {
    return banUser(user.getId(), deleteDays);
  }
  
  public Future<Exception> banUser(final String userId, final int deleteDays)
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplServer.logger.debug("Trying to ban an user from server {} (user id: {}, delete days: {})", new Object[] { ImplServer.this, userId, Integer.valueOf(deleteDays) });
          
          HttpResponse<JsonNode> response = Unirest.put("https://discordapp.com/api/guilds/:guild_id/bans/" + userId + "?delete-message-days=" + deleteDays).header("authorization", ImplServer.this.api.getToken()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          final User user = (User)ImplServer.this.api.getUserById(userId).get();
          if (user != null) {
            ImplServer.this.removeMember(user);
          }
          ImplServer.logger.info("Banned an user from server {} (user id: {}, delete days: {})", new Object[] { ImplServer.this, userId, Integer.valueOf(deleteDays) });
          
          ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<ServerMemberBanListener> listeners = ImplServer.this.api.getListeners(ServerMemberBanListener.class);
              synchronized (listeners)
              {
                for (ServerMemberBanListener listener : listeners) {
                  listener.onServerMemberBan(ImplServer.this.api, user, ImplServer.this);
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
  
  public Future<Exception> unbanUser(final String userId)
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplServer.logger.debug("Trying to unban an user from server {} (user id: {})", ImplServer.this, userId);
        try
        {
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/guilds/" + ImplServer.this.getId() + "/bans/" + userId).header("authorization", ImplServer.this.api.getToken()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          ImplServer.logger.info("Unbanned an user from server {} (user id: {})", ImplServer.this, userId);
          ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<ServerMemberUnbanListener> listeners = ImplServer.this.api.getListeners(ServerMemberUnbanListener.class);
              synchronized (listeners)
              {
                for (ServerMemberUnbanListener listener : listeners) {
                  listener.onServerMemberUnban(ImplServer.this.api, ImplServer.8.this.val$userId, ImplServer.this);
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
  
  public Future<User[]> getBans()
  {
    return getBans(null);
  }
  
  public Future<User[]> getBans(FutureCallback<User[]> callback)
  {
    ListenableFuture<User[]> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public User[] call()
        throws Exception
      {
        ImplServer.logger.debug("Trying to get bans for server {}", ImplServer.this);
        HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/guilds/" + ImplServer.this.getId() + "/bans").header("authorization", ImplServer.this.api.getToken()).asJson();
        
        ImplServer.this.api.checkResponse(response);
        JSONArray bannedUsersJson = ((JsonNode)response.getBody()).getArray();
        User[] bannedUsers = new User[bannedUsersJson.length()];
        for (int i = 0; i < bannedUsersJson.length(); i++) {
          bannedUsers[i] = ImplServer.this.api.getOrCreateUser(bannedUsersJson.getJSONObject(i));
        }
        ImplServer.logger.debug("Got bans for server {} (amount: {})", ImplServer.this, Integer.valueOf(bannedUsers.length));
        return bannedUsers;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Exception> kickUser(User user)
  {
    return kickUser(user.getId());
  }
  
  public Future<Exception> kickUser(final String userId)
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplServer.logger.debug("Trying to kick an user from server {} (user id: {})", ImplServer.this);
        try
        {
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/guilds/" + ImplServer.this.getId() + "/members/" + userId).header("authorization", ImplServer.this.api.getToken()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          final User user = (User)ImplServer.this.api.getUserById(userId).get();
          if (user != null) {
            ImplServer.this.removeMember(user);
          }
          ImplServer.logger.info("Kicked an user from server {} (user id: {})", ImplServer.this);
          ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<ServerMemberRemoveListener> listeners = ImplServer.this.api.getListeners(ServerMemberRemoveListener.class);
              synchronized (listeners)
              {
                for (ServerMemberRemoveListener listener : listeners) {
                  listener.onServerMemberRemove(ImplServer.this.api, user, ImplServer.this);
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
  
  public Future<Role> createRole()
  {
    return createRole(null);
  }
  
  public Future<Role> createRole(FutureCallback<Role> callback)
  {
    ListenableFuture<Role> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Role call()
        throws Exception
      {
        ImplServer.logger.debug("Trying to create a role in server {}", ImplServer.this);
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/guilds/" + ImplServer.this.getId() + "/roles").header("authorization", ImplServer.this.api.getToken()).asJson();
        
        ImplServer.this.api.checkResponse(response);
        final Role role = new ImplRole(((JsonNode)response.getBody()).getObject(), ImplServer.this, ImplServer.this.api);
        ImplServer.logger.info("Created role in server {} (name: {}, id: {})", new Object[] { ImplServer.this, role.getName(), role.getId() });
        
        ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
        {
          public void run()
          {
            List<RoleCreateListener> listeners = ImplServer.this.api.getListeners(RoleCreateListener.class);
            synchronized (listeners)
            {
              for (RoleCreateListener listener : listeners) {
                listener.onRoleCreate(ImplServer.this.api, role);
              }
            }
          }
        });
        return role;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Exception> updateName(String newName)
  {
    return update(newName, null, null);
  }
  
  public Future<Exception> updateRegion(Region newRegion)
  {
    return update(null, newRegion, null);
  }
  
  public Future<Exception> updateIcon(BufferedImage newIcon)
  {
    return update(null, null, newIcon);
  }
  
  public Future<Exception> update(final String newName, final Region newRegion, BufferedImage newIcon)
  {
    final JSONObject params = new JSONObject();
    if (newName == null) {
      params.put("name", getName());
    } else {
      params.put("name", newName);
    }
    if (newRegion != null) {
      params.put("region", newRegion.getKey());
    }
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplServer.logger.debug("Trying to update server {} (new name: {}, old name: {}, new region: {}, old region: {}", new Object[] { ImplServer.this, newName, ImplServer.this.getName(), newRegion == null ? "null" : newRegion.getKey(), ImplServer.this.getRegion().getKey() });
          
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/guilds/" + ImplServer.this.getId()).header("authorization", ImplServer.this.api.getToken()).header("Content-Type", "application/json").body(params.toString()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          ImplServer.logger.debug("Updated server {} (new name: {}, old name: {}, new region: {}, old region: {}", new Object[] { ImplServer.this, newName, ImplServer.this.getName(), newRegion == null ? "null" : newRegion.getKey(), ImplServer.this.getRegion().getKey() });
          
          String name = ((JsonNode)response.getBody()).getObject().getString("name");
          if (!ImplServer.this.getName().equals(name))
          {
            final String oldName = ImplServer.this.getName();
            ImplServer.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<ServerChangeNameListener> listeners = ImplServer.this.api.getListeners(ServerChangeNameListener.class);
                synchronized (listeners)
                {
                  for (ServerChangeNameListener listener : listeners) {
                    listener.onServerChangeName(ImplServer.this.api, ImplServer.this, oldName);
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
  
  public Region getRegion()
  {
    return this.region;
  }
  
  public int getMemberCount()
  {
    return this.memberCount;
  }
  
  public boolean isLarge()
  {
    return this.large;
  }
  
  public Future<Exception> authorizeBot(String applicationId)
  {
    return authorizeBot(applicationId, null);
  }
  
  public Future<Exception> authorizeBot(final String applicationId, final Permissions permissions)
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplServer.logger.debug("Trying to authorize bot with application id {} and permissions {}", applicationId, permissions);
          
          HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/oauth2/authorize?client_id={id}&scope=bot").routeParam("id", applicationId).header("authorization", ImplServer.this.api.getToken()).header("Content-Type", "application/json").body(new JSONObject().put("guild_id", ImplServer.this.getId()).put("permissions", ((ImplPermissions)permissions).getAllowed()).put("authorize", true).toString()).asJson();
          
          ImplServer.this.api.checkResponse(response);
          ImplServer.logger.debug("Authorized bot with application id {} and permissions {}", applicationId, permissions);
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
  
  public void setRegion(Region region)
  {
    this.region = region;
  }
  
  public void addMember(User user)
  {
    this.members.put(user.getId(), user);
  }
  
  public void removeMember(User user)
  {
    this.members.remove(user.getId());
  }
  
  public void incrementMemberCount()
  {
    this.memberCount += 1;
  }
  
  public void decrementMemberCount()
  {
    this.memberCount -= 1;
  }
  
  public void setMemberCount(int memberCount)
  {
    this.memberCount = memberCount;
  }
  
  public void addChannel(Channel channel)
  {
    this.channels.put(channel.getId(), channel);
  }
  
  public void addVoiceChannel(VoiceChannel channel)
  {
    this.voiceChannels.put(channel.getId(), channel);
  }
  
  public void addRole(Role role)
  {
    this.roles.put(role.getId(), role);
  }
  
  public void removeRole(Role role)
  {
    this.roles.remove(role.getId());
  }
  
  public void removeChannel(Channel channel)
  {
    this.channels.remove(channel.getId());
  }
  
  public void removeVoiceChannel(VoiceChannel channel)
  {
    this.voiceChannels.remove(channel.getId());
  }
  
  private Object createChannelBlocking(String name, boolean voice)
    throws Exception
  {
    logger.debug("Trying to create channel in server {} (name: {}, voice: {})", new Object[] { this, name, Boolean.valueOf(voice) });
    JSONObject param = new JSONObject().put("name", name).put("type", voice ? "voice" : "text");
    HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/guilds/" + this.id + "/channels").header("authorization", this.api.getToken()).header("Content-Type", "application/json").body(param.toString()).asJson();
    
    this.api.checkResponse(response);
    if (voice) {
      return new ImplVoiceChannel(((JsonNode)response.getBody()).getObject(), this, this.api);
    }
    return new ImplChannel(((JsonNode)response.getBody()).getObject(), this, this.api);
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
