package uk.co.umbaska.Misc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportCallEvent implements Listener
{
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onPlayerTeleport(PlayerTeleportEvent event)
  {
    org.bukkit.entity.Player player = event.getPlayer();
    if (event.getCause() != org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.UNKNOWN) {}
  }
  

  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onWorldChange(EntityTeleportEvent e)
  {
    Entity ent = e.getEntity();
    if (e.getFrom().getWorld() != e.getTo().getWorld()) {}
  }
}
