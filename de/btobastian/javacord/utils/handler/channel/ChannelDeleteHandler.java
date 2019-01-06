package de.btobastian.javacord.utils.handler.channel;

import de.btobastian.javacord.ImplDiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.listener.channel.ChannelDeleteListener;
import de.btobastian.javacord.listener.voicechannel.VoiceChannelDeleteListener;
import de.btobastian.javacord.utils.PacketHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

public class ChannelDeleteHandler
  extends PacketHandler
{
  public ChannelDeleteHandler(ImplDiscordAPI api)
  {
    super(api, true, "CHANNEL_DELETE");
  }
  
  public void handle(JSONObject packet)
  {
    boolean isPrivate = packet.getBoolean("is_private");
    if (isPrivate) {
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
    final Channel channel = server.getChannelById(packet.getString("id"));
    ((ImplServer)server).removeChannel(channel);
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<ChannelDeleteListener> listeners = ChannelDeleteHandler.this.api.getListeners(ChannelDeleteListener.class);
        synchronized (listeners)
        {
          for (ChannelDeleteListener listener : listeners) {
            listener.onChannelDelete(ChannelDeleteHandler.this.api, channel);
          }
        }
      }
    });
  }
  
  private void handleServerVoiceChannel(JSONObject packet, Server server)
  {
    final VoiceChannel channel = server.getVoiceChannelById(packet.getString("id"));
    ((ImplServer)server).removeVoiceChannel(channel);
    this.listenerExecutorService.submit(new Runnable()
    {
      public void run()
      {
        List<VoiceChannelDeleteListener> listeners = ChannelDeleteHandler.this.api.getListeners(VoiceChannelDeleteListener.class);
        synchronized (listeners)
        {
          for (VoiceChannelDeleteListener listener : listeners) {
            listener.onVoiceChannelDelete(ChannelDeleteHandler.this.api, channel);
          }
        }
      }
    });
  }
}
