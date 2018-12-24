package uk.co.umbaska.HologramBased;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

public class HologramCentral implements org.bukkit.event.Listener
{
  private static ArrayList<Hologram> holograms = new ArrayList();
  private static HashMap<String, Boolean> is1_8 = new HashMap();
  
  public static HashMap<Entity, ArrayList<String>> holoText = new HashMap();
  
  private static boolean isWitherSkulls = true;
  

  private static org.bukkit.plugin.Plugin plugin;
  

  private static HashMap<String, ArrayList<Hologram>> viewableHolograms = new HashMap();
  
  static { plugin = Bukkit.getPluginManager().getPlugins()[0];
    Bukkit.getPluginManager().registerEvents(new HologramCentral(), plugin);
    BukkitRunnable runnable = new BukkitRunnable()
    {
      public void run() {
        for (Hologram hologram : new ArrayList(HologramCentral.holograms)) {
          if (hologram.getEntityFollowed() != null) {
            Entity entity = hologram.getEntityFollowed();
            if ((!entity.isValid()) && ((!(entity instanceof Player)) || (!((Player)entity).isOnline()))) {
              if (hologram.isRemovedOnEntityDeath()) {
                HologramCentral.removeHologram(hologram);
              } else {
                hologram.setFollowEntity(null);
              }
            } else {
              Location loc1 = hologram.entityLastLocation;
              Location loc2 = entity.getLocation();
              if (!loc1.equals(loc2))
              {
                hologram.entityLastLocation = loc2;
                Location toAdd = hologram.getRelativeToEntity();
                if ((hologram.isRelativePitch()) || (hologram.isRelativeYaw())) {
                  double r = Math.sqrt(toAdd.getX() * toAdd.getX() + toAdd.getY() * toAdd.getY() + toAdd.getZ() * toAdd.getZ());
                  
                  if (hologram.isRelativePitchControlMoreThanHeight()) {}
                  





                  toAdd.setWorld(loc2.getWorld());
                }
                Location newLoc = loc2.clone().add(toAdd);
                hologram.moveHologram(newLoc, false);
              }
            }
          }
          if (hologram.getMovement() != null) {
            hologram.moveHologram(hologram.getLocation().add(hologram.getMovement()));
          }
        }
      }
    };
    runnable.runTaskTimer(plugin, 0L, 0L);
  }
  
  static void addHologram(Hologram hologram) { if (!holograms.contains(hologram)) {
      holograms.add(hologram);
      
      for (Player p : Bukkit.getServer().getOnlinePlayers()) {
        if (hologram.isVisible(p, p.getLocation())) {
          ArrayList<Hologram> viewable = (ArrayList)viewableHolograms.get(p.getName());
          if (viewable == null) {
            viewable = new ArrayList();
            viewableHolograms.put(p.getName(), viewable);
          }
          viewable.add(hologram);
          try {
            for (PacketContainer packet : hologram.getSpawnPackets(p)) {
              ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet, false);
            }
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
  
  static void addHologram(Player p, Hologram hologram) {
    if ((holograms.contains(hologram)) && 
      (hologram.isVisible(p, p.getLocation()))) {
      ArrayList<Hologram> viewable = (ArrayList)viewableHolograms.get(p.getName());
      if (viewable == null) {
        viewable = new ArrayList();
        viewableHolograms.put(p.getName(), viewable);
      }
      viewable.add(hologram);
      try {
        for (PacketContainer packet : hologram.getSpawnPackets(p)) {
          ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet, false);
        }
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
  
  static boolean is1_8(Player player)
  {
    return true;
  }
  
  static boolean isInUse(Hologram hologram) {
    return holograms.contains(hologram);
  }
  
  public static boolean isUsingWitherSkulls() {
    return isWitherSkulls;
  }
  
  static void removeHologram(Hologram hologram) {
    if (holograms.contains(hologram)) {
      holograms.remove(hologram);
      Iterator<Map.Entry<String, ArrayList<Hologram>>> itel = viewableHolograms.entrySet().iterator();
      while (itel.hasNext()) {
        Map.Entry<String, ArrayList<Hologram>> entry = (Map.Entry)itel.next();
        if (((ArrayList)entry.getValue()).remove(hologram)) {
          Player player = Bukkit.getPlayerExact((String)entry.getKey());
          if (player != null) {
            try {
              ProtocolLibrary.getProtocolManager().sendServerPacket(player, hologram.getDestroyPacket(player), false);
            }
            catch (InvocationTargetException e) {
              e.printStackTrace();
            }
          }
          if ((player == null) || (((ArrayList)entry.getValue()).isEmpty())) {
            itel.remove();
          }
        }
      }
    }
  }
  
  static void removeHologram(Player player, Hologram hologram) {
    if ((holograms.contains(hologram)) && 
      (viewableHolograms.containsKey(player.getName())) && 
      (((ArrayList)viewableHolograms.get(player.getName())).remove(hologram)) && 
      (((ArrayList)viewableHolograms.get(player.getName())).isEmpty())) {
      viewableHolograms.remove(player.getName());
      try {
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, hologram.getDestroyPacket(player), false);
      }
      catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
  


  public static Location rotate(Location loc, double yaw, double pitch)
  {
    double x = loc.getX();
    double y = loc.getY();
    double z = loc.getZ();
    yaw = Math.toRadians(yaw);
    pitch = Math.toRadians(pitch);
    double newX = x * Math.cos(yaw) - z * Math.sin(yaw);
    double newZ = x * Math.sin(yaw) + z * Math.cos(yaw);
    double newY = y * (yaw / 90.0D);
    return new Location(null, newX, newY, newZ);
  }
  
  public static void setUsingWitherSkulls(boolean usingSkulls) {
    isWitherSkulls = usingSkulls;
  }
  
  private void doCheck(Player p, Location loc) {
    ArrayList<Hologram> viewable = (ArrayList)viewableHolograms.get(p.getName());
    if (viewable == null) {
      viewable = new ArrayList();
    }
    for (Hologram hologram : holograms) {
      boolean view = hologram.isVisible(p, loc);
      if (view != viewable.contains(hologram)) {
        if (view) {
          viewable.add(hologram);
          try {
            for (PacketContainer packet : hologram.getSpawnPackets(p)) {
              ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet, false);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          viewable.remove(hologram);
          try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, hologram.getDestroyPacket(p), false);
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
    }
    if (viewable.isEmpty()) {
      viewableHolograms.remove(p.getName());
    } else if (!viewableHolograms.containsKey(p.getName())) {
      viewableHolograms.put(p.getName(), viewable);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onMove(PlayerMoveEvent event) {
    doCheck(event.getPlayer(), event.getTo());
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    viewableHolograms.remove(event.getPlayer().getName());
    is1_8.remove(event.getPlayer().getName());
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onTeleport(PlayerTeleportEvent event) {
    doCheck(event.getPlayer(), event.getTo());
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onUnload(WorldUnloadEvent event) {
    for (Hologram hologram : new ArrayList(holograms)) {
      if (hologram.getLocation().getWorld() == event.getWorld()) {
        removeHologram(hologram);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onWorldSwitch(PlayerChangedWorldEvent event) {
    viewableHolograms.remove(event.getPlayer().getName());
    doCheck(event.getPlayer(), event.getPlayer().getLocation());
  }
}
