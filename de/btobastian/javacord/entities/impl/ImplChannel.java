package de.btobastian.javacord.entities.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.InviteBuilder;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageHistory;
import de.btobastian.javacord.entities.message.MessageReceiver;
import de.btobastian.javacord.entities.message.impl.ImplMessage;
import de.btobastian.javacord.entities.message.impl.ImplMessageHistory;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplPermissions;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.channel.ChannelChangeNameListener;
import de.btobastian.javacord.listener.channel.ChannelChangeTopicListener;
import de.btobastian.javacord.listener.channel.ChannelDeleteListener;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplChannel
  implements Channel
{
  private static final Logger logger = LoggerUtil.getLogger(ImplChannel.class);
  private static final Permissions emptyPermissions = new ImplPermissions(0, 0);
  private final ImplDiscordAPI api;
  private final String id;
  private String name;
  private String topic = null;
  private int position;
  private final ImplServer server;
  private final ConcurrentHashMap<String, Permissions> overwrittenPermissions = new ConcurrentHashMap();
  
  public ImplChannel(JSONObject data, ImplServer server, ImplDiscordAPI api)
  {
    this.api = api;
    this.server = server;
    
    this.id = data.getString("id");
    this.name = data.getString("name");
    try
    {
      this.topic = data.getString("topic");
    }
    catch (JSONException ignored) {}
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
    server.addChannel(this);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getTopic()
  {
    return this.topic;
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
        try
        {
          ImplChannel.logger.debug("Trying to delete channel {}", ImplChannel.this);
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/channels/" + ImplChannel.this.id).header("authorization", ImplChannel.this.api.getToken()).asJson();
          
          ImplChannel.this.api.checkResponse(response);
          ImplChannel.this.server.removeChannel(ImplChannel.this);
          ImplChannel.logger.info("Deleted channel {}", ImplChannel.this);
          
          ImplChannel.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<ChannelDeleteListener> listeners = ImplChannel.this.api.getListeners(ChannelDeleteListener.class);
              synchronized (listeners)
              {
                for (ChannelDeleteListener listener : listeners) {
                  listener.onChannelDelete(ImplChannel.this.api, ImplChannel.this);
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
  
  public void type()
  {
    try
    {
      logger.debug("Sending typing state in channel {}", this);
      Unirest.post("https://discordapp.com/api/channels/" + this.id + "/typing").header("authorization", this.api.getToken()).asJson();
      
      logger.debug("Sent typing state in channel {}", this);
    }
    catch (UnirestException e)
    {
      logger.warn("Couldn't send typing state in channel {}. Please contact the developer!", this, e);
    }
  }
  
  public InviteBuilder getInviteBuilder()
  {
    return new ImplInviteBuilder(this, this.api);
  }
  
  public Future<Message> sendMessage(String content)
  {
    return sendMessage(content, false);
  }
  
  public Future<Message> sendMessage(String content, boolean tts)
  {
    return sendMessage(content, tts, null);
  }
  
  public Future<Message> sendMessage(String content, FutureCallback<Message> callback)
  {
    return sendMessage(content, false, callback);
  }
  
  public Future<Message> sendMessage(final String content, final boolean tts, FutureCallback<Message> callback)
  {
    final MessageReceiver receiver = this;
    ListenableFuture<Message> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Message call()
        throws Exception
      {
        ImplChannel.logger.debug("Trying to send message in channel {} (content: \"{}\", tts: {})", new Object[] { ImplChannel.this, content, Boolean.valueOf(tts) });
        
        ImplChannel.this.api.checkRateLimit();
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/channels/" + ImplChannel.this.id + "/messages").header("authorization", ImplChannel.this.api.getToken()).header("content-type", "application/json").body(new JSONObject().put("content", content).put("tts", tts).put("mentions", new String[0]).toString()).asJson();
        
        ImplChannel.this.api.checkResponse(response);
        ImplChannel.logger.debug("Sent message in channel {} (content: \"{}\", tts: {})", new Object[] { ImplChannel.this, content, Boolean.valueOf(tts) });
        
        return new ImplMessage(((JsonNode)response.getBody()).getObject(), ImplChannel.this.api, receiver);
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Message> sendFile(File file)
  {
    return sendFile(file, null, null);
  }
  
  public Future<Message> sendFile(File file, FutureCallback<Message> callback)
  {
    return sendFile(file, null, callback);
  }
  
  public Future<Message> sendFile(InputStream inputStream, String filename)
  {
    return sendFile(inputStream, filename, null, null);
  }
  
  public Future<Message> sendFile(InputStream inputStream, String filename, FutureCallback<Message> callback)
  {
    return sendFile(inputStream, filename, null, callback);
  }
  
  public Future<Message> sendFile(File file, String comment)
  {
    return sendFile(file, comment, null);
  }
  
  public Future<Message> sendFile(final File file, final String comment, FutureCallback<Message> callback)
  {
    final MessageReceiver receiver = this;
    ListenableFuture<Message> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Message call()
        throws Exception
      {
        ImplChannel.logger.debug("Trying to send a file in channel {} (name: {}, comment: {})", new Object[] { ImplChannel.this, file.getName(), comment });
        
        ImplChannel.this.api.checkRateLimit();
        MultipartBody body = Unirest.post("https://discordapp.com/api/channels/" + ImplChannel.this.id + "/messages").header("authorization", ImplChannel.this.api.getToken()).field("file", file);
        if (comment != null) {
          body.field("content", comment);
        }
        HttpResponse<JsonNode> response = body.asJson();
        ImplChannel.this.api.checkResponse(response);
        ImplChannel.logger.debug("Sent a file in channel {} (name: {}, comment: {})", new Object[] { ImplChannel.this, file.getName(), comment });
        
        return new ImplMessage(((JsonNode)response.getBody()).getObject(), ImplChannel.this.api, receiver);
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<Message> sendFile(InputStream inputStream, String filename, String comment)
  {
    return sendFile(inputStream, filename, comment, null);
  }
  
  public Future<Message> sendFile(final InputStream inputStream, final String filename, final String comment, FutureCallback<Message> callback)
  {
    final MessageReceiver receiver = this;
    ListenableFuture<Message> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public Message call()
        throws Exception
      {
        ImplChannel.logger.debug("Trying to send an input stream in channel {} (comment: {})", ImplChannel.this, comment);
        
        ImplChannel.this.api.checkRateLimit();
        MultipartBody body = Unirest.post("https://discordapp.com/api/channels/" + ImplChannel.this.id + "/messages").header("authorization", ImplChannel.this.api.getToken()).field("file", inputStream, filename);
        if (comment != null) {
          body.field("content", comment);
        }
        HttpResponse<JsonNode> response = body.asJson();
        ImplChannel.this.api.checkResponse(response);
        ImplChannel.logger.debug("Sent an input stream in channel {} (comment: {})", ImplChannel.this, comment);
        return new ImplMessage(((JsonNode)response.getBody()).getObject(), ImplChannel.this.api, receiver);
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
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
  
  public Future<MessageHistory> getMessageHistory(int limit)
  {
    return getMessageHistory(null, false, limit, null);
  }
  
  public Future<MessageHistory> getMessageHistory(int limit, FutureCallback<MessageHistory> callback)
  {
    return getMessageHistory(null, false, limit, callback);
  }
  
  public Future<MessageHistory> getMessageHistoryBefore(Message before, int limit)
  {
    return getMessageHistory(before.getId(), true, limit, null);
  }
  
  public Future<MessageHistory> getMessageHistoryBefore(Message before, int limit, FutureCallback<MessageHistory> callback)
  {
    return getMessageHistory(before.getId(), true, limit, callback);
  }
  
  public Future<MessageHistory> getMessageHistoryBefore(String beforeId, int limit)
  {
    return getMessageHistory(beforeId, true, limit, null);
  }
  
  public Future<MessageHistory> getMessageHistoryBefore(String beforeId, int limit, FutureCallback<MessageHistory> callback)
  {
    return getMessageHistory(beforeId, true, limit, callback);
  }
  
  public Future<MessageHistory> getMessageHistoryAfter(Message after, int limit)
  {
    return getMessageHistory(after.getId(), false, limit, null);
  }
  
  public Future<MessageHistory> getMessageHistoryAfter(Message after, int limit, FutureCallback<MessageHistory> callback)
  {
    return getMessageHistory(after.getId(), false, limit, callback);
  }
  
  public Future<MessageHistory> getMessageHistoryAfter(String afterId, int limit)
  {
    return getMessageHistory(afterId, false, limit, null);
  }
  
  public Future<MessageHistory> getMessageHistoryAfter(String afterId, int limit, FutureCallback<MessageHistory> callback)
  {
    return getMessageHistory(afterId, false, limit, callback);
  }
  
  public Future<Exception> updateName(String newName)
  {
    return update(newName, getTopic());
  }
  
  public Future<Exception> updateTopic(String newTopic)
  {
    return update(getName(), newTopic);
  }
  
  public Future<Exception> update(final String newName, final String newTopic)
  {
    final JSONObject params = new JSONObject().put("name", newName).put("topic", newTopic);
    
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        ImplChannel.logger.debug("Trying to update channel {} (new name: {}, old name: {}, new topic: {}, old topic: {})", new Object[] { ImplChannel.this, newName, ImplChannel.this.getName(), newTopic, ImplChannel.this.getTopic() });
        try
        {
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/channels/" + ImplChannel.this.getId()).header("authorization", ImplChannel.this.api.getToken()).header("Content-Type", "application/json").body(params.toString()).asJson();
          
          ImplChannel.this.api.checkResponse(response);
          ImplChannel.logger.info("Updated channel {} (new name: {}, old name: {}, new topic: {}, old topic: {})", new Object[] { ImplChannel.this, newName, ImplChannel.this.getName(), newTopic, ImplChannel.this.getTopic() });
          
          String updatedName = ((JsonNode)response.getBody()).getObject().getString("name");
          String updatedTopic = null;
          if ((((JsonNode)response.getBody()).getObject().has("topic")) && (!((JsonNode)response.getBody()).getObject().isNull("topic"))) {
            updatedTopic = ((JsonNode)response.getBody()).getObject().getString("topic");
          }
          if (!updatedName.equals(ImplChannel.this.getName()))
          {
            final String oldName = ImplChannel.this.getName();
            ImplChannel.this.setName(updatedName);
            ImplChannel.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<ChannelChangeNameListener> listeners = ImplChannel.this.api.getListeners(ChannelChangeNameListener.class);
                synchronized (listeners)
                {
                  for (ChannelChangeNameListener listener : listeners) {
                    listener.onChannelChangeName(ImplChannel.this.api, ImplChannel.this, oldName);
                  }
                }
              }
            });
          }
          if (((ImplChannel.this.getTopic() != null) && (updatedTopic == null)) || ((ImplChannel.this.getTopic() == null) && (updatedTopic != null)) || ((ImplChannel.this.getTopic() != null) && (!ImplChannel.this.getTopic().equals(updatedTopic))))
          {
            final String oldTopic = ImplChannel.this.getTopic();
            ImplChannel.this.setTopic(updatedTopic);
            ImplChannel.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<ChannelChangeTopicListener> listeners = ImplChannel.this.api.getListeners(ChannelChangeTopicListener.class);
                synchronized (listeners)
                {
                  for (ChannelChangeTopicListener listener : listeners) {
                    listener.onChannelChangeTopic(ImplChannel.this.api, ImplChannel.this, oldTopic);
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
  
  public String getMentionTag()
  {
    return "<#" + getId() + ">";
  }
  
  private Future<MessageHistory> getMessageHistory(final String messageId, final boolean before, final int limit, FutureCallback<MessageHistory> callback)
  {
    ListenableFuture<MessageHistory> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public MessageHistory call()
        throws Exception
      {
        MessageHistory history = new ImplMessageHistory(ImplChannel.this.api, ImplChannel.this.id, messageId, before, limit);
        
        ImplChannel.this.api.addHistory(history);
        return history;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setTopic(String topic)
  {
    this.topic = topic;
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
