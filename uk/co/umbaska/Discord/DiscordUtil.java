package uk.co.umbaska.Discord;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import java.util.HashMap;

public class DiscordUtil
{
  private static HashMap<DiscordAPI, Boolean> connections = new HashMap<>();
  
  public static DiscordAPI connect(String email, String password)
  {
    DiscordAPI api = Javacord.getApi(email, password);
    connections.put(api, Boolean.valueOf(false));
    return api;
  }
}
