package de.btobastian.javacord.utils.handler;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.impl.ImplUser;
import de.btobastian.javacord.utils.DiscordWebsocketAdapter;
import de.btobastian.javacord.utils.PacketHandler;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadyHandler
  extends PacketHandler
{
  public ReadyHandler(ImplDiscordAPI api)
  {
    super(api, false, "READY");
  }
  
  public void handle(JSONObject packet)
  {
    long heartbeatInterval = packet.getLong("heartbeat_interval");
    this.api.getSocketAdapter().startHeartbeat(heartbeatInterval);
    
    JSONArray guilds = packet.getJSONArray("guilds");
    for (int i = 0; i < guilds.length(); i++)
    {
      JSONObject guild = guilds.getJSONObject(i);
      if ((!guild.has("unavailable")) || (!guild.getBoolean("unavailable"))) {
        new ImplServer(guild, this.api);
      }
    }
    JSONArray privateChannels = packet.getJSONArray("private_channels");
    for (int i = 0; i < privateChannels.length(); i++)
    {
      JSONObject privateChannel = privateChannels.getJSONObject(i);
      String id = privateChannel.getString("id");
      User user = this.api.getOrCreateUser(privateChannel.getJSONObject("recipient"));
      if (user != null) {
        ((ImplUser)user).setUserChannelId(id);
      }
    }
    this.api.setYourself(this.api.getOrCreateUser(packet.getJSONObject("user")));
  }
}
