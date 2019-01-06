package uk.co.umbaska.LargeSk.util;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import uk.co.umbaska.Umbaska;

public class MetricsManager
{
  public static void scheduleEnableMetrics()
  {
    Bukkit.getScheduler().runTaskLaterAsynchronously(Umbaska.get(), new Runnable()
    {
      public void run() {}
    }, 100L);
  }
  
  public static void enableMetrics()
  {
    try
    {
      Metrics metrics = new Metrics(Umbaska.get());
      metrics.start();
    }
    catch (IOException e)
    {
      Umbaska.logWarning(new Object[] { "Enabling Metrics failed ¯\\_(ツ)_/¯" });
      e.printStackTrace();
    }
  }
  
  public static void disableMetrics()
  {
    try
    {
      Metrics metrics = new Metrics(Umbaska.get());
      metrics.disable();
    }
    catch (IOException e)
    {
      Umbaska.logWarning(new Object[] { "Disabling Metrics failed ¯\\_(ツ)_/¯" });
      e.printStackTrace();
    }
  }
}
