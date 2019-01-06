package de.btobastian.javacord.utils.handler;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Region;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.impl.ImplUser;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.utils.DiscordWebsocketAdapter;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadyReconnectHandler
  extends PacketHandler
{
  public ReadyReconnectHandler(ImplDiscordAPI api)
  {
    super(api, false, "READY_RECONNECT");
  }
  
  public void handle(JSONObject packet)
  {
    long heartbeatInterval = packet.getLong("heartbeat_interval");
    this.api.getSocketAdapter().startHeartbeat(heartbeatInterval);
    
    JSONArray guilds = packet.getJSONArray("guilds");
    for (int i = 0; i < guilds.length(); i++)
    {
      JSONObject guild = guilds.getJSONObject(i);
      if ((!guild.has("unavailable")) || (!guild.getBoolean("unavailable")))
      {
        Server server = this.api.getServerById(guild.getString("id"));
        if (server == null)
        {
          new ImplServer(guild, this.api);
        }
        else
        {
          ((ImplServer)server).setName(guild.getString("name"));
          ((ImplServer)server).setRegion(Region.getRegionByKey(guild.getString("region")));
          ((ImplServer)server).setMemberCount(guild.getInt("member_count"));
          
          JSONArray members = new JSONArray();
          if (guild.has("members")) {
            members = guild.getJSONArray("members");
          }
          for (int j = 0; j < members.length(); j++)
          {
            User member = this.api.getOrCreateUser(members.getJSONObject(j).getJSONObject("user"));
            ((ImplServer)server).addMember(member);
            
            JSONArray memberRoles = members.getJSONObject(j).getJSONArray("roles");
            for (int k = 0; k < memberRoles.length(); k++)
            {
              Role role = server.getRoleById(memberRoles.getString(k));
              if (role != null) {
                ((ImplRole)role).addUserNoUpdate(member);
              }
            }
          }
          JSONArray presences = new JSONArray();
          if (guild.has("presences")) {
            presences = guild.getJSONArray("presences");
          }
          for (int j = 0; j < presences.length(); j++)
          {
            JSONObject presence = presences.getJSONObject(j);
            User user;
            try
            {
              user = (User)this.api.getUserById(presence.getJSONObject("user").getString("id")).get();
            }
            catch (InterruptedException|ExecutionException e)
            {
              e.printStackTrace();
              continue;
            }
            if ((presence.has("game")) && (!presence.isNull("game")) && 
              (presence.getJSONObject("game").has("name")) && (!presence.getJSONObject("game").isNull("name"))) {
              ((ImplUser)user).setGame(presence.getJSONObject("game").getString("name"));
            }
          }
        }
      }
    }
    JSONArray privateChannels = packet.getJSONArray("private_channels");
    for (int i = 0; i < privateChannels.length(); i++)
    {
      JSONObject privateChannel = privateChannels.getJSONObject(i);
      String id = privateChannel.getString("id");
      User user = this.api.getOrCreateUser(privateChannel.getJSONObject("recipient"));
      ((ImplUser)user).setUserChannelId(id);
    }
  }
}
