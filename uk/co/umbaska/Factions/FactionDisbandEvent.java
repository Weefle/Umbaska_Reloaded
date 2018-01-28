package uk.co.umbaska.Factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsDisband;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class FactionDisbandEvent implements Listener
{
  @EventHandler
  public void onFactionDisband(EventFactionsDisband event)
  {
    org.bukkit.command.CommandSender sender = event.getSender();
    String factionname = event.getFaction().getName();
    
    if ((sender instanceof Player)) {
      Player player = (Player)sender;
      org.bukkit.Bukkit.getServer().getPluginManager().callEvent(new EvtFactionDisbandEvent(player, factionname));
    }
  }
}
