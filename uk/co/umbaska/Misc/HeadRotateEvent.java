package uk.co.umbaska.Misc;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

public class HeadRotateEvent implements org.bukkit.event.Listener
{
  @org.bukkit.event.EventHandler
  public void onHeadRotate(PlayerMoveEvent event)
  {
    if ((event.getFrom().getYaw() != event.getTo().getYaw()) && (event.getFrom().getPitch() != event.getTo().getPitch()))
    {
      org.bukkit.entity.Player player = event.getPlayer();
      EvtHeadRotateEvent e = new EvtHeadRotateEvent(player);
      org.bukkit.Bukkit.getServer().getPluginManager().callEvent(e);
      if (e.isCancelled()) {
        event.getTo().setPitch(event.getTo().getPitch() - (event.getTo().getPitch() - event.getFrom().getPitch()));
        


        event.getTo().setYaw(event.getTo().getYaw() - (event.getTo().getYaw() - event.getFrom().getYaw()));
        


        return;
      }
    }
  }
}
