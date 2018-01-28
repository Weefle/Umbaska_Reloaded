package uk.co.umbaska.Bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;




public class Messenger
  implements PluginMessageListener
{
  public Plugin plugin;
  public ByteArrayDataInput bytein;
  public Cache cache;
  
  public Messenger(Plugin j)
  {
    j.getServer().getMessenger().registerOutgoingPluginChannel(j, "BungeeCord");
    j.getServer().getMessenger().registerIncomingPluginChannel(j, "BungeeCord", this);
    
    this.plugin = j;
    this.cache = new Cache();
  }
  
  public Messenger(Plugin j, Boolean autoCache, Integer autoCacheHeartBeat)
  {
    j.getServer().getMessenger().registerOutgoingPluginChannel(j, "BungeeCord");
    j.getServer().getMessenger().registerIncomingPluginChannel(j, "BungeeCord", this);
    
    this.plugin = j;
    this.cache = new Cache(autoCache, autoCacheHeartBeat, j);
  }
  
  public void onPluginMessageReceived(String channel, Player player, byte[] message)
  {
    if (!channel.equals("BungeeCord")) {
      return;
    }
    this.bytein = ByteStreams.newDataInput(message);
    DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
    try
    {
      String subChannel = in.readUTF();
      if (subChannel.equals("KickPlayer"))
      {
        short size = in.readShort();
        byte[] bytes = new byte[size];
        in.readFully(bytes);
        DataInputStream cmdMsg = new DataInputStream(new ByteArrayInputStream(bytes));
        String reason = cmdMsg.readUTF();
        String executor = cmdMsg.readUTF();
        Player executingPlayer = Bukkit.getPlayer(executor);
        if (executingPlayer == null) {
          return;
        }
        executingPlayer.kickPlayer(colorString(reason));
      }
      if (subChannel.equals("Message"))
      {
        short size = in.readShort();
        byte[] bytes = new byte[size];
        in.readFully(bytes);
        DataInputStream cmdMsg = new DataInputStream(new ByteArrayInputStream(bytes));
        String msg2Send = cmdMsg.readUTF();
        String executor = cmdMsg.readUTF();
        Player executingPlayer = Bukkit.getPlayer(executor);
        if (executingPlayer == null) {
          return;
        }
        executingPlayer.sendMessage(colorString(msg2Send));
      }
      if (subChannel.equals("GetServers")) {
        String _temp = in.readUTF();
        _temp = _temp.replace(" and ", " ");
        _temp = _temp.replace(", ", " ");
        _temp = _temp.replace(" ", ", ");
        this.cache.allServers.clear();
        for (String s : _temp.split(", ")) {
          this.cache.allServers.add(s);
        }
      }
      if (subChannel.equals("PlayerList"))
      {
        String plServer = in.readUTF();
        if (plServer.equalsIgnoreCase("ALL")) {
          String originalPlayerList = in.readUTF();
          this.cache.allPlayersOnline.clear();
          for (String s : originalPlayerList.split(", ")) {
            this.cache.allPlayersOnline.add(s);
          }
        } else {
          String originalPlayerList = in.readUTF();
          List<String> playersOnline = new ArrayList();
          for (String s : originalPlayerList.split(", ")) {
            playersOnline.add(s);
          }
          this.cache.playersOnlineServer.put(plServer, playersOnline);
        }
      }
      if (subChannel.equals("PlayerCount")) {
        String inUTF = in.readUTF();
        if (inUTF.equalsIgnoreCase("ALL")) {
          this.cache.playersOnline = in.readShort();
        } else {
          this.cache.online.put(inUTF, Integer.valueOf(in.readInt()));
        }
      }
      if (subChannel.equals("ConsoleCommand"))
      {
        short size = in.readShort();
        byte[] bytes = new byte[size];
        in.readFully(bytes);
        DataInputStream cmdMsg = new DataInputStream(new ByteArrayInputStream(bytes));
        String command = cmdMsg.readUTF();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
      }
      else if (subChannel.equals("PlayerCommand"))
      {
        short size = in.readShort();
        byte[] bytes = new byte[size];
        in.readFully(bytes);
        DataInputStream cmdMsg = new DataInputStream(new ByteArrayInputStream(bytes));
        String command = cmdMsg.readUTF();
        String executor = cmdMsg.readUTF();
        Player executingPlayer = Bukkit.getPlayer(executor);
        if (executingPlayer == null) {
          return;
        }
        Bukkit.dispatchCommand(executingPlayer, command);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public String colorString(String s)
  {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  public Integer getServerCount(String server)
  {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("PlayerCount");
    out.writeUTF(server);
    try
    {
      sendAnonymous(out.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return (Integer)this.cache.online.get(server);
  }
  
  public List<String> getAllPlayersOnServer(String server)
  {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("PlayerList");
    out.writeUTF(server);
    try
    {
      sendAnonymous(out.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return (List)this.cache.playersOnlineServer.get(server);
  }
  
  public List<String> getAllPlayers()
  {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("PlayerList");
    out.writeUTF("ALL");
    try
    {
      sendAnonymous(out.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this.cache.allPlayersOnline;
  }
  
  public List<String> getAllServers()
  {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("GetServers");
    try
    {
      sendAnonymous(out.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this.cache.allServers;
  }
  
  public void sendMsgToPlayer(String player, String message) {
    ByteArrayOutputStream msg = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(msg);
    try
    {
      out.writeUTF("Message");
      out.writeUTF(player);
      out.writeUTF(message);
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    sendAnonymous(msg.toByteArray());
  }
  
  public void kickPlayer(String player, String message)
  {
    ByteArrayOutputStream msg = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(msg);
    try
    {
      out.writeUTF("KickPlayer");
      out.writeUTF(player);
      out.writeUTF(message);
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    sendAnonymous(msg.toByteArray());
  }
  
  public List<Player> getOnlinePlayers() {
    List<Player> players = new ArrayList();
    for (World w : Bukkit.getWorlds()) {
      for (Player e : w.getPlayers()) {
        players.add(e);
      }
    }
    return players;
  }
  
  public void sendAnonymous(byte[] message)
  {
    if (getOnlinePlayers().size() < 1) {
      return;
    }
    ((Player)getOnlinePlayers().get(0)).sendPluginMessage(this.plugin, "BungeeCord", message);
  }
  
  public void sendTo(byte[] message, Player[] players)
  {
    for (Player player : players) {
      player.sendPluginMessage(this.plugin, "BungeeCord", message);
    }
  }
}
