package uk.co.umbaska.LargeSk.util;

import java.io.IOException;

import org.bukkit.Bukkit;

import uk.co.umbaska.Main;

public class MetricsManager
{
  public static void scheduleEnableMetrics()
  {
    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), new Runnable()
    {
      public void run() {}
    }, 100L);
  }
  
  public static void enableMetrics()
  {
    try
    {
      Metrics metrics = new Metrics(Main.getInstance());
      metrics.start();
    }
    catch (IOException e)
    {
      Main.getInstance().getLogger().warning("Enabling Metrics failed ¯\\\\_(ツ)_/¯");
      e.printStackTrace();
    }
  }
  
  public static void disableMetrics()
  {
    try
    {
      Metrics metrics = new Metrics(Main.getInstance());
      metrics.disable();
    }
    catch (IOException e)
    {
    	Main.getInstance().getLogger().warning("Disabling Metrics failed ¯\\\\_(ツ)_/¯");
      e.printStackTrace();
    }
  }
}
