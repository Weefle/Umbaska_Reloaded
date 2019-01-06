package de.btobastian.javacord.utils.handler.message;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.impl.ImplMessage;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class MessageCreateHandler
  extends PacketHandler
{
  public MessageCreateHandler(ImplDiscordAPI api)
  {
    super(api, true, "MESSAGE_CREATE");
  }
  
  public void handle(JSONObject packet)
  {
    String messageId = packet.getString("id");
    Message messageTemp = this.api.getMessageById(messageId);
    if (messageTemp == null) {
      messageTemp = new ImplMessage(packet, this.api, null);
    }
    final Message message = messageTemp;
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<MessageCreateListener> listeners = MessageCreateHandler.this.api.getListeners(MessageCreateListener.class);
        synchronized (listeners)
        {
          for (MessageCreateListener listener : listeners) {
            listener.onMessageCreate(MessageCreateHandler.this.api, message);
          }
        }
      }
    });
  }
}
