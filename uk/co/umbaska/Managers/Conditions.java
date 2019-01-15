package uk.co.umbaska.Managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.CondIsOnGround;
import ch.njol.skript.conditions.base.PropertyCondition;
import uk.co.umbaska.Main;
import uk.co.umbaska.AAC.CondCheckEnabled;
import uk.co.umbaska.AAC.CondIsBypassed;
import uk.co.umbaska.LargeSk.conditions.CondEvaluateCondition;
import uk.co.umbaska.LargeSk.skinsrestorer.CondPlayerHasSkin;
import uk.co.umbaska.Potato.CondIsServerPotato;

public class Conditions
{
  public static Boolean use_bungee = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("use_bungee"));
  public static Boolean debugInfo = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("debug_info"));
  
  private static String version = Register.getVersion();
  
  @SuppressWarnings("unused")
private static void registerNewCondition(String name, String cls, String syntax, Boolean multiversion)
  {
    if (Skript.isAcceptRegistrations()) {
      if (multiversion.booleanValue()) {
        Class<?> newCls = Register.getClass(cls);
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
  
  private static void registerNewCondition(String name, Class<?> cls, String syntax)
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
  
  @SuppressWarnings("unchecked")
@Deprecated
  private static void registerNewCondition(@SuppressWarnings("rawtypes") Class cls, String syntax)
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
	  registerNewCondition("Evaluate condition", CondEvaluateCondition.class, "[evaluate] cond[ition] %string%");
    registerNewCondition("Bar has Flag", uk.co.umbaska.BossBars.CondBarHasFlag.class, "%bossbar% has %barflag%");
    registerNewCondition("Server is a Potato", CondIsServerPotato.class, "[(this|the)] server is [(a|an|the)] [instance of] [[the] only (true|real)] potato");
    if (Bukkit.getServer().getPluginManager().getPlugin("AAC") != null)
    {
    	registerNewCondition("Hack is enabled", CondCheckEnabled.class, "[AAC] (check %-hacktype%|%-hacktype% check) is (enabled|on)%");
    	registerNewCondition("Player is bypassed", CondIsBypassed.class, "[AAC] %player%('s| is) bypass(ed by|ing) AAC");
    	registerNewCondition("Player is on groung", CondIsOnGround.class, "[AAC] %player%('s| is) on ground");
    }
    if (Bukkit.getServer().getPluginManager().getPlugin("SkinsRestorer") != null)
    {
    	registerNewCondition("Player has skin", CondPlayerHasSkin.class, "%offlineplayer% (has|have) [a] skin");
    }
    if (Bukkit.getServer().getPluginManager().getPlugin("SkRambled") == null)
    {
      Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("mcMMO");
      if (pl != null) {
        PropertyCondition.register(uk.co.umbaska.mcMMO.CondIsUsingPartyChat.class, "(using party(chat| chat))", "players");
        PropertyCondition.register(uk.co.umbaska.mcMMO.CondIsUsingAdminChat.class, "(using admin(chat| chat))", "players");
      }
    }
  }

public static String getVersion() {
	return version;
}

public static void setVersion(String version) {
	Conditions.version = version;
}
}
