package de.btobastian.javacord.entities.message.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageHistory;
import de.btobastian.javacord.utils.LoggerUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

public class ImplMessageHistory
  implements MessageHistory
{
  private static final Logger logger = LoggerUtil.getLogger(ImplMessageHistory.class);
  private final ConcurrentHashMap<String, Message> messages = new ConcurrentHashMap();
  private Message oldestMessage = null;
  private Message newestMessage = null;
  
  public ImplMessageHistory(ImplDiscordAPI api, String channelId, int limit)
    throws Exception
  {
    this(api, channelId, null, false, limit);
  }
  
  public ImplMessageHistory(ImplDiscordAPI api, String channelId, String messageId, boolean before, int limit)
    throws Exception
  {
    int step = 0;
    if (messageId == null) {
      before = true;
    }
    logger.debug("Trying to get message history (channel id: {}, message id: {}, before: {}, limit: {}", new Object[] { channelId, messageId == null ? "none" : messageId, Boolean.valueOf(before), Integer.valueOf(limit) });
    for (int i = limit / 100; i > 0; i--)
    {
      int receivedMessages;
      int receivedMessages;
      if (step++ == 0) {
        receivedMessages = request(api, channelId, messageId, before, 100);
      } else {
        receivedMessages = request(api, channelId, before ? this.oldestMessage.getId() : this.newestMessage.getId(), before, 100);
      }
      if (receivedMessages == 0) {
        return;
      }
    }
    if (step == 0) {
      request(api, channelId, messageId, before, limit % 100);
    } else {
      request(api, channelId, before ? this.oldestMessage.getId() : this.newestMessage.getId(), before, limit % 100);
    }
    logger.debug("Got message history (channel id: {}, message id: {}, before: {}, limit: {}, amount: {}", new Object[] { channelId, messageId == null ? "none" : messageId, Boolean.valueOf(before), Integer.valueOf(limit), Integer.valueOf(this.messages.size()) });
  }
  
  private int request(ImplDiscordAPI api, String channelId, String messageId, boolean before, int limit)
    throws Exception
  {
    if (limit <= 0) {
      return 0;
    }
    logger.debug("Requesting part of message history (channel id: {}, message id: {}, before: {}, limit: {}", new Object[] { channelId, messageId == null ? "none" : messageId, Boolean.valueOf(before), Integer.valueOf(limit) });
    
    String link = "https://discordapp.com/api/channels/" + channelId + "/messages?&" + (before ? "before" : "after") + "=" + messageId + "&limit=" + limit;
    
    HttpResponse<JsonNode> response = Unirest.get(link).header("authorization", api.getToken()).asJson();
    api.checkResponse(response);
    JSONArray messages = ((JsonNode)response.getBody()).getArray();
    for (int i = 0; i < messages.length(); i++)
    {
      JSONObject messageJson = messages.getJSONObject(i);
      String id = messageJson.getString("id");
      Message message = api.getMessageById(id);
      if (message == null) {
        message = new ImplMessage(messageJson, api, null);
      }
      if ((this.newestMessage == null) || (message.compareTo(this.newestMessage) > 0)) {
        this.newestMessage = message;
      }
      if ((this.oldestMessage == null) || (message.compareTo(this.oldestMessage) < 0)) {
        this.oldestMessage = message;
      }
      this.messages.put(id, message);
    }
    return messages.length();
  }
  
  public Message getMessageById(String id)
  {
    return (Message)this.messages.get(id);
  }
  
  public Iterator<Message> iterator()
  {
    return getMessages().iterator();
  }
  
  public Collection<Message> getMessages()
  {
    return Collections.unmodifiableCollection(this.messages.values());
  }
  
  public Message getNewestMessage()
  {
    if (this.newestMessage != null) {
      return this.newestMessage;
    }
    Message newestMessage = null;
    for (Message message : this.messages.values()) {
      if (newestMessage == null) {
        newestMessage = message;
      } else if (message.compareTo(newestMessage) > 0) {
        newestMessage = message;
      }
    }
    return newestMessage;
  }
  
  public Message getOldestMessage()
  {
    if (this.oldestMessage != null) {
      return this.oldestMessage;
    }
    Message oldestMessage = null;
    for (Message message : this.messages.values()) {
      if (oldestMessage == null) {
        oldestMessage = message;
      } else if (message.compareTo(oldestMessage) < 0) {
        oldestMessage = message;
      }
    }
    return oldestMessage;
  }
  
  public List<Message> getMessagesSorted()
  {
    List<Message> messages = new ArrayList(this.messages.values());
    Collections.sort(messages);
    return messages;
  }
  
  public void removeMessage(String id)
  {
    this.messages.remove(id);
    if ((this.newestMessage != null) && (this.newestMessage.getId().equals(id))) {
      this.newestMessage = null;
    }
    if ((this.oldestMessage != null) && (this.oldestMessage.getId().equals(id))) {
      this.oldestMessage = null;
    }
  }
}
