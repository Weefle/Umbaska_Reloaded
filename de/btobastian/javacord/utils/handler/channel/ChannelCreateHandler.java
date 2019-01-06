package de.btobastian.javacord.utils.handler.channel;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.impl.ImplChannel;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.entities.impl.ImplUser;
import de.btobastian.javacord.entities.impl.ImplVoiceChannel;
import de.btobastian.javacord.listener.channel.ChannelCreateListener;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelCreateListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class ChannelCreateHandler
  extends PacketHandler
{
  public ChannelCreateHandler(ImplDiscordAPI api)
  {
    super(api, true, "CHANNEL_CREATE");
  }
  
  public void handle(JSONObject packet)
  {
    boolean isPrivate = packet.getBoolean("is_private");
    if (isPrivate)
    {
      User recipient = this.api.getOrCreateUser(packet.getJSONObject("recipient"));
      ((ImplUser)recipient).setUserChannelId(packet.getString("id"));
      return;
    }
    Server server = this.api.getServerById(packet.getString("guild_id"));
    if (packet.getString("type").equals("text")) {
      handleServerTextChannel(packet, server);
    } else {
      handleServerVoiceChannel(packet, server);
    }
  }
  
  private void handleServerTextChannel(JSONObject packet, Server server)
  {
    if (server.getChannelById(packet.getString("id")) != null) {
      return;
    }
    final Channel channel = new ImplChannel(packet, (ImplServer)server, this.api);
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<ChannelCreateListener> listeners = ChannelCreateHandler.this.api.getListeners(ChannelCreateListener.class);
        synchronized (listeners)
        {
          for (ChannelCreateListener listener : listeners) {
            listener.onChannelCreate(ChannelCreateHandler.this.api, channel);
          }
        }
      }
    });
  }
  
  private void handleServerVoiceChannel(JSONObject packet, Server server)
  {
    if (server.getVoiceChannelById(packet.getString("id")) != null) {
      return;
    }
    final VoiceChannel channel = new ImplVoiceChannel(packet, (ImplServer)server, this.api);
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<VoiceChannelCreateListener> listeners = ChannelCreateHandler.this.api.getListeners(VoiceChannelCreateListener.class);
        synchronized (listeners)
        {
          for (VoiceChannelCreateListener listener : listeners) {
            listener.onVoiceChannelCreate(ChannelCreateHandler.this.api, channel);
          }
        }
      }
    });
  }
}
