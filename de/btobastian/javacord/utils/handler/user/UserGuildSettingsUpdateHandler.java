package de.btobastian.javacord.utils.handler.user;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.utils.PacketHandler;
import org.json.JSONObject;

public class UserGuildSettingsUpdateHandler
  extends PacketHandler
{
  public UserGuildSettingsUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "USER_GUILD_SETTINGS_UPDATE");
  }
  
  public void handle(JSONObject packet) {}
}
