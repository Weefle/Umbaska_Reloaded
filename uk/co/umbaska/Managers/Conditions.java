package uk.co.umbaska.Managers;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import uk.co.umbaska.Main;

public class Conditions
{
  public static Boolean use_bungee = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("use_bungee"));
  public static Boolean debugInfo = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("debug_info"));
  
  private static String version = Register.getVersion();
  
  private static void registerNewCondition(String name, String cls, String syntax, Boolean multiversion)
  {
    if (Skript.isAcceptRegistrations()) {
      if (multiversion.booleanValue()) {
        Class newCls = Register.getClass(cls);
        if (newCls == null) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Condition for " + name + " due to Can't find Class!");
          return;
        }
        registerNewCondition(name, newCls, syntax);
      }
      else {
        try {
          registerNewCondition(name, Class.forName(cls), syntax);
        } catch (ClassNotFoundException e) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Condition for " + name + " due to Wrong Spigot/Bukkit Version!");
        }
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Condition for " + name + " due to Skript Not Accepting Registrations");
    }
  }
  
  private static void registerNewCondition(String name, Class cls, String syntax)
  {
    if (Skript.isAcceptRegistrations()) {
      registerNewCondition(cls, syntax);
      if (debugInfo.booleanValue()) {
        Bukkit.getLogger().info("Umbaska »»» Registered Condition for " + name + " with syntax \n" + syntax);
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Condition for " + name + " due to Skript Not Accepting Registrations");
    }
  }
  
  @Deprecated
  private static void registerNewCondition(Class cls, String syntax)
  {
    if (Skript.isAcceptRegistrations()) {
      Skript.registerCondition(cls, new String[] { syntax });
      if (debugInfo.booleanValue()) {
        Bukkit.getLogger().info("Umbaska »»» Registered Condition for " + cls.getName() + " with syntax\n " + syntax);
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Condition for " + cls.getName() + " due to Skript Not Accepting Registrations");
    }
  }
  



  public static void runRegister()
  {
    registerNewCondition("Bar has Flag", uk.co.umbaska.BossBars.CondBarHasFlag.class, "%bossbar% has %barflag%");
    if (Bukkit.getServer().getPluginManager().getPlugin("SkRambled") == null)
    {
      Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("mcMMO");
      if (pl != null) {
        PropertyCondition.register(uk.co.umbaska.mcMMO.CondIsUsingPartyChat.class, "(using party(chat| chat))", "players");
        PropertyCondition.register(uk.co.umbaska.mcMMO.CondIsUsingAdminChat.class, "(using admin(chat| chat))", "players");
      }
    }
  }
}
