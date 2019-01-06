package de.btobastian.javacord.utils.handler.message;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.utils.PacketHandler;
import org.json.JSONObject;

public class MessageAckHandler
  extends PacketHandler
{
  public MessageAckHandler(ImplDiscordAPI api)
  {
    super(api, true, "MESSAGE_ACK");
  }
  
  public void handle(JSONObject packet) {}
}
