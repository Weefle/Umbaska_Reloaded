package uk.co.umbaska.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class FreezeListener
  implements Listener
{
  private Plugin p;
  private List<Player> frozen = new ArrayList();
  private HashMap<Player, Float> flightSpeedHolder = new HashMap();
  private FlightTracker flightTracker;
  
  public FreezeListener(Plugin p) {
    this.p = p;
    this.flightTracker = new FlightTracker(p);
    Bukkit.getPluginManager().registerEvents(this, p);
  }
  
  public void setFreezeState(Player p, Boolean isFrozen)
  {
    if ((!isFrozen(p).booleanValue()) && (isFrozen.booleanValue())) {
      this.flightSpeedHolder.put(p, Float.valueOf(p.getFlySpeed()));
      this.frozen.add(p);
    } else {
      this.frozen.remove(p);
      p.setFlySpeed(((Float)this.flightSpeedHolder.get(p)).floatValue());
      p.setFlying(false);
      p.setAllowFlight(false);
      this.flightSpeedHolder.remove(p);
    }
  }
  
  public Boolean isFrozen(Player p) {
    return Boolean.valueOf(this.frozen.contains(p));
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onFlightToggle(PlayerToggleFlightEvent e) {
    if (isFrozen(e.getPlayer()).booleanValue()) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerMove(PlayerMoveEvent e) {
    if ((isFrozen(e.getPlayer()).booleanValue()) && (
      (e.getTo().getX() != e.getFrom().getX()) || (e.getTo().getZ() != e.getFrom().getZ()))) {
      Location newTo = e.getFrom();
      newTo.setPitch(e.getTo().getPitch());
      newTo.setYaw(e.getTo().getYaw());
      
      e.setTo(newTo);
    }
  }
  
  private class FlightTracker {
    BukkitTask task;
    
    public FlightTracker(Plugin p) {
      this.task = Bukkit.getScheduler().runTaskTimer(p, new Runnable()
      {
        public void run() {
          for (Player p : FreezeListener.this.frozen) {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setFlySpeed(0.0F); } } }, 1L, 1L);
    }
  }
}
