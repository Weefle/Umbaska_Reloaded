package uk.co.umbaska.LargeSk.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.google.common.base.Objects;

public class PlayerChunkChangeEvt
  implements Listener
{
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void chunkChangeDetect(PlayerMoveEvent event)
  {
    Chunk from = event.getFrom().getChunk();
    
    Chunk to = event.getTo().getChunk();
    if (!Objects.equal(from, to)) {
      Bukkit.getServer().getPluginManager().callEvent(new EvtPlayerChunkChange(event.getPlayer(), from, to));
    }
  }
}
