package uk.co.umbaska.LargeSk.events;

import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class PlayerViolationEvt
  implements Listener
{
  @EventHandler
  public void onPlayerViolation(PlayerViolationEvent e)
  {
    Player player = e.getPlayer();
    HackType hack = e.getHackType();
    String message = e.getMessage();
    Integer violations = Integer.valueOf(e.getViolations());
    Bukkit.getServer().getPluginManager().callEvent(new EvtPlayerViolation(player, hack, message, violations));
  }
}
