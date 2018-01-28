package uk.co.umbaska.Misc;

import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.PluginManager;

public class CropGrowEvent implements org.bukkit.event.Listener
{
  @org.bukkit.event.EventHandler
  public void onTreeGrow(StructureGrowEvent event)
  {
    if (event.getBlocks().size() > 0) {
      EvtCropGrowEvent e = new EvtCropGrowEvent((org.bukkit.block.BlockState)event.getBlocks().get(0));
      org.bukkit.Bukkit.getServer().getPluginManager().callEvent(e);
      if (e.isCancelled()) {
        event.setCancelled(true);
      }
    }
  }
  
  @org.bukkit.event.EventHandler
  public void onCropGrow(BlockGrowEvent event) {
    EvtCropGrowEvent e = new EvtCropGrowEvent(event.getNewState());
    org.bukkit.Bukkit.getServer().getPluginManager().callEvent(e);
    if (e.isCancelled()) {
      event.setCancelled(true);
    }
  }
}
