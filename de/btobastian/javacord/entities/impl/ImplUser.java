package de.btobastian.javacord.entities.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageHistory;
import de.btobastian.javacord.entities.message.MessageReceiver;
import de.btobastian.javacord.entities.message.impl.ImplMessage;
import de.btobastian.javacord.entities.message.impl.ImplMessageHistory;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplUser
  implements User
{
  private static final Logger logger = LoggerUtil.getLogger(ImplUser.class);
  private final ImplDiscordAPI api;
  private final String id;
  private String name;
  private String avatarId = null;
  private final Object userChannelIdLock = new Object();
  private String userChannelId = null;
  private String game = null;
  private final String discriminator;
  private final boolean bot;
  
  public ImplUser(JSONObject data, ImplDiscordAPI api)
  {
    this.api = api;
    
    this.id = data.getString("id");
    this.name = data.getString("username");
    try
    {
      this.avatarId = data.getString("avatar");
    }
    catch (JSONException ignored) {}
    this.discriminator = data.getString("discriminator");
    this.bot = ((data.has("bot")) && (data.getBoolean("bot")));
    
    api.getUserMap().put(this.id, this);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void type()
  {
    if (this.userChannelId == null) {
      return;
    }
    try
    {
      logger.debug("Sending typing state to user {}", this);
      HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/channels/" + getUserChannelIdBlocking() + "/typing").header("authorization", this.api.getToken()).asJson();
      
      this.api.checkResponse(response);
      logger.debug("Sent typing state to user {}", this);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public boolean isYourself()
  {
    return this.api.getYourself() == this;
  }
  
  public Future<byte[]> getAvatarAsByteArray()
  {
    return getAvatarAsByteArray(null);
  }
  
  public Future<byte[]> getAvatarAsByteArray(FutureCallback<byte[]> callback)
  {
    ListenableFuture<byte[]> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public byte[] call()
        throws Exception
      {
        ImplUser.logger.debug("Trying to get avatar from user {}", ImplUser.this);
        if (ImplUser.this.avatarId == null)
        {
          ImplUser.logger.debug("User {} seems to have no avatar. Returning empty array!", ImplUser.this);
          return new byte[0];
        }
        URL url = new URL("https://discordapp.com/api/users/" + ImplUser.this.id + "/avatars/" + ImplUser.this.avatarId + ".jpg");
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("User-Agent", "Javacord DiscordBot (https://github.com/BtoBastian/Javacord, v2.0.10)");
        InputStream in = new BufferedInputStream(conn.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte['Ѐ'];
        int n;
        while (-1 != (n = in.read(buf))) {
          out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] avatar = out.toByteArray();
        ImplUser.logger.debug("Got avatar from user {} (size: {})", ImplUser.this, Integer.valueOf(avatar.length));
        return avatar;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Future<BufferedImage> getAvatar()
  {
    return getAvatar(null);
  }
  
  public Future<BufferedImage> getAvatar(FutureCallback<BufferedImage> callback)
  {
    ListenableFuture<BufferedImage> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public BufferedImage call()
        throws Exception
      {
        byte[] imageAsBytes = (byte[])ImplUser.this.getAvatarAsByteArray().get();
        if (imageAsBytes.length == 0) {
          return null;
        }
        InputStream in = new ByteArrayInputStream(imageAsBytes);
        return ImageIO.read(in);
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public URL getAvatarUrl()
  {
    if (this.avatarId == null) {
      return null;
    }
    try
    {
      return new URL("https://discordapp.com/api/users/" + this.id + "/avatars/" + this.avatarId + ".jpg");
    }
    catch (MalformedURLException e)
    {
      logger.warn("Seems like the url of the avatar is malformed! Please contact the developer!", e);
    }
    return null;
  }
  
  public String getAvatarId()
  {
    return this.avatarId;
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
        ImplUser.logger.debug("Trying to send message to user {} (content: \"{}\", tts: {})", new Object[] { ImplUser.this, content, Boolean.valueOf(tts) });
        
        ImplUser.this.api.checkRateLimit();
        HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/channels/" + ImplUser.this.getUserChannelIdBlocking() + "/messages").header("authorization", ImplUser.this.api.getToken()).header("content-type", "application/json").body(new JSONObject().put("content", content).put("tts", tts).put("mentions", new String[0]).toString()).asJson();
        
        ImplUser.this.api.checkResponse(response);
        ImplUser.logger.debug("Sent message to user {} (content: \"{}\", tts: {})", new Object[] { ImplUser.this, content, Boolean.valueOf(tts) });
        return new ImplMessage(((JsonNode)response.getBody()).getObject(), ImplUser.this.api, receiver);
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
        ImplUser.logger.debug("Trying to send a file to user {} (name: {}, comment: {})", new Object[] { ImplUser.this, file.getName(), comment });
        
        ImplUser.this.api.checkRateLimit();
        MultipartBody body = Unirest.post("https://discordapp.com/api/channels/" + ImplUser.this.getUserChannelIdBlocking() + "/messages").header("authorization", ImplUser.this.api.getToken()).field("file", file);
        if (comment != null) {
          body.field("content", comment);
        }
        HttpResponse<JsonNode> response = body.asJson();
        ImplUser.this.api.checkResponse(response);
        ImplUser.logger.debug("Sent a file to user {} (name: {}, comment: {})", new Object[] { ImplUser.this, file.getName(), comment });
        
        return new ImplMessage(((JsonNode)response.getBody()).getObject(), ImplUser.this.api, receiver);
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
        ImplUser.logger.debug("Trying to send an input stream to user {} (comment: {})", ImplUser.this, comment);
        
        ImplUser.this.api.checkRateLimit();
        MultipartBody body = Unirest.post("https://discordapp.com/api/channels/" + ImplUser.this.getUserChannelIdBlocking() + "/messages").header("authorization", ImplUser.this.api.getToken()).field("file", inputStream, filename);
        if (comment != null) {
          body.field("content", comment);
        }
        HttpResponse<JsonNode> response = body.asJson();
        ImplUser.this.api.checkResponse(response);
        ImplUser.logger.debug("Sent an input stream to user {} (comment: {})", ImplUser.this, comment);
        return new ImplMessage(((JsonNode)response.getBody()).getObject(), ImplUser.this.api, receiver);
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public Collection<Role> getRoles(Server server)
  {
    Collection<Role> userRoles = new ArrayList();
    Iterator<Role> rolesIterator = server.getRoles().iterator();
    while (rolesIterator.hasNext())
    {
      Role role = (Role)rolesIterator.next();
      if (role.getUsers().contains(this)) {
        userRoles.add(role);
      }
    }
    return userRoles;
  }
  
  public String getGame()
  {
    return this.game;
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
  
  public String getMentionTag()
  {
    return "<@" + getId() + ">";
  }
  
  public String getDiscriminator()
  {
    return this.discriminator;
  }
  
  public boolean isBot()
  {
    return this.bot;
  }
  
  private Future<MessageHistory> getMessageHistory(final String messageId, final boolean before, final int limit, FutureCallback<MessageHistory> callback)
  {
    ListenableFuture<MessageHistory> future = this.api.getThreadPool().getListeningExecutorService().submit(new Callable()
    {
      public MessageHistory call()
        throws Exception
      {
        MessageHistory history = new ImplMessageHistory(ImplUser.this.api, ImplUser.this.getUserChannelIdBlocking(), messageId, before, limit);
        
        ImplUser.this.api.addHistory(history);
        return history;
      }
    });
    if (callback != null) {
      Futures.addCallback(future, callback);
    }
    return future;
  }
  
  public void setUserChannelId(String userChannelId)
  {
    synchronized (this.userChannelIdLock)
    {
      this.userChannelId = userChannelId;
    }
  }
  
  public String getUserChannelIdBlocking()
    throws Exception
  {
    synchronized (this.userChannelIdLock)
    {
      if (this.userChannelId != null) {
        return this.userChannelId;
      }
      logger.debug("Trying to get channel id of user {}", this);
      HttpResponse<JsonNode> response = Unirest.post("https://discordapp.com/api/users/" + this.api.getYourself().getId() + "/channels").header("authorization", this.api.getToken()).header("Content-Type", "application/json").body(new JSONObject().put("recipient_id", this.id).toString()).asJson();
      
      this.api.checkResponse(response);
      this.userChannelId = ((JsonNode)response.getBody()).getObject().getString("id");
      logger.debug("Got channel id of user {} (channel id: {})", this, this.userChannelId);
      return this.userChannelId;
    }
  }
  
  /* Error */
  public String getUserChannelId()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 7	de/btobastian/javacord/entities/impl/ImplUser:userChannelIdLock	Ljava/lang/Object;
    //   4: dup
    //   5: astore_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 8	de/btobastian/javacord/entities/impl/ImplUser:userChannelId	Ljava/lang/String;
    //   11: aload_1
    //   12: monitorexit
    //   13: areturn
    //   14: astore_2
    //   15: aload_1
    //   16: monitorexit
    //   17: aload_2
    //   18: athrow
    // Line number table:
    //   Java source line #507	-> byte code offset #0
    //   Java source line #508	-> byte code offset #7
    //   Java source line #509	-> byte code offset #14
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	19	0	this	ImplUser
    //   5	11	1	Ljava/lang/Object;	Object
    //   14	4	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   7	13	14	finally
    //   14	17	14	finally
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setGame(String game)
  {
    this.game = game;
  }
  
  public void setAvatarId(String avatarId)
  {
    this.avatarId = avatarId;
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
