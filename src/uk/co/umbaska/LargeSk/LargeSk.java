package uk.co.umbaska.LargeSk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import uk.co.umbaska.LargeSk.register.LargeSkRegister;
import uk.co.umbaska.LargeSk.util.LargeConfig;
import uk.co.umbaska.LargeSk.util.MetricsManager;
import uk.co.umbaska.LargeSk.util.SkAddons;
import uk.co.umbaska.LargeSk.util.Xlog;

public class LargeSk
  extends JavaPlugin
{
  public static boolean debug = false;
  
  public static LargeSk getPluginInstance()
  {
    return (LargeSk)getPlugin(LargeSk.class);
  }
  
  public void onEnable()
  {
    LargeConfig largeconfig = new LargeConfig();
    LargeSkRegister register = new LargeSkRegister();
    
    long eTime = System.currentTimeMillis();
    Xlog.logInfo(ChatColor.YELLOW + "=== ENABLE " + ChatColor.GREEN + "START" + ChatColor.YELLOW + " ===");
    
    largeconfig.load();
    
    Skript.registerAddon(this);
    
    register.registerAll();
    
    Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable()
    {
      public void run() {}
    }, 100L);
    
    Xlog.logInfo("Share your problems and ideas on https://github.com/Nicofisi/LargeSk/issues");
    
    Bukkit.getScheduler().runTaskAsynchronously(this, SkAddons.logAddons());
    Xlog.logInfo("I will show you a list of your Skript addons as soon as everything loads up.");
    if (debug) {}
    eTime = System.currentTimeMillis() - eTime;
    Xlog.logInfo(ChatColor.YELLOW + "=== ENABLE " + ChatColor.GREEN + "COMPLETE" + ChatColor.YELLOW + " (Took " + ChatColor.LIGHT_PURPLE + eTime + "ms" + ChatColor.YELLOW + ") ===");
  }
  
  public void onDisable()
  {
    if (debug) {
      Xlog.logInfo("Cancelling tasks..");
    }
    Bukkit.getScheduler().cancelTasks(this);
    MetricsManager.disableMetrics();
    Xlog.logInfo("Bye, Senpai!");
  }
}
