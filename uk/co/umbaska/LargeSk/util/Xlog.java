package uk.co.umbaska.LargeSk.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import uk.co.umbaska.Umbaska;

public class Xlog
{
  public static void logInfo(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[" + ChatColor.AQUA + Umbaska.get().getDescription().getName() + " " + Umbaska.get().getDescription().getVersion() + ChatColor.BLUE + "] " + ChatColor.RESET + msg);
  }
  
  public static void logWarning(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + Umbaska.get().getDescription().getName() + " " + Umbaska.get().getDescription().getVersion() + ChatColor.LIGHT_PURPLE + " WARNING" + ChatColor.DARK_PURPLE + "] " + ChatColor.RESET + msg);
  }
  
  public static void logError(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + Umbaska.get().getDescription().getName() + " " + Umbaska.get().getDescription().getVersion() + ChatColor.RED + "  ERROR " + ChatColor.DARK_RED + "] " + ChatColor.RESET + msg);
  }
  
  public static void logUpdater(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + Umbaska.get().getDescription().getName() + " " + Umbaska.get().getDescription().getVersion() + ChatColor.GREEN + " UPDATER" + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + msg);
  }
  
  public static void logRaw(String msg)
  {
    Bukkit.getConsoleSender().sendMessage(msg);
  }
  
  public static void logDefault(String msg)
  {
    Bukkit.getConsoleSender().sendMessage("[" + Umbaska.get().getName() + "] " + msg);
  }
}
