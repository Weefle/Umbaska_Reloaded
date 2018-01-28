package uk.co.umbaska.Utils;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;

public class TPSUtil extends org.bukkit.scheduler.BukkitRunnable
{
  private long lastTick;
  private Deque<Long> tickIntervals;
  int resolution = 40;
  
  public TPSUtil(Plugin plugin) {
    this.lastTick = System.currentTimeMillis();
    this.tickIntervals = new ArrayDeque(Collections.nCopies(this.resolution, Long.valueOf(50L)));
    runTaskTimer(plugin, 1L, 1L);
  }
  
  public void run()
  {
    long curr = System.currentTimeMillis();
    long delta = curr - this.lastTick;
    this.lastTick = curr;
    this.tickIntervals.removeFirst();
    this.tickIntervals.addLast(Long.valueOf(delta));
  }
  
  public double getTPS() {
    int base = 0;
    for (Iterator i$ = this.tickIntervals.iterator(); i$.hasNext();) { long delta = ((Long)i$.next()).longValue();
      base = (int)(base + delta);
    }
    return 1000.0D / (base / this.resolution);
  }
}
