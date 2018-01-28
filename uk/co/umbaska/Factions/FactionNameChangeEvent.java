package uk.co.umbaska.Factions;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsNameChange;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class FactionNameChangeEvent implements Listener
{
  @EventHandler
  public void onFactionNameChange(EventFactionsNameChange event)
  {
    org.bukkit.command.CommandSender sender = event.getSender();
    Faction faction = event.getFaction();
    
    if ((sender instanceof Player)) {
      Player player = (Player)sender;
      Bukkit.getServer().getPluginManager().callEvent(new EvtFactionNameChangeEvent(player, faction));
    }
  }
}
