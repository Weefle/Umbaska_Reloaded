package de.btobastian.javacord.entities.message.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.impl.ImplUser;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageAttachment;
import de.btobastian.javacord.entities.message.MessageReceiver;
import de.btobastian.javacord.listener.message.MessageDeleteListener;
import de.btobastian.javacord.listener.message.MessageEditListener;
import de.btobastian.javacord.utils.LoggerUtil;
import de.btobastian.javacord.utils.ThreadPool;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplMessage
  implements Message
{
  private static final Logger logger = LoggerUtil.getLogger(ImplMessage.class);
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
  private static final SimpleDateFormat FORMAT_ALTERNATIVE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private static final SimpleDateFormat FORMAT_ALTERNATIVE_TWO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
  private final ImplDiscordAPI api;
  private final String id;
  private String content = null;
  private final boolean tts;
  private final User author;
  private final List<User> mentions = new ArrayList();
  private final MessageReceiver receiver;
  private final String channelId;
  private final List<MessageAttachment> attachments = new ArrayList();
  private Calendar creationDate = Calendar.getInstance();
  
  public ImplMessage(JSONObject data, ImplDiscordAPI api, MessageReceiver receiver)
  {
    this.api = api;
    
    this.id = data.getString("id");
    if (data.has("content")) {
      this.content = data.getString("content");
    }
    this.tts = data.getBoolean("tts");
    if (data.has("timestamp"))
    {
      String time = data.getString("timestamp");
      Calendar calendar = Calendar.getInstance();
      synchronized (FORMAT)
      {
        try
        {
          calendar.setTime(FORMAT.parse(time.substring(0, time.length() - 9)));
        }
        catch (ParseException ignored)
        {
          try
          {
            calendar.setTime(FORMAT_ALTERNATIVE.parse(time.substring(0, time.length() - 9)));
          }
          catch (ParseException ignored2)
          {
            try
            {
              calendar.setTime(FORMAT_ALTERNATIVE_TWO.parse(time.substring(0, time.length() - 9)));
            }
            catch (ParseException e)
            {
              logger.warn("Could not parse timestamp {}. Please contact the developer!", time, e);
            }
          }
        }
      }
      this.creationDate = calendar;
    }
    this.author = api.getOrCreateUser(data.getJSONObject("author"));
    try
    {
      JSONArray attachments = data.getJSONArray("attachments");
      for (int i = 0; i < attachments.length(); i++)
      {
        JSONObject attachment = attachments.getJSONObject(i);
        String url = attachment.getString("url");
        String proxyUrl = attachment.getString("proxy_url");
        int size = attachment.getInt("size");
        String id = attachment.getString("id");
        String name = attachment.getString("filename");
        this.attachments.add(new ImplMessageAttachment(url, proxyUrl, size, id, name));
      }
    }
    catch (JSONException ignored) {}
    JSONArray mentions = data.getJSONArray("mentions");
    for (int i = 0; i < mentions.length(); i++)
    {
      String userId = mentions.getJSONObject(i).getString("id");
      User user;
      try
      {
        user = (User)api.getUserById(userId).get();
      }
      catch (InterruptedException|ExecutionException e)
      {
        continue;
      }
      this.mentions.add(user);
    }
    this.channelId = data.getString("channel_id");
    if (receiver == null) {
      this.receiver = findReceiver(this.channelId);
    } else {
      this.receiver = receiver;
    }
    if (getChannelReceiver() != null) {
      ((ImplServer)getChannelReceiver().getServer()).addMember(this.author);
    }
    api.addMessage(this);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public Channel getChannelReceiver()
  {
    if ((this.receiver instanceof Channel)) {
      return (Channel)this.receiver;
    }
    return null;
  }
  
  public User getUserReceiver()
  {
    if ((this.receiver instanceof User)) {
      return (User)this.receiver;
    }
    return null;
  }
  
  public MessageReceiver getReceiver()
  {
    return this.receiver;
  }
  
  public User getAuthor()
  {
    return this.author;
  }
  
  public boolean isPrivateMessage()
  {
    return getUserReceiver() != null;
  }
  
  public List<User> getMentions()
  {
    return new ArrayList(this.mentions);
  }
  
  public boolean isTts()
  {
    return this.tts;
  }
  
  public Future<Exception> delete()
  {
    final Message message = this;
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          ImplMessage.logger.debug("Trying to delete message (id: {}, author: {}, content: \"{}\")", new Object[] { ImplMessage.this.getId(), ImplMessage.this.getAuthor(), ImplMessage.this.getContent() });
          
          HttpResponse<JsonNode> response = Unirest.delete("https://discordapp.com/api/channels/" + ImplMessage.this.channelId + "/messages/" + ImplMessage.this.getId()).header("authorization", ImplMessage.this.api.getToken()).asJson();
          
          ImplMessage.this.api.checkResponse(response);
          ImplMessage.this.api.removeMessage(message);
          ImplMessage.logger.debug("Deleted message (id: {}, author: {}, content: \"{}\")", new Object[] { ImplMessage.this.getId(), ImplMessage.this.getAuthor(), ImplMessage.this.getContent() });
          
          ImplMessage.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
          {
            public void run()
            {
              List<MessageDeleteListener> listeners = ImplMessage.this.api.getListeners(MessageDeleteListener.class);
              synchronized (listeners)
              {
                for (MessageDeleteListener listener : listeners) {
                  listener.onMessageDelete(ImplMessage.this.api, ImplMessage.1.this.val$message);
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
  
  public Collection<MessageAttachment> getAttachments()
  {
    return Collections.unmodifiableCollection(this.attachments);
  }
  
  public Future<Message> reply(String content)
  {
    return reply(content, false);
  }
  
  public Future<Message> reply(String content, boolean tts)
  {
    return reply(content, tts, null);
  }
  
  public Future<Message> reply(String content, FutureCallback<Message> callback)
  {
    return reply(content, false, callback);
  }
  
  public Future<Message> reply(String content, boolean tts, FutureCallback<Message> callback)
  {
    return this.receiver.sendMessage(content, tts, callback);
  }
  
  public Future<Message> replyFile(File file)
  {
    return replyFile(file, null, null);
  }
  
  public Future<Message> replyFile(File file, FutureCallback<Message> callback)
  {
    return replyFile(file, null, callback);
  }
  
  public Future<Message> replyFile(InputStream inputStream, String filename)
  {
    return replyFile(inputStream, filename, null, null);
  }
  
  public Future<Message> replyFile(InputStream inputStream, String filename, FutureCallback<Message> callback)
  {
    return replyFile(inputStream, filename, null, callback);
  }
  
  public Future<Message> replyFile(File file, String comment)
  {
    return replyFile(file, comment, null);
  }
  
  public Future<Message> replyFile(File file, String comment, FutureCallback<Message> callback)
  {
    return this.receiver.sendFile(file, comment, callback);
  }
  
  public Future<Message> replyFile(InputStream inputStream, String filename, String comment)
  {
    return replyFile(inputStream, filename, comment, null);
  }
  
  public Future<Message> replyFile(InputStream inputStream, String filename, String comment, FutureCallback<Message> callback)
  {
    return this.receiver.sendFile(inputStream, filename, comment, callback);
  }
  
  public Calendar getCreationDate()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(this.creationDate.getTime());
    return calendar;
  }
  
  public int compareTo(Message other)
  {
    return this.creationDate.compareTo(other.getCreationDate());
  }
  
  public Future<Exception> edit(final String content)
  {
    this.api.getThreadPool().getExecutorService().submit(new Callable()
    {
      public Exception call()
        throws Exception
      {
        try
        {
          HttpResponse<JsonNode> response = Unirest.patch("https://discordapp.com/api/channels/" + ImplMessage.this.channelId + "/messages/" + ImplMessage.this.getId()).header("authorization", ImplMessage.this.api.getToken()).header("content-type", "application/json").body(new JSONObject().put("content", content).toString()).asJson();
          
          ImplMessage.this.api.checkResponse(response);
          final String oldContent = ImplMessage.this.getContent();
          ImplMessage.this.setContent(content);
          if (!oldContent.equals(content)) {
            ImplMessage.this.api.getThreadPool().getSingleThreadExecutorService("listeners").submit(new Runnable()
            {
              public void run()
              {
                List<MessageEditListener> listeners = ImplMessage.this.api.getListeners(MessageEditListener.class);
                synchronized (listeners)
                {
                  for (MessageEditListener listener : listeners) {
                    listener.onMessageEdit(ImplMessage.this.api, ImplMessage.this, oldContent);
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
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  private MessageReceiver findReceiver(String channelId)
  {
    for (Server server : this.api.getServers()) {
      if (server.getChannelById(channelId) != null) {
        return server.getChannelById(channelId);
      }
    }
    for (User user : this.api.getUsers()) {
      if (channelId.equals(((ImplUser)user).getUserChannelId())) {
        return user;
      }
    }
    return null;
  }
  
  public String toString()
  {
    return getAuthor().getName() + ": " + getContent() + " (id: " + getId() + ")";
  }
  
  public int hashCode()
  {
    return getId().hashCode();
  }
}
