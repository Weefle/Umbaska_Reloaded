package de.btobastian.javacord.utils.handler.user;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.impl.ImplUser;
import de.btobastian.javacord.entities.permissions.impl.ImplRole;
import de.btobastian.javacord.listener.user.UserChangeGameListener;
import de.btobastian.javacord.listener.user.UserChangeNameListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONObject;

public class PresenceUpdateHandler
  extends PacketHandler
{
  public PresenceUpdateHandler(ImplDiscordAPI api)
  {
    super(api, true, "PRESENCE_UPDATE");
  }
  
  public void handle(JSONObject packet)
  {
    final User user = this.api.getOrCreateUser(packet.getJSONObject("user"));
    
    Server server = null;
    if (packet.has("guild_id")) {
      server = this.api.getServerById(packet.getString("guild_id"));
    }
    if (server != null) {
      ((ImplServer)server).addMember(user);
    }
    if ((server != null) && (packet.has("roles")))
    {
      JSONArray roleIds = packet.getJSONArray("roles");
      for (int i = 0; i < roleIds.length(); i++) {
        ((ImplRole)server.getRoleById(roleIds.getString(i))).addUserNoUpdate(user);
      }
    }
    if (packet.getJSONObject("user").has("username"))
    {
      String name = packet.getJSONObject("user").getString("username");
      if (!user.getName().equals(name))
      {
        final String oldName = user.getName();
        ((ImplUser)user).setName(name);
        this.listenerExecutorService.submit(new Runnable()
        {
          public void run()
          {
            List<UserChangeNameListener> listeners = PresenceUpdateHandler.this.api.getListeners(UserChangeNameListener.class);
            synchronized (listeners)
            {
              for (UserChangeNameListener listener : listeners) {
                listener.onUserChangeName(PresenceUpdateHandler.this.api, user, oldName);
              }
            }
          }
        });
      }
    }
    if ((packet.has("game")) && (!packet.isNull("game")) && 
      (packet.getJSONObject("game").has("name")))
    {
      String game = packet.getJSONObject("game").get("name").toString();
      String oldGame = user.getGame();
      if (((game == null) && (oldGame != null)) || ((game != null) && (oldGame == null)) || ((game != null) && (!game.equals(oldGame))))
      {
        ((ImplUser)user).setGame(game);
        List<UserChangeGameListener> listeners = this.api.getListeners(UserChangeGameListener.class);
        synchronized (listeners)
        {
          for (UserChangeGameListener listener : listeners) {
            listener.onUserChangeGame(this.api, user, oldGame);
          }
        }
      }
    }
  }
}
