package uk.co.umbaska.Misc;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

public class RepairEvent implements org.bukkit.event.Listener
{
  @org.bukkit.event.EventHandler
  public static void onInventoryClick(InventoryClickEvent event)
  {
    org.bukkit.entity.HumanEntity ent = event.getWhoClicked();
    if ((ent instanceof Player)) {
      Player player = (Player)ent;
      if ((event.getInventory() instanceof AnvilInventory)) {
        AnvilInventory anvil = (AnvilInventory)event.getInventory();
        if (event.getRawSlot() == event.getView().convertSlot(event.getRawSlot()))
        {
          if (event.getRawSlot() == 2) {
            if (anvil.getContents()[0] == null)
              return;
            if (anvil.getContents()[1] == null)
              return;
            if ((anvil.getContents()[0].getType() != Material.AIR) && (anvil.getContents()[1].getType() != Material.AIR))
            {
              ItemStack item = event.getCurrentItem();
              if (item.getType() != Material.AIR) {
                EvtRepairEvent e = new EvtRepairEvent(player, item);
                
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(e);
                
                if (e.isCancelled())
                {
                  event.setCancelled(true);
                }
              }
            }
          }
        }
      }
    }
  }
}
