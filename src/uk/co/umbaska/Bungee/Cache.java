package uk.co.umbaska.Bungee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import uk.co.umbaska.Main;




public class Cache
{
  public HashMap<String, Integer> online = new HashMap<>();
  public HashMap<String, List<String>> playersOnlineServer = new HashMap<>();
  public List<String> allPlayersOnline = new ArrayList<>();
  public List<String> allServers = new ArrayList<>();
  public short playersOnline = 0;
  
  public Cache() {}
  
  public Cache(Boolean autoCache, Integer autoCacheHeartBeat, Plugin p)
  {
    if (autoCache.booleanValue()) {
      Bukkit.getScheduler().runTaskTimerAsynchronously(p, new Runnable()
      {
        public void run() {
          Main.messenger.getAllServers();
          for (String s : Cache.this.allServers) {
            Main.messenger.getAllPlayersOnServer(s);
            Main.messenger.getServerCount(s);
          }
          Main.messenger.getAllPlayers();
          Main.messenger.getServerCount("ALL"); } }, autoCacheHeartBeat.intValue(), autoCacheHeartBeat.intValue());
    }
  }
}
