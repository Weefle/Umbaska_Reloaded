package de.btobastian.javacord.utils.handler.message;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.impl.ImplMessage;
import de.btobastian.javacord.listener.message.MessageEditListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class MessageUpdateHandler
  extends PacketHandler
{
  public MessageUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "MESSAGE_UPDATE");
  }
  
  public void handle(JSONObject packet)
  {
    String messageId = packet.getString("id");
    final Message message = this.api.getMessageById(messageId);
    if (message == null) {
      return;
    }
    final String oldContent = message.getContent();
    if (!packet.has("content")) {
      return;
    }
    ((ImplMessage)message).setContent(packet.getString("content"));
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<MessageEditListener> listeners = MessageUpdateHandler.this.api.getListeners(MessageEditListener.class);
        synchronized (listeners)
        {
          for (MessageEditListener listener : listeners) {
            listener.onMessageEdit(MessageUpdateHandler.this.api, message, oldContent);
          }
        }
      }
    });
  }
}
