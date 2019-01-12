package uk.co.umbaska.LargeSk.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import uk.co.umbaska.Main;

public class Xlog
{
  public static void logInfo(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + Main.getInstance().getDescription().getName() + " " + Main.getInstance().getDescription().getVersion() + ChatColor.BLUE + "] " + ChatColor.RESET + msg);
  }
  
  public static void logWarning(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + Main.getInstance().getDescription().getName() + " " + Main.getInstance().getDescription().getVersion() + ChatColor.LIGHT_PURPLE + " WARNING" + ChatColor.DARK_PURPLE + "] " + ChatColor.RESET + msg);
  }
  
  public static void logError(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + Main.getInstance().getDescription().getName() + " " + Main.getInstance().getDescription().getVersion() + ChatColor.RED + "  ERROR " + ChatColor.DARK_RED + "] " + ChatColor.RESET + msg);
  }
  
  public static void logUpdater(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + Main.getInstance().getDescription().getName() + " " + Main.getInstance().getDescription().getVersion() + ChatColor.GREEN + " UPDATER" + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + msg);
  }
  
  public static void logRaw(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(msg);
  }
  
  public static void logDefault(String msg)
  {
    Bukkit.getConsoleSender().sendMessage("[" + Main.getInstance().getName() + "] " + msg);
  }
}
