package de.btobastian.javacord.utils.handler.message;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageDeleteListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class MessageDeleteHandler
  extends PacketHandler
{
  public MessageDeleteHandler(ImplDiscordAPI api)
  {
    super(api, true, "MESSAGE_DELETE");
  }
  
  public void handle(JSONObject packet)
  {
    String messageId = packet.getString("id");
    final Message message = this.api.getMessageById(messageId);
    if (message == null) {
      return;
    }
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<MessageDeleteListener> listeners = MessageDeleteHandler.this.api.getListeners(MessageDeleteListener.class);
        synchronized (listeners)
        {
          for (MessageDeleteListener listener : listeners) {
            listener.onMessageDelete(MessageDeleteHandler.this.api, message);
          }
        }
      }
    });
  }
}
