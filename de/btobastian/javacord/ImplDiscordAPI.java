package de.btobastian.javacord;

import com.google.common.io.BaseEncoding;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.SettableFuture;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;
import de.btobastian.javacord.entities.Application;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Invite;
import de.btobastian.javacord.entities.Region;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.impl.ImplApplication;
import de.btobastian.javacord.entities.impl.ImplInvite;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.impl.ImplUser;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageHistory;
import de.btobastian.javacord.entities.message.impl.ImplMessageHistory;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.exceptions.BadResponseException;
import de.btobastian.javacord.exceptions.NotSupportedForBotsException;
import de.btobastian.javacord.exceptions.PermissionsException;
import de.btobastian.javacord.exceptions.RateLimitedException;
import de.btobastian.javacord.listener.Listener;
import de.btobastian.javacord.listener.server.ServerJoinListener;
import de.btobastian.javacord.listener.user.UserChangeNameListener;
import de.btobastian.javacord.utils.DiscordWebsocketAdapter;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplDiscordAPI
  implements DiscordAPI
{
  private static final Logger logger = LoggerUtil.getLogger(ImplDiscordAPI.class);
  private final ThreadPool pool;
  private String email = null;
  private String password = null;
  private String token = null;
  private String game = null;
  private boolean idle = false;
  private boolean autoReconnect = true;
  private User you = null;
  private volatile int messageCacheSize = 200;
  private DiscordWebsocketAdapter socketAdapter = null;
  private RateLimitedException lastRateLimitedException = null;
  private final ConcurrentHashMap<String, Server> servers = new ConcurrentHashMap();
  private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap();
  private final ArrayList<Message> messages = new ArrayList();
  private final ConcurrentHashMap<Class<?>, List<Listener>> listeners = new ConcurrentHashMap();
  private final ConcurrentHashMap<String, SettableFuture<Server>> waitingForListener = new ConcurrentHashMap();
  private final Set<MessageHistory> messageHistories = Collections.newSetFromMap(new WeakHashMap());
  private final Object listenerLock = new Object();
  private final ServerJoinListener listener = new ServerJoinListener()
  {
    public void onServerJoin(DiscordAPI api, Server server)
    {
      synchronized (ImplDiscordAPI.this.listenerLock)
      {
        SettableFuture<Server> future = (SettableFuture)ImplDiscordAPI.this.waitingForListener.get(server.getId());
        if (future != null)
        {
          ImplDiscordAPI.logger.debug("Joined or created server {}. We were waiting for this server!", server);
          ImplDiscordAPI.this.waitingForListener.remove(server.getId());
          future.set(server);
        }
      }
    }
  };
  
  public ImplDiscordAPI(ThreadPool pool)
  {
    this.pool = pool;
  }
  
  public void connect(FutureCallback<DiscordAPI> callback)
  {
    final DiscordAPI api = this;
    Futures.addCallback(this.pool.getListeningExecutorService().submit(new Callable()
    {
      public DiscordAPI call()
        throws Exception
      {
        ImplDiscordAPI.this.connectBlocking();
        return api;
      }
    }), callback);
  }
  
  public void connectBlocking()
  {
    if ((this.token == null) || (!checkTokenBlocking(this.token)))
    {
      if ((this.email == null) || (this.password == null))
      {
        logger.warn("No valid token provided AND missing email or password. Connecting not possible!");
        return;
      }
      this.token = requestTokenBlocking();
    }
    String gateway = requestGatewayBlocking();
    try
    {
      this.socketAdapter = new DiscordWebsocketAdapter(new URI(gateway), this, false);
    }
    catch (URISyntaxException e)
    {
      logger.warn("Something went wrong while connecting. Please contact the developer!", e);
      return;
    }
    try
    {
      if (!((Boolean)this.socketAdapter.isReady().get()).booleanValue()) {
        throw new IllegalStateException("Socket closed before ready packet was received!");
      }
    }
    catch (InterruptedException|ExecutionException e)
    {
      logger.warn("Something went wrong while connecting. Please contact the developer!", e);
    }
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public void setGame(String game)
  {
    this.game = game;
    try
    {
      if ((this.socketAdapter != null) && (this.socketAdapter.isReady().isDone()) && (((Boolean)this.socketAdapter.isReady().get()).booleanValue())) {
        this.socketAdapter.updateStatus();
      }
    }
    catch (InterruptedException|ExecutionException e)
    {
      e.printStackTrace();
    }
  }
  
  public String getGame()
  {
    return this.game;
  }
  
  public Server getServerById(String id)
  {
    return (Server)this.servers.get(id);
  }
  
  public Collection<Server> getServers()
  {
    return Collections.unmodifiableCollection(this.servers.values());
  }
  
  public Collection<Channel> getChannels()
  {
    Collection<Channel> channels = new ArrayList();
    for (Server server : getServers()) {
      channels.addAll(server.getChannels());
    }
    return Collections.unmodifiableCollection(channels);
  }
  
  public Channel getChannelById(String id)
  {
    Iterator<Server> serverIterator = getServers().iterator();
    while (serverIterator.hasNext())
    {
      Channel channel = ((Server)serverIterator.next()).getChannelById(id);
      if (channel != null) {
        return channel;
      }
    }
    return null;
  }
  
  public Collection<VoiceChannel> getVoiceChannels()
  {
    Collection<VoiceChannel> channels = new ArrayList();
    for (Server server : getServers()) {
      channels.addAll(server.getVoiceChannels());
    }
    return Collections.unmodifiableCollection(channels);
  }
  
  public VoiceChannel getVoiceChannelById(String id)
  {
    Iterator<Server> serverIterator = getServers().iterator();
    while (serverIterator.hasNext())
    {
      VoiceChannel channel = ((Server)serverIterator.next()).getVoiceChannelById(id);
      if (channel != null) {
        return channel;
      }
    }
    return null;
  }
  
  public Future<User> getUserById(final String id)
  {
    User user = (User)this.users.get(id);
    if (user != null) {
      return Futures.immediateFuture(user);
    }
    getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public User call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying request/find user with id {} who isn't cached", id);
        User user = null;
        Iterator<Server> serverIterator = ImplDiscordAPI.this.getServers().iterator();
        while (serverIterator.hasNext())
        {
          Server server = (Server)serverIterator.next();
          HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/guilds/" + server.getId() + "/members/" + id).header("authorization", ImplDiscordAPI.this.token).asJson();
          if ((response.getStatus() >= 200) && (response.getStatus() <= 299))
          {
            user = ImplDiscordAPI.this.getOrCreateUser(((JsonNode)response.getBody()).getObject().getJSONObject("user"));
            
            ((ImplServer)server).addMember(user);
            if (((JsonNode)response.getBody()).getObject().has("roles"))
            {
              JSONArray roleIds = ((JsonNode)response.getBody()).getObject().getJSONArray("roles");
              for (int i = 0; i < roleIds.length(); i++) {
                ((ImplRole)server.getRoleById(roleIds.getString(i))).addUserNoUpdate(user);
              }
            }
          }
        }
        if (user != null) {
          ImplDiscordAPI.logger.debug("Found user {} with id {}", user, id);
        } else {
          ImplDiscordAPI.logger.debug("No user with id {} was found", id);
        }
        return user;
      }
    });
  }
  
  public User getCachedUserById(String id)
  {
    return (User)this.users.get(id);
  }
  
  public Collection<User> getUsers()
  {
    return Collections.unmodifiableCollection(this.users.values());
  }
  
  public void registerListener(Listener listener)
  {
    for (Class<?> implementedInterface : listener.getClass().getInterfaces()) {
      if (Listener.class.isAssignableFrom(implementedInterface))
      {
        List<Listener> listenersList = (List)this.listeners.get(implementedInterface);
        if (listenersList == null)
        {
          listenersList = new ArrayList();
          this.listeners.put(implementedInterface, listenersList);
        }
        synchronized (listenersList)
        {
          listenersList.add(listener);
        }
      }
    }
  }
  
  public Message getMessageById(String id)
  {
    synchronized (this.messages)
    {
      for (Message message : this.messages) {
        if (message.getId().equals(id)) {
          return message;
        }
      }
    }
    synchronized (this.messageHistories)
    {
      for (MessageHistory history : this.messageHistories) {
        history.getMessageById(id);
      }
    }
    return null;
  }
  
  public ThreadPool getThreadPool()
  {
    return this.pool;
  }
  
  public void setIdle(boolean idle)
  {
    this.idle = idle;
    try
    {
      if ((this.socketAdapter != null) && (this.socketAdapter.isReady().isDone()) && (((Boolean)this.socketAdapter.isReady().get()).booleanValue())) {
        this.socketAdapter.updateStatus();
      }
    }
    catch (InterruptedException|ExecutionException e)
    {
      e.printStackTrace();
    }
  }
  
  public boolean isIdle()
  {
    return this.idle;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public void setToken(String token, boolean bot)
  {
    this.token = (bot ? "Bot " + token : token);
  }
  
  public boolean checkTokenBlocking(String token)
  {
    try
    {
      logger.debug("Checking token {}", token.replaceAll(".{10}", "**********"));
      HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/users/@me/guilds").header("authorization", token).asJson();
      if ((response.getStatus() < 200) || (response.getStatus() > 299))
      {
        logger.debug("Checked token {} (valid: {})", token.replaceAll(".{10}", "**********"), Boolean.valueOf(false));
        return false;
      }
      logger.debug("Checked token {} (valid: {})", token.replaceAll(".{10}", "**********"), Boolean.valueOf(true));
      return true;
    }
    catch (UnirestException e) {}
    return false;
  }
  
  public Future<Server> acceptInvite(String inviteCode)
  {
    return acceptInvite(inviteCode, null);
  }
  
  public Future<Server> acceptInvite(final String inviteCode, FutureCallback<Server> callback)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    ListenableFuture<Server> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Server call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to accept invite (code: {})", inviteCode);
        SettableFuture<Server> settableFuture;
        synchronized (ImplDiscordAPI.this.listenerLock)
        {
          HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/invite/" + inviteCode).header("authorization", ImplDiscordAPI.this.token).asJson();
          
          ImplDiscordAPI.this.checkResponse(response);
          String guildId = ((JsonNode)response.getBody()).getObject().getJSONObject("guild").getString("id");
          if (ImplDiscordAPI.this.getServerById(guildId) != null) {
            throw new IllegalStateException("Already member of this server!");
          }
          ImplDiscordAPI.logger.info("Accepted invite and waiting for listener to be called (code: {}, server id: {})", inviteCode, guildId);
          
          settableFuture = SettableFuture.create();
          ImplDiscordAPI.this.waitingForListener.put(guildId, settableFuture);
        }
        return (Server)settableFuture.get();
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Server> createServer(String name)
  {
    return createServer(name, Region.US_WEST, null, null);
  }
  
  public Future<Server> createServer(String name, FutureCallback<Server> callback)
  {
    return createServer(name, Region.US_WEST, null, callback);
  }
  
  public Future<Server> createServer(String name, Region region)
  {
    return createServer(name, region, null, null);
  }
  
  public Future<Server> createServer(String name, Region region, FutureCallback<Server> callback)
  {
    return createServer(name, region, null, callback);
  }
  
  public Future<Server> createServer(String name, BufferedImage icon)
  {
    return createServer(name, Region.US_WEST, icon, null);
  }
  
  public Future<Server> createServer(String name, BufferedImage icon, FutureCallback<Server> callback)
  {
    return createServer(name, Region.US_WEST, icon, callback);
  }
  
  public Future<Server> createServer(String name, Region region, BufferedImage icon)
  {
    return createServer(name, region, icon, null);
  }
  
  public Future<Server> createServer(final String name, final Region region, final BufferedImage icon, FutureCallback<Server> callback)
  {
    ListenableFuture<Server> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Server call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to create server (name: {}, region: {}, icon: {}", new Object[] { name, region == null ? "null" : region.getKey(), Boolean.valueOf(icon != null ? 1 : false) });
        if ((name == null) || (name.length() < 2) || (name.length() > 100)) {
          throw new IllegalArgumentException("Name must be 2-100 characters long!");
        }
        JSONObject params = new JSONObject();
        if (icon != null)
        {
          if ((icon.getHeight() != 128) || (icon.getWidth() != 128)) {
            throw new IllegalArgumentException("Icon must be 128*128px!");
          }
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          ImageIO.write(icon, "jpg", os);
          params.put("icon", "data:image/jpg;base64," + BaseEncoding.base64().encode(os.toByteArray()));
        }
        params.put("name", name);
        params.put("region", region == null ? Region.US_WEST.getKey() : region.getKey());
        SettableFuture<Server> settableFuture;
        synchronized (ImplDiscordAPI.this.listenerLock)
        {
          HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/guilds").header("authorization", ImplDiscordAPI.this.token).header("Content-Type", "application/json").body(params.toString()).asJson();
          
          ImplDiscordAPI.this.checkResponse(response);
          String guildId = ((JsonNode)response.getBody()).getObject().getString("id");
          ImplDiscordAPI.logger.info("Created server and waiting for listener to be called (name: {}, region: {}, icon: {}, server id: {})", new Object[] { name, region == null ? "null" : region.getKey(), Boolean.valueOf(icon != null ? 1 : false), guildId });
          
          settableFuture = SettableFuture.create();
          ImplDiscordAPI.this.waitingForListener.put(guildId, settableFuture);
        }
        return (Server)settableFuture.get();
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public User getYourself()
  {
    return this.you;
  }
  
  public Future<Exception> updateUsername(String newUsername)
  {
    return updateProfile(newUsername, null, null, null);
  }
  
  public Future<Exception> updateEmail(String newEmail)
  {
    return updateProfile(null, newEmail, null, null);
  }
  
  public Future<Exception> updatePassword(String newPassword)
  {
    return updateProfile(null, null, newPassword, null);
  }
  
  public Future<Exception> updateAvatar(BufferedImage newAvatar)
  {
    return updateProfile(null, null, null, newAvatar);
  }
  
  public Future<Exception> updateProfile(final String newUsername, String newEmail, final String newPassword, final BufferedImage newAvatar)
  {
    logger.debug("Trying to update profile (username: {}, email: {}, password: {}, change avatar: {}", new Object[] { newUsername, this.email, newPassword == null ? "null" : newPassword.replaceAll(".", "*"), Boolean.valueOf(newAvatar != null ? 1 : false) });
    
    String avatarString = getYourself().getAvatarId();
    if (newAvatar != null) {
      try
      {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(newAvatar, "jpg", os);
        avatarString = "data:image/jpg;base64," + BaseEncoding.base64().encode(os.toByteArray());
      }
      catch (IOException ignored) {}
    }
    final JSONObject params = new JSONObject().put("username", newUsername == null ? getYourself().getName() : newUsername).put("email", newEmail == null ? this.email : newEmail).put("avatar", avatarString == null ? JSONObject.NULL : avatarString).put("password", this.password);
    if (newPassword != null) {
      params.put("new_password", newPassword);
    }
    getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/users/@me").header("authorization", ImplDiscordAPI.this.token).header("Content-Type", "application/json").body(params.toString()).asJson();
          
          ImplDiscordAPI.this.checkResponse(response);
          ImplDiscordAPI.logger.info("Updated profile (username: {}, email: {}, password: {}, change avatar: {}", new Object[] { newUsername, ImplDiscordAPI.this.email, newPassword == null ? "null" : newPassword.replaceAll(".", "*"), Boolean.valueOf(newAvatar != null ? 1 : false) });
          
          ((ImplUser)ImplDiscordAPI.this.getYourself()).setAvatarId(((JsonNode)response.getBody()).getObject().getString("avatar"));
          ImplDiscordAPI.this.setEmail(((JsonNode)response.getBody()).getObject().getString("email"));
          ImplDiscordAPI.this.setToken(((JsonNode)response.getBody()).getObject().getString("token"), ImplDiscordAPI.this.token.startsWith("Bot "));
          final String oldName = ImplDiscordAPI.this.getYourself().getName();
          ((ImplUser)ImplDiscordAPI.this.getYourself()).setName(((JsonNode)response.getBody()).getObject().getString("username"));
          if (newPassword != null) {
            ImplDiscordAPI.this.password = newPassword;
          }
          if (!ImplDiscordAPI.this.getYourself().getName().equals(oldName)) {
            ImplDiscordAPI.this.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<UserChangeNameListener> listeners = ImplDiscordAPI.this.getListeners(UserChangeNameListener.class);
                synchronized (listeners)
                {
                  for (UserChangeNameListener listener : listeners) {
                    listener.onUserChangeName(ImplDiscordAPI.this, ImplDiscordAPI.this.getYourself(), oldName);
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
  
  public Future<Invite> parseInvite(String invite)
  {
    return parseInvite(invite, null);
  }
  
  public Future<Invite> parseInvite(final String invite, FutureCallback<Invite> callback)
  {
    final String inviteCode = invite.replace("https://discord.gg/", "").replace("http://discord.gg/", "");
    ListenableFuture<Invite> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Invite call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to parse invite {} (parsed code: {})", invite, inviteCode);
        HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/invite/" + inviteCode).header("authorization", ImplDiscordAPI.this.token).asJson();
        
        ImplDiscordAPI.this.checkResponse(response);
        ImplDiscordAPI.logger.debug("Parsed invite {} (parsed code: {})", invite, inviteCode);
        return new ImplInvite(ImplDiscordAPI.this, ((JsonNode)response.getBody()).getObject());
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Exception> deleteInvite(final String inviteCode)
  {
    getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to delete invite {}", inviteCode);
        try
        {
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/invite/" + inviteCode).header("authorization", ImplDiscordAPI.this.token).asJson();
          
          ImplDiscordAPI.this.checkResponse(response);
          ImplDiscordAPI.logger.info("Deleted invite {}", inviteCode);
        }
        catch (Exception e)
        {
          return e;
        }
        return null;
      }
    });
  }
  
  public void setMessageCacheSize(int size)
  {
    this.messageCacheSize = (size < 0 ? 0 : size);
    synchronized (this.messages)
    {
      while (this.messages.size() > this.messageCacheSize) {
        this.messages.remove(0);
      }
    }
  }
  
  public int getMessageCacheSize()
  {
    return this.messageCacheSize;
  }
  
  public void reconnect(FutureCallback<DiscordAPI> callback)
  {
    Futures.addCallback(getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public DiscordAPI call()
        throws Exception
      {
        ImplDiscordAPI.this.reconnectBlocking();
        return ImplDiscordAPI.this;
      }
    }), callback);
  }
  
  public void reconnectBlocking()
  {
    reconnectBlocking(requestGatewayBlocking());
  }
  
  public void setAutoReconnect(boolean autoReconnect)
  {
    this.autoReconnect = autoReconnect;
  }
  
  public boolean isAutoReconnectEnabled()
  {
    return this.autoReconnect;
  }
  
  public Future<String> convertToBotAccount(String ownerToken)
  {
    return convertToBotAccount(null, ownerToken);
  }
  
  public Future<String> convertToBotAccount(final String applicationId, final String ownerToken)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    getThreadPool().getExecutorService().submit(new Callable()
    {
      public String call()
        throws Exception
      {
        String id = applicationId;
        ImplDiscordAPI.logger.debug("Trying to convert account to bot account (application id: {}, owner token: {}", id, ownerToken.replaceAll(".{10}", "**********"));
        if (applicationId == null)
        {
          ImplDiscordAPI.logger.debug("Trying to create application for owner");
          HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/oauth2/applications").header("content-type", "application/json").header("authorization", ownerToken).body(new JSONObject().put("name", ImplDiscordAPI.this.getYourself().getName()).toString()).asJson();
          
          ImplDiscordAPI.this.checkResponse(response);
          Application application = new ImplApplication(ImplDiscordAPI.this, ((JsonNode)response.getBody()).getObject());
          ImplDiscordAPI.logger.debug("Created application for owner (application: {})", application);
          id = application.getId();
        }
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/oauth2/applications/" + id + "/bot").header("content-type", "application/json").header("authorization", ownerToken).body(new JSONObject().put("token", ImplDiscordAPI.this.getToken()).toString()).asJson();
        
        ImplDiscordAPI.this.setToken(((JsonNode)response.getBody()).getObject().getString("token"), true);
        ImplDiscordAPI.logger.info("Converted account into bot account (id: {}, new token: {})", id, ImplDiscordAPI.this.getToken().replaceAll(".{10}", "**********"));
        
        return id;
      }
    });
  }
  
  public Future<Collection<Application>> getApplications()
  {
    return getApplications(null);
  }
  
  public Future<Collection<Application>> getApplications(FutureCallback<Collection<Application>> callback)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    ListenableFuture<Collection<Application>> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Collection<Application> call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to get applications");
        HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/oauth2/applications").header("authorization", ImplDiscordAPI.this.token).asJson();
        
        ImplDiscordAPI.this.checkResponse(response);
        JSONArray jsonApplications = ((JsonNode)response.getBody()).getArray();
        Set<Application> applications = new HashSet();
        for (int i = 0; i < jsonApplications.length(); i++) {
          applications.add(new ImplApplication(ImplDiscordAPI.this, jsonApplications.getJSONObject(i)));
        }
        ImplDiscordAPI.logger.debug("Got applications (amount: {})", Integer.valueOf(applications.size()));
        return applications;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Application> createApplication(String name)
  {
    return createApplication(name, null);
  }
  
  public Future<Application> createApplication(final String name, FutureCallback<Application> callback)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    ListenableFuture<Application> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Application call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to create application with name {}", name);
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/oauth2/applications").header("content-type", "application/json").header("authorization", ImplDiscordAPI.this.getToken()).body(new JSONObject().put("name", name).toString()).asJson();
        
        ImplDiscordAPI.this.checkResponse(response);
        Application application = new ImplApplication(ImplDiscordAPI.this, ((JsonNode)response.getBody()).getObject());
        
        ImplDiscordAPI.logger.debug("Created application {}", application);
        return application;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Application> getApplication(String id)
  {
    return getApplication(id, null);
  }
  
  public Future<Application> getApplication(final String id, FutureCallback<Application> callback)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    ListenableFuture<Application> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Application call()
        throws Exception
      {
        ImplDiscordAPI.logger.debug("Trying to get application with id {}", id);
        HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/oauth2/applications/" + id).header("Authorization", ImplDiscordAPI.this.token).asJson();
        
        ImplDiscordAPI.this.checkResponse(response);
        Application application = new ImplApplication(ImplDiscordAPI.this, ((JsonNode)response.getBody()).getObject());
        
        ImplDiscordAPI.logger.debug("Got application {}", application);
        return application;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Exception> deleteApplication(final String id)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplDiscordAPI.logger.debug("Trying to delete application with id {}", id);
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/oauth2/applications/" + id).header("Authorization", ImplDiscordAPI.this.getToken()).asJson();
          
          ImplDiscordAPI.this.checkResponse(response);
          ImplDiscordAPI.logger.debug("Deleted application with id {}", id);
        }
        catch (Exception e)
        {
          return e;
        }
        return null;
      }
    });
  }
  
  public Future<Application> createBot(String name)
  {
    return createBot(name, null, null);
  }
  
  public Future<Application> createBot(String name, FutureCallback<Application> callback)
  {
    return createBot(name, null, callback);
  }
  
  public Future<Application> createBot(String name, String applicationId)
  {
    return createBot(name, applicationId, null);
  }
  
  public Future<Application> createBot(final String name, final String applicationId, FutureCallback<Application> callback)
  {
    if (getYourself().isBot()) {
      throw new NotSupportedForBotsException();
    }
    ListenableFuture<Application> future = getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Application call()
        throws Exception
      {
        ImplApplication application;
        ImplApplication application;
        if (applicationId == null) {
          application = (ImplApplication)ImplDiscordAPI.this.createApplication(name).get();
        } else {
          application = (ImplApplication)ImplDiscordAPI.this.getApplication(applicationId).get();
        }
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/oauth2/applications/" + application.getId() + "/bot").header("Authorization", ImplDiscordAPI.this.getToken()).header("content-type", "application/json").body(new JSONObject().toString()).asJson();
        
        ImplDiscordAPI.this.checkResponse(response);
        User bot = ImplDiscordAPI.this.getOrCreateUser(((JsonNode)response.getBody()).getObject());
        String botToken = ((JsonNode)response.getBody()).getObject().getString("token");
        application.setBot(bot);
        application.setBotToken(botToken);
        return application;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public void reconnectBlocking(String gateway)
  {
    logger.debug("Trying to reconnect to gateway {}", gateway);
    this.socketAdapter.getWebSocket().disconnect();
    if ((this.token == null) || (!checkTokenBlocking(this.token))) {
      this.token = requestTokenBlocking();
    }
    try
    {
      WebSocketFactory factory = new WebSocketFactory();
      factory.setSSLContext(SSLContext.getDefault());
      
      this.socketAdapter = new DiscordWebsocketAdapter(new URI(gateway), this, true);
    }
    catch (URISyntaxException|NoSuchAlgorithmException e)
    {
      logger.warn("Reconnect failed. Please contact the developer!", e);
      return;
    }
    try
    {
      if (!((Boolean)this.socketAdapter.isReady().get()).booleanValue()) {
        throw new IllegalStateException("Socket closed before ready packet was received!");
      }
    }
    catch (InterruptedException|ExecutionException e)
    {
      logger.warn("Reconnect failed. Please contact the developer!", e);
    }
  }
  
  public void checkRateLimit()
    throws RateLimitedException
  {
    long retryAt = this.lastRateLimitedException == null ? 0L : this.lastRateLimitedException.getRetryAfter();
    long retryAfter = retryAt - System.currentTimeMillis();
    if (retryAfter > 0L) {
      throw new RateLimitedException("We are still rate limited for " + retryAfter + " ms!", retryAfter);
    }
  }
  
  public void setYourself(User user)
  {
    this.you = user;
  }
  
  public User getOrCreateUser(JSONObject data)
  {
    String id = data.getString("id");
    User user = (User)this.users.get(id);
    if (user == null) {
      user = new ImplUser(data, this);
    }
    return user;
  }
  
  public ConcurrentHashMap<String, Server> getServerMap()
  {
    return this.servers;
  }
  
  public ConcurrentHashMap<String, User> getUserMap()
  {
    return this.users;
  }
  
  public DiscordWebsocketAdapter getSocketAdapter()
  {
    return this.socketAdapter;
  }
  
  public String requestTokenBlocking()
  {
    try
    {
      logger.debug("Trying to request token (email: {}, password: {})", this.email, this.password.replaceAll(".", "*"));
      HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/auth/login").field("email", this.email).field("password", this.password).asJson();
      
      JSONObject jsonResponse = ((JsonNode)response.getBody()).getObject();
      if (response.getStatus() == 400) {
        throw new IllegalArgumentException("400 Bad request! Maybe wrong email or password?");
      }
      if ((response.getStatus() < 200) || (response.getStatus() > 299)) {
        throw new IllegalStateException("Received http status code " + response.getStatus() + " with message " + response.getStatusText() + " and body " + response.getBody());
      }
      if ((jsonResponse.has("password")) || (jsonResponse.has("email"))) {
        throw new IllegalArgumentException("Wrong email or password!");
      }
      String token = jsonResponse.getString("token");
      logger.debug("Requested token {} (email: {}, password: {})", new Object[] { token.replaceAll(".{10}", "**********"), this.email, this.password.replaceAll(".", "*") });
      
      return token;
    }
    catch (UnirestException e)
    {
      logger.warn("Couldn't request token (email: {}, password: {}). Please contact the developer!", new Object[] { this.email, this.password.replaceAll(".", "*"), e });
    }
    return null;
  }
  
  public String requestGatewayBlocking()
  {
    try
    {
      logger.debug("Requesting gateway (token: {})", this.token.replaceAll(".{10}", "**********"));
      HttpResponse<JsonNode> response = Unirest.get("https://discordapp.com/api/gateway").header("authorization", this.token).asJson();
      if (response.getStatus() == 401) {
        throw new IllegalStateException("Cannot request gateway! Invalid token?");
      }
      if ((response.getStatus() < 200) || (response.getStatus() > 299)) {
        throw new IllegalStateException("Received http status code " + response.getStatus() + " with message " + response.getStatusText() + " and body " + response.getBody());
      }
      String gateway = ((JsonNode)response.getBody()).getObject().getString("url");
      logger.debug("Requested gateway {} (token: {})", gateway, this.token.replaceAll(".{10}", "**********"));
      return gateway;
    }
    catch (UnirestException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public <T extends Listener> List<T> getListeners(Class<T> listenerClass)
  {
    List<T> listenersList = (List)this.listeners.get(listenerClass);
    return listenersList == null ? new ArrayList() : listenersList;
  }
  
  public <T extends Listener> List<T> getListeners()
  {
    for (List<Listener> list : this.listeners.values()) {
      try
      {
        return list;
      }
      catch (ClassCastException ignored) {}
    }
    return new ArrayList();
  }
  
  public void addMessage(Message message)
  {
    synchronized (this.messages)
    {
      if (this.messages.size() > this.messageCacheSize) {
        this.messages.remove(0);
      }
      this.messages.add(message);
    }
  }
  
  public void removeMessage(Message message)
  {
    synchronized (this.messages)
    {
      this.messages.remove(message);
    }
    synchronized (this.messageHistories)
    {
      for (MessageHistory history : this.messageHistories) {
        ((ImplMessageHistory)history).removeMessage(message.getId());
      }
    }
  }
  
  public void addHistory(MessageHistory history)
  {
    synchronized (this.messageHistories)
    {
      this.messageHistories.add(history);
    }
  }
  
  public void checkResponse(HttpResponse<JsonNode> response)
    throws Exception
  {
    String message = "";
    if ((response.getBody() != null) && (!((JsonNode)response.getBody()).isArray()) && (((JsonNode)response.getBody()).getObject().has("message"))) {
      message = " " + ((JsonNode)response.getBody()).getObject().getString("message");
    }
    if (response.getStatus() == 403) {
      throw new PermissionsException("Missing permissions!" + message);
    }
    if ((response.getBody() != null) && (!((JsonNode)response.getBody()).isArray()) && (((JsonNode)response.getBody()).getObject().has("retry_after")))
    {
      long retryAfter = ((JsonNode)response.getBody()).getObject().getLong("retry_after");
      RateLimitedException exception = new RateLimitedException("We got rate limited for " + retryAfter + " ms!", retryAfter);
      
      this.lastRateLimitedException = exception;
      throw exception;
    }
    if ((response.getStatus() < 200) || (response.getStatus() > 299)) {
      throw new BadResponseException("Received http status code " + response.getStatus() + " with message " + response.getStatusText() + " and body " + response.getBody(), response.getStatus(), response.getStatusText(), response);
    }
  }
  
  public Set<MessageHistory> getMessageHistories()
  {
    return this.messageHistories;
  }
  
  public ServerJoinListener getInternalServerJoinListener()
  {
    return this.listener;
  }
  
  public void setSocketAdapter(DiscordWebsocketAdapter socketAdapter)
  {
    this.socketAdapter = socketAdapter;
  }
}
