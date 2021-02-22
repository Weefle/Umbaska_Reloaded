package uk.co.umbaska.Factions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.massivecraft.factions.event.EventFactionsCreate;

public class FactionCreateEvent implements Listener
{
  @EventHandler
  public void onFactionCreate(EventFactionsCreate event)
  {
    org.bukkit.command.CommandSender sender = event.getSender();
    String factionname = event.getFactionName();
    
    if ((sender instanceof Player)) {
      Player player = (Player)sender;
      Bukkit.getServer().getPluginManager().callEvent(new EvtFactionCreateEvent(player, factionname));
    }
  }
}
