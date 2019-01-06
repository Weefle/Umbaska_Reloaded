package uk.co.umbaska.LargeSk.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import uk.co.umbaska.Umbaska;

public class LargeMessenger
  implements PluginMessageListener
{
  Plugin umb = Umbaska.get();
  Server srv = Bukkit.getServer();
  
  public LargeMessenger getMessenger()
  {
    return new LargeMessenger();
  }
  
  public void registerMessenger()
  {
    this.srv.getMessenger().registerOutgoingPluginChannel(this.umb, "BungeeCord");
    this.srv.getMessenger().registerIncomingPluginChannel(this.umb, "BungeeCord", this);
  }
  
  public void onPluginMessageReceived(String channel, Player player, byte[] message)
  {
    if (!channel.equals("BungeeCord")) {
      return;
    }
    ByteArrayDataInput in = ByteStreams.newDataInput(message);
    String sub = in.readUTF();
    if (!sub.equals("LargeSkEff")) {
      return;
    }
    String msg = in.readUTF();
    this.srv.getPluginManager().callEvent(new EvtPluginMessageReceived(msg));
  }
}
