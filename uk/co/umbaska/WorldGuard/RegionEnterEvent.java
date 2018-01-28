package uk.co.umbaska.WorldGuard;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

public class RegionEnterEvent implements org.bukkit.event.Listener
{
  @org.bukkit.event.EventHandler
  public static void onRegionEnter(PlayerMoveEvent event)
  {
    for (ProtectedRegion region : WGBukkit.getRegionManager(event.getTo().getWorld()).getRegions().values())
    {


      if ((region.contains((int)event.getTo().getX(), (int)event.getTo().getY(), (int)event.getTo().getZ())) && (!region.contains((int)event.getFrom().getX(), (int)event.getFrom().getY(), (int)event.getFrom().getZ())))
      {



        EvtRegionEnterEvent e = new EvtRegionEnterEvent(region, event.getPlayer());
        
        org.bukkit.Bukkit.getServer().getPluginManager().callEvent(e);
        if (e.isCancelled())
        {
          event.setCancelled(true);
        }
      }
    }
  }
}
