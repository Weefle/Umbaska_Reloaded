package uk.co.umbaska.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportCallEvent
  implements Listener
{
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void onPlayerTeleport(PlayerTeleportEvent event)
  {
    Player player = event.getPlayer();
    if (event.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN)
    {
      player.getLocation().getChunk().load();
      EvtTeleportCallEvent e = new EvtTeleportCallEvent(player);
      Bukkit.getServer().getPluginManager().callEvent(e);
    }
  }
}
