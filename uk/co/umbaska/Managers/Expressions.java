package uk.co.umbaska.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Objective;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.palmergames.bukkit.towny.object.Town;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import uk.co.umbaska.Main;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudColor;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudDuration;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudDurationOnUse;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudParticle;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudRadius;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudRadiusOnUse;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudRadiusPerTick;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudReapplicationDelay;
import uk.co.umbaska.AreaEffectCloud.ExprEffectCloudWaitTime;
import uk.co.umbaska.AreaEffectCloud.ExprNewBukkitColor;
import uk.co.umbaska.AreaEffectCloud.ExprNewPotionEffect;
import uk.co.umbaska.ArmourStands.ExprNoAI;
import uk.co.umbaska.ArmourStands.ExprsHeadDirectionX;
import uk.co.umbaska.ArmourStands.ExprsHeadDirectionY;
import uk.co.umbaska.ArmourStands.ExprsHeadDirectionZ;
import uk.co.umbaska.ArmourStands.ExprsVisible;
import uk.co.umbaska.ArmourStands.Legs.ExprsRightLegDirectionX;
import uk.co.umbaska.ArmourStands.Legs.ExprsRightLegDirectionY;
import uk.co.umbaska.ArmourStands.Legs.ExprsRightLegDirectionZ;
import uk.co.umbaska.BossBars.ExprBossBarColor;
import uk.co.umbaska.BossBars.ExprGetBarFromSerialised;
import uk.co.umbaska.BossBars.ExprNewBossBar;
import uk.co.umbaska.BossBars.ExprSerialisedBossBar;
import uk.co.umbaska.Bungee.ExprAllServers;
import uk.co.umbaska.Bungee.ExprBungeeServerCount;
import uk.co.umbaska.Bungee.ExprBungeeUUID;
import uk.co.umbaska.Enums.ParticleEnum;
import uk.co.umbaska.Factions.ExprAlliesOfFaction;
import uk.co.umbaska.Factions.ExprFactionOfPlayer;
import uk.co.umbaska.Factions.ExprFactions;
import uk.co.umbaska.Factions.ExprRelationshipStatus;
import uk.co.umbaska.GattSk.Expressions.ExprClickType;
import uk.co.umbaska.GattSk.Expressions.ExprClickedItem;
import uk.co.umbaska.JSON.ExprJsonMessageStyle;
import uk.co.umbaska.JSON.JSONMessage;
import uk.co.umbaska.MathsExpressions.ExprAtan;
import uk.co.umbaska.MathsExpressions.ExprFactorial;
import uk.co.umbaska.MathsExpressions.ExprHyperbolicCos;
import uk.co.umbaska.MathsExpressions.ExprSignum;
import uk.co.umbaska.Misc.Banners.ExprNewBannerFrom;
import uk.co.umbaska.Misc.Banners.ExprNewLayer;
import uk.co.umbaska.Misc.Looping.ExprLoopSpecificBlocksCyl;
import uk.co.umbaska.Misc.NotVersionAffected.ExprBlitzkrieg;
import uk.co.umbaska.Misc.NotVersionAffected.ExprDirectionLocation;
import uk.co.umbaska.Misc.NotVersionAffected.ExprMainhandItem;
import uk.co.umbaska.Misc.NotVersionAffected.ExprMainhandItemPlayer;
import uk.co.umbaska.Misc.NotVersionAffected.ExprOffhandItem;
import uk.co.umbaska.Misc.NotVersionAffected.ExprOffhandItemPlayer;
import uk.co.umbaska.Misc.UM2_0.ExprBlockSkullOwner;
import uk.co.umbaska.Misc.UM2_0.ExprClosestEntity;
import uk.co.umbaska.NametagEdit.ExprGetPrefix;
import uk.co.umbaska.PlaceHolderAPI.EffParse;
import uk.co.umbaska.ProtocolLib.FakePlayer.ExprGetPlayer;
import uk.co.umbaska.System.ExprGetFile;
import uk.co.umbaska.System.ExprGetLine;
import uk.co.umbaska.System.ExprPing;
import uk.co.umbaska.Towny.ExprRDChatName;
import uk.co.umbaska.Towny.ExprRDLastOnlineDate;
import uk.co.umbaska.Towny.ExprTDPlayers;
import uk.co.umbaska.UUID.ExprNamesOfPlayer;

public class Expressions
{
  public static Boolean use_bungee = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("use_bungee"));
  public static Boolean debugInfo = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("debug_info"));
  public static String registeredPlotSystem = null;
  
  @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
private static void registerNewSimpleExpression(String name, String cls, Class returnType, String syntax1, String syntax2, Boolean multiversion) {
    Class newCls = Register.getClass(cls);
    if (Skript.isAcceptRegistrations()) {
      if (multiversion.booleanValue())
      {
        if (newCls == null) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Can't find Class!");
          return;
        }
        if (debugInfo.booleanValue()) {
          Bukkit.getLogger().info("Umbaska »»» Registered Expression for " + name + " with syntax\n set " + syntax1 + " of " + syntax2 + " for Version " + Register.getVersion());
        }
        SimplePropertyExpression.register(newCls, returnType, syntax1, syntax2);
      }
      else {
        Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Wrong Spigot/Bukkit Version!");
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Skript Not Accepting Registrations");
    }
    List<String> expressions = Register.simpleexpressionList.get(name);
    if (expressions == null) {
      expressions = new ArrayList<>();
    }
    expressions.add(syntax1 + " " + syntax2);
    expressions.add("Example*: set " + syntax1 + " of %" + syntax2 + "% to %" + returnType.getSimpleName() + "%");
    Register.simpleexpressionList.put(name, expressions);
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
private static void registerNewSimpleExpression(String name, Class cls, Class returnType, String syntax1, String syntax2, Boolean multiversion) {
    if (Skript.isAcceptRegistrations()) {
      if (debugInfo.booleanValue()) {
        Bukkit.getLogger().info("Umbaska »»» Registered Expression for " + name + " with syntax\n set " + syntax1 + " of " + syntax2 + " for Version " + Register.getVersion());
      }
      SimplePropertyExpression.register(cls, returnType, syntax1, syntax2);
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Skript Not Accepting Registrations");
    }
    List<String> expressions = Register.simpleexpressionList.get(name);
    if (expressions == null) {
      expressions = new ArrayList<>();
    }
    expressions.add(syntax1 + " " + syntax2);
    expressions.add("Example*: set " + syntax1 + " of %" + syntax2 + "% to %" + returnType.getSimpleName() + "%");
    Register.simpleexpressionList.put(name, expressions);
  }
  
  @SuppressWarnings({ "rawtypes", "unused" })
private static void registerNewExpression(String name, String cls, Class returnType, ExpressionType expressionType, String syntax, Boolean multiversion) {
    if (Skript.isAcceptRegistrations()) {
      if (multiversion.booleanValue()) {
        Class newCls = Register.getClass(cls);
        if (newCls == null) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Can't find Class!");
          return;
        }
        if (debugInfo.booleanValue()) {
          Bukkit.getLogger().info("Umbaska »»» Registered Expression for " + name + " with syntax\n " + syntax + " for Version " + Register.getVersion());
        }
        registerNewExpression(name, newCls, returnType, expressionType, new String[] { syntax });
      }
      else {
        try {
          registerNewExpression(name, Class.forName(cls), returnType, expressionType, new String[] { syntax });
        } catch (ClassNotFoundException e) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Wrong Spigot/Bukkit Version!");
        }
      }
    }
    else
      Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Skript Not Accepting Registrations");
  }
  
  @SuppressWarnings("rawtypes")
private static void registerNewExpression(String name, Class cls, Class returnType, ExpressionType expressionType, String... syntaxes) {
    if (Skript.isAcceptRegistrations()) {
      registerNewExpression(cls, returnType, expressionType, syntaxes);
      if (debugInfo.booleanValue()) {
        for (String syntax : syntaxes) {
          Bukkit.getLogger().info("Umbaska »»» Registered Expression for " + name + " with syntax \n" + syntax);
        }
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + name + " due to Skript Not Accepting Registrations");
    }
    List<String> expressions = Register.expressionList.get(name);
    if (expressions == null) {
      expressions = new ArrayList<>();
    }
    for (String s : syntaxes) {
      expressions.add(s);
    }
    Register.expressionList.put(name, expressions);
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
private static void registerNewExpression(Class cls, Class returnType, ExpressionType expressionType, String... syntaxes) { if (Skript.isAcceptRegistrations()) {
      Skript.registerExpression(cls, returnType, expressionType, syntaxes);
      if (debugInfo.booleanValue()) {
        for (String syntax : syntaxes) {
          Bukkit.getLogger().info("Umbaska »»» Registered Expression for " + cls.getName() + " with syntax\n " + syntax);
        }
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Expression for " + cls.getName() + " due to Skript Not Accepting Registrations");
    }
  }
  
  public static void runRegister() {
	 // Boolean registeredPlotSquared = Boolean.valueOf(false);
	    Plugin /*pl = Bukkit.getServer().getPluginManager().getPlugin("PlotSquared");
	    registeredPlotSystem = "No plot system hooked";
	    if (pl != null)
	    {
	      registeredPlotSquared = Boolean.valueOf(true);
	      registerNewExpression("PlotSquared - Plot at Player", uk.co.umbaska.PlotSquared.ExprPlotAtPlayer.class, String.class, ExpressionType.PROPERTY, new String[] { "plot at %player%" });
	      registerNewExpression("PlotSquared - Plot at Location", uk.co.umbaska.PlotSquared.ExprPlotAtLoc.class, String.class, ExpressionType.PROPERTY, new String[] { "plot at location %location%" });
	      registerNewExpression("PlotSquared - Owner of Plot", uk.co.umbaska.PlotSquared.ExprGetOwner.class, String.class, ExpressionType.PROPERTY, new String[] { "[get ]owner of %string%" });
	      registerNewExpression("PlotSquared - Plots of Player", uk.co.umbaska.PlotSquared.ExprGetPlayerPlots.class, String.class, ExpressionType.PROPERTY, new String[] { "plots of %player%" });
	      registerNewExpression("PlotSquared - Top Corner of Plot", uk.co.umbaska.PlotSquared.ExprTopCorner.class, Location.class, ExpressionType.PROPERTY, new String[] { "(top|upper) corner of %string% in %world%" });
	      registerNewExpression("PlotSquared - Bottom Corner of Plot", uk.co.umbaska.PlotSquared.ExprBottomCorner.class, Location.class, ExpressionType.PROPERTY, new String[] { "(bottom|lower) corner of %string% in %world%" });
	      registeredPlotSystem = "PlotSquared";
	    }
	    if (!registeredPlotSquared.booleanValue())
	    {*/
	      pl = Bukkit.getServer().getPluginManager().getPlugin("PlotMe");
	      if (pl != null)
	      {
        registerNewExpression("PlotMe - Plot at Player", uk.co.umbaska.PlotMe.ExprPlotAtPlayer.class, String.class, ExpressionType.PROPERTY, new String[] { "plot at %player%" });
        registerNewExpression("PlotMe - Plot at Location", uk.co.umbaska.PlotMe.ExprPlotAtLoc.class, String.class, ExpressionType.PROPERTY, new String[] { "plot at location %location%" });
        registerNewExpression("PlotMe - Owner of Plot", uk.co.umbaska.PlotMe.ExprGetOwner.class, String.class, ExpressionType.PROPERTY, new String[] { "[get ]owner of %string%" });
        registerNewExpression("PlotMe - Plots of Player", uk.co.umbaska.PlotMe.ExprGetPlayerPlots.class, String.class, ExpressionType.PROPERTY, new String[] { "plots list" });
        registerNewExpression("PlotMe - Top Corner of Plot", uk.co.umbaska.PlotMe.ExprTopCorner.class, Location.class, ExpressionType.PROPERTY, new String[] { "(top|upper) corner of %string% in %world%" });
        registerNewExpression("PlotMe - Bottom Corner of Plot", uk.co.umbaska.PlotMe.ExprBottomCorner.class, Location.class, ExpressionType.PROPERTY, new String[] { "(bottom|lower) corner of %string% in %world%" });
        registeredPlotSystem = "PlotMe";
      }
    
    
    if (pl != null) {
      registeredPlotSystem = pl.getName() + " - " + pl.getDescription().getVersion();
    }
    


    pl = Bukkit.getServer().getPluginManager().getPlugin("Vault");
    if (pl != null) {
      registerNewExpression(uk.co.umbaska.Vault.ExprGroupOfPlayer.class, String.class, ExpressionType.PROPERTY, new String[] { "primary group of %player%" });
    }
    

    registerNewExpression("World Border Expressions", uk.co.umbaska.WorldBorder.ExprWorldBorder.class, Object.class, ExpressionType.PROPERTY, new String[] { "worldborder size of %world%", "worldborder [damage] amount of %world%", "worldborder [damage] buffer of %world%", "worldborder [warning] distance of %world%", "worldborder [warning] time of %world%" });
    






    registerNewExpression("Network", uk.co.umbaska.Misc.ExprNetworking.class, Object.class, ExpressionType.PROPERTY, new String[] { "ip [of server]", "port [of server]", "connection throttle [of server]", "online mode [of server]", "version [of server]", "motd [of server]", "name [of server]", "idle timeout [of server]", "[server] icon [of server]", "max players [of server]", "host of %player%", "port of %player%", "full host of %player%", "host[ ]name of %player%", "address of %player%", "connection address of %player%", "full ip [of server]" });
    



















    pl = Bukkit.getServer().getPluginManager().getPlugin("Towny");
    if (pl != null) {
      registerNewExpression("Towny - Town at Player", uk.co.umbaska.Towny.ExprTownAtPlayer.class, String.class, ExpressionType.PROPERTY, new String[] { "town at %player%" });
      registerNewExpression("Towny - Town of Player", uk.co.umbaska.Towny.ExprTownOfPlayer.class, Town.class, ExpressionType.PROPERTY, new String[] { "town of %player%" });
      registerNewExpression("Towny - Town Balance", uk.co.umbaska.Towny.ExprTDBank.class, Double.class, ExpressionType.PROPERTY, new String[] { "town balance of %string%" });
      registerNewExpression("Towny - Town Player Count", uk.co.umbaska.Towny.ExprTDPlayerCount.class, Integer.class, ExpressionType.PROPERTY, new String[] { "player[ ]count of %string%" });
      registerNewExpression("Towny - Town Players", ExprTDPlayers.class, String.class, ExpressionType.PROPERTY, new String[] { "players of %string%" });
      registerNewExpression("Towny - Town Taxes", uk.co.umbaska.Towny.ExprTDTaxes.class, Double.class, ExpressionType.PROPERTY, new String[] { "town taxes of %string%" });
      registerNewExpression("Towny - Plot Owner", uk.co.umbaska.Towny.ExprPlotOwner.class, String.class, ExpressionType.PROPERTY, new String[] { "owner of plot at %location%" });
      registerNewExpression("Towny - Plot Price", uk.co.umbaska.Towny.ExprPlotPrice.class, Double.class, ExpressionType.PROPERTY, new String[] { "price of plot at %location%" });
      registerNewExpression("Towny - RD Last Online", uk.co.umbaska.Towny.ExprRDLastOnline.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data last online of %player%" });
      registerNewExpression("Towny - RD Last Online Date", ExprRDLastOnlineDate.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data last online date of %player%" });
      registerNewExpression("Towny - RD Chat Name", ExprRDChatName.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data chat name of %player%" });
      registerNewExpression("Towny - RD Friends", uk.co.umbaska.Towny.ExprRDFriends.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data friends of %player%" });
      registerNewExpression("Towny - RD Nation Ranks--", uk.co.umbaska.Towny.ExprRDNationRanks.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data nation ranks of %player%" });
      registerNewExpression("Towny - Resident Data Registered", uk.co.umbaska.Towny.ExprRDRegistered.class, Long.class, ExpressionType.PROPERTY, new String[] { "resident data registered of %player%" });
      registerNewExpression("Towny - Resident Data Surname", uk.co.umbaska.Towny.ExprRDSurname.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data surname of %player%" });
      registerNewExpression("Towny - Resident Data Title", uk.co.umbaska.Towny.ExprRDTitle.class, String.class, ExpressionType.PROPERTY, new String[] { "resident data title of %player%" });
    }
    

    registerNewExpression("Names of Player", ExprNamesOfPlayer.class, String.class, ExpressionType.COMBINED, new String[] { "names of %string%" });
    if (!Main.getInstance().getConfig().isSet("entity_uuid_fix")) {
      Main.getInstance().getConfig().set("entity_uuid_fix", Boolean.valueOf(false));
    }
    registerNewExpression("Entity UUID", uk.co.umbaska.UUID.ExprUUIDOfEntity.class, String.class, ExpressionType.SIMPLE, new String[] { "entity uuid of %entity%" });
    





















    pl = Bukkit.getServer().getPluginManager().getPlugin("UmbaskaAPI");
    if (pl != null) {
      registerNewExpression("Factions - Faction of Player ", ExprFactionOfPlayer.class, String.class, ExpressionType.PROPERTY, new String[] { "faction of %player%" });
    }
    

    registerNewExpression("Spawner - Delay Time", uk.co.umbaska.Spawner.ExprDelayTime.class, Integer.class, ExpressionType.PROPERTY, new String[] { "delay time of %location%" });
    registerNewExpression("Spawner - Entity Type", uk.co.umbaska.Spawner.ExprSpawnedType.class, String.class, ExpressionType.PROPERTY, new String[] { "entity type of %location%" });
    
    registerNewExpression("Item Name", uk.co.umbaska.Spawner.ExprItemName.class, String.class, ExpressionType.SIMPLE, new String[] { "item name" });
    registerNewExpression("Centred Text", uk.co.umbaska.Misc.NotVersionAffected.EffCentredText.class, String.class, ExpressionType.SIMPLE, new String[] { "cent(er|re)d %string%" });
    registerNewExpression("Centred Text", uk.co.umbaska.Misc.NotVersionAffected.EffCentredTextSize.class, String.class, ExpressionType.SIMPLE, new String[] { "cent(er|re)d %string% [with] [max] [length] [of] %-integer%" });
    registerNewExpression("Armor Points", uk.co.umbaska.Misc.NotVersionAffected.ExprArmourPoints.class, Double.class, ExpressionType.PROPERTY, new String[] { "(armour|armor) points of %player%" });
    registerNewExpression("Amount of Items", uk.co.umbaska.Misc.NotVersionAffected.ExprItemCountInSlot.class, Integer.class, ExpressionType.SIMPLE, new String[] { "amount of items in %itemstack%" });
    registerNewExpression("Get JSON String", uk.co.umbaska.Misc.NotVersionAffected.ExprGetJSONString.class, String.class, ExpressionType.SIMPLE, new String[] { "JSON string %string% from %string%" });
    registerNewExpression("Get Digits from String", uk.co.umbaska.Misc.NotVersionAffected.ExprGetDigits.class, String.class, ExpressionType.SIMPLE, new String[] { "get (digits|numbers|nums|num) of %string%" });
    
    registerNewExpression("New Location", uk.co.umbaska.Misc.NotVersionAffected.ExprNewLocation.class, Location.class, ExpressionType.SIMPLE, new String[] { "new location %number%, %number%, %number% in world %string%" });
    registerNewExpression("File Exists", uk.co.umbaska.System.ExprFileExists.class, Boolean.class, ExpressionType.PROPERTY, new String[] { "exist(e|a)nce of %string%" });
    registerNewExpression("Get File", ExprGetFile.class, String.class, ExpressionType.PROPERTY, new String[] { "file from %string%" });
    registerNewExpression("Conents of file", uk.co.umbaska.System.ExprContent.class, String.class, ExpressionType.SIMPLE, new String[] { "content[s] (from|of) file %string%" });
    registerNewExpression("Get line in file", ExprGetLine.class, String.class, ExpressionType.SIMPLE, new String[] { "line %integer% in file %string%" });
    registerNewExpression("Files in Folder", uk.co.umbaska.System.ExprFileInDir.class, String.class, ExpressionType.SIMPLE, new String[] { "files in %string% (recursive|r) %boolean%" });
    registerNewExpression("Enchants of Item", uk.co.umbaska.Misc.ExprEnchantsOfItem.class, String.class, ExpressionType.PROPERTY, new String[] { "enchants of %itemstack%" });
    pl = Bukkit.getServer().getPluginManager().getPlugin("NametagEdit");
    if (pl != null) {
      registerNewExpression("NametagEdit - Prefix of Player", ExprGetPrefix.class, String.class, ExpressionType.PROPERTY, new String[] { "prefix of %player%" });
      registerNewExpression("NametagEdit - Suffix of Player", uk.co.umbaska.NametagEdit.ExprGetSuffix.class, String.class, ExpressionType.PROPERTY, new String[] { "suffix of %player%" });
      registerNewExpression("NametagEdit - Name Tag of Player", uk.co.umbaska.NametagEdit.ExprGetNametag.class, String.class, ExpressionType.PROPERTY, new String[] { "name tag of %player%" });
    }
    
    registerNewExpression("All Blocks", uk.co.umbaska.Misc.Looping.ExprLoopAllBlocks.class, Block.class, ExpressionType.SIMPLE, new String[] { "[all] blocks (from|within) %location% to %location%" });
    registerNewExpression("Solid Blocks", uk.co.umbaska.Misc.Looping.ExprLoopSolidBlocks.class, Block.class, ExpressionType.SIMPLE, new String[] { "[all] solid blocks (from|within) %location% to %location%" });
    registerNewExpression("Transparent Blocks", uk.co.umbaska.Misc.Looping.ExprLoopTransparentBlocks.class, Block.class, ExpressionType.SIMPLE, new String[] { "[all] (transparent|trans|t|non-solid|see through|other) blocks (from|within) %location% to %location%" });
    registerNewExpression("Blocks in Cylinder", uk.co.umbaska.Misc.NotVersionAffected.ExprBlocksInCylinder.class, Block.class, ExpressionType.COMBINED, new String[] { "blocks in cylindrical radius of %number%( [with] height|,) %number% (around|from[ block[ at]]) %location%" });
    
    registerNewExpression("Specific Blocks - Cylinder", ExprLoopSpecificBlocksCyl.class, Block.class, ExpressionType.COMBINED, new String[] { "[all] [blocks of type] %materials% in cylindrical radius of %number%( [with] height|,) %number% (around|from[ block[ at]]) %location%" });
    registerNewExpression("Specific Blocks - 2 Points", uk.co.umbaska.Misc.Looping.ExprLoopSpecificBlocks2Point.class, Block.class, ExpressionType.COMBINED, new String[] { "[all] [blocks of type] %materials% (from|within) %location% to %location%" });
    registerNewExpression("Specific Blocks - Sphere", uk.co.umbaska.Misc.Looping.ExprLoopSpecificBlocksSphere.class, Block.class, ExpressionType.COMBINED, new String[] { "[all] [blocks of type] %materials% (from|around) %location% [with] [radius] %number%" });
    

    registerNewExpression("Command Block Data", uk.co.umbaska.Misc.ExprCommandBlockInfo.class, String.class, ExpressionType.SIMPLE, new String[] { "command of %block%", "name of %block%" });
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
    if (pl != null) {
      registerNewExpression("PlaceholderAPI - Parse", EffParse.class, String.class, ExpressionType.PROPERTY, new String[] { "placeholder parse %string% as %player%", "placeholder parse %string%" });
    }
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
    if (pl != null) {
      registerNewExpression("Dynmap - Visibility of Player", uk.co.umbaska.Dynmap.ExprVisOfPlayer.class, Boolean.class, ExpressionType.PROPERTY, new String[] { "Dynmap visibility of %player%" });
    }
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("MassiveCore");
    if (pl != null) {
      pl = Bukkit.getServer().getPluginManager().getPlugin("Factions");
      if (pl != null) {
        registerNewSimpleExpression("Factions - Name of Faction", uk.co.umbaska.Factions.ExprNameOfFaction.class, String.class, "name", "faction", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Faction of Player", ExprFactionOfPlayer.class, Faction.class, "faction", "player", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Description of Faction", uk.co.umbaska.Factions.ExprDescriptionOfFaction.class, String.class, "description", "faction", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Power of Player", uk.co.umbaska.Factions.ExprPowerOfPlayer.class, Double.class, "power", "player", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Powerboost of Player", uk.co.umbaska.Factions.ExprPowerboostOfPlayer.class, Double.class, "powerboost", "player", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - MOTD of Faction", uk.co.umbaska.Factions.ExprMOTDOfFaction.class, String.class, "motd", "faction", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Home of Faction", uk.co.umbaska.Factions.ExprHomeOfFaction.class, Location.class, "home", "faction", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Title of Player", uk.co.umbaska.Factions.ExprTitleOfPlayer.class, String.class, "title", "player", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Power of Faction", uk.co.umbaska.Factions.ExprPowerOfFaction.class, Double.class, "power", "faction", Boolean.valueOf(false));
        registerNewSimpleExpression("Factions - Powerboost of Faction", uk.co.umbaska.Factions.ExprPowerboostOfFaction.class, Double.class, "powerboost", "faction", Boolean.valueOf(false));
        registerNewExpression("Factions - Faction at Location", uk.co.umbaska.Factions.ExprFactionAtLocation.class, Faction.class, ExpressionType.SIMPLE, new String[] { "[the] faction at %location%" });
        registerNewExpression("Factions - Factions", ExprFactions.class, String.class, ExpressionType.SIMPLE, new String[] { "list of [all] Factions", "Factions list", "all Factions" });
        registerNewExpression("Factions - Allies of Faction", ExprAlliesOfFaction.class, String.class, ExpressionType.SIMPLE, new String[] { "list of [all] allies of [the] [faction] %faction%", "[all] faction allies [list] of [the] [faction] %faction%" });
        registerNewExpression("Factions - Players of Faction", uk.co.umbaska.Factions.ExprPlayersOfFaction.class, Player.class, ExpressionType.SIMPLE, new String[] { "list of [all] players of [the faction] %faction%", "[all] players['] [list] of [the faction] %faction%" });
        registerNewExpression("Factions - Relationship Status", ExprRelationshipStatus.class, Rel.class, ExpressionType.SIMPLE, new String[] { "relation[ship] [status] between [the] [faction] %faction% (and|with) [the] [faction] %faction%" });
        registerNewExpression("Factions - Rank of Player", uk.co.umbaska.Factions.ExprRankOfPlayer.class, Rel.class, ExpressionType.SIMPLE, new String[] { "role of [the] [player] %player%" });
        registerNewExpression("Factions - Enemies of Faction", uk.co.umbaska.Factions.ExprEnemiesOfFaction.class, String.class, ExpressionType.SIMPLE, new String[] { "list of [all] enemies of [the] [faction] %faction%", "[all] faction enemies [list] of %faction%" });
        registerNewExpression("Factions - Truces of Faction", uk.co.umbaska.Factions.ExprTrucesOfFaction.class, String.class, ExpressionType.SIMPLE, new String[] { "list of [all] truces of [the] [faction] %faction%", "[all] faction truces [list] of %faction%" });
      }
    }
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("mcMMO");
    if (pl != null) {
      registerNewSimpleExpression("mcMMO - Power Level of Player", uk.co.umbaska.mcMMO.ExprPowerLevelOfPlayer.class, Integer.class, "power(level| level)", "player", Boolean.valueOf(false));
    }
    

    registerNewExpression("Free Memory", uk.co.umbaska.System.ExprFreeMemory.class, Integer.class, ExpressionType.PROPERTY, new String[] { "free memory" });
    registerNewExpression("Java Version", uk.co.umbaska.System.ExprJavaVersion.class, String.class, ExpressionType.PROPERTY, new String[] { "java version" });
    registerNewExpression("Max Memory", uk.co.umbaska.System.ExprMaxMemory.class, Integer.class, ExpressionType.PROPERTY, new String[] { "max memory" });
    registerNewExpression("Total Memory", uk.co.umbaska.System.ExprTotalMemory.class, Integer.class, ExpressionType.PROPERTY, new String[] { "total memory" });
    registerNewExpression("TPS", uk.co.umbaska.System.ExprTPS.class, Double.class, ExpressionType.PROPERTY, new String[] { "tps" });
    registerNewExpression("Ping", ExprPing.class, Integer.class, ExpressionType.PROPERTY, new String[] { "%player%[[']s] ping" });
    registerNewExpression("Ping", ExprPing.class, Integer.class, ExpressionType.PROPERTY, new String[] { "ping of %player%" });
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
    if (pl != null) {
      registerNewExpression("ProtocolLib - Can See", uk.co.umbaska.ProtocolLib.ExprCanSee.class, Boolean.class, ExpressionType.PROPERTY, new String[] { "visibility of %entities% for %player%" });
    }
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    if (pl != null) {
      registerNewExpression("All Schematics", uk.co.umbaska.WorldEdit.ExprAllSchematics.class, String.class, ExpressionType.COMBINED, new String[] { "all schematics" });
    }
    

    registerNewExpression("Armor Dyed Color", uk.co.umbaska.Misc.NotVersionAffected.ExprDyed.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "%itemstack% (colo[u]red|dyed) %color%" });
    registerNewExpression("Armor Dyed RGB", uk.co.umbaska.Misc.NotVersionAffected.ExprDyedRGB.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "%itemstack% (colo[u]red|dyed) %number%, %number%(,| and) %number%" });
    registerNewExpression("Clicked Item", ExprClickedItem.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "clicked item" });
    registerNewExpression("Clicked Inventory", uk.co.umbaska.GattSk.Expressions.ExprClickedInventory.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "clicked inventory" });
    registerNewExpression("Clicked Cursor Item", uk.co.umbaska.GattSk.Expressions.ExprCursorItem.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "cursor item" });
    registerNewExpression("Clicked Slot", uk.co.umbaska.GattSk.Expressions.ExprClickedSlot.class, Integer.class, ExpressionType.SIMPLE, new String[] { "clicked slot" });
    registerNewExpression("Clicked Type", ExprClickType.class, String.class, ExpressionType.SIMPLE, new String[] { "click type" });
    registerNewExpression("Clicked Item Name", uk.co.umbaska.GattSk.Expressions.ExprClickedItemName.class, String.class, ExpressionType.SIMPLE, new String[] { "clicked item name" });
    registerNewExpression("Clicked Item Lore", uk.co.umbaska.GattSk.Expressions.ExprClickedItemLore.class, String.class, ExpressionType.SIMPLE, new String[] { "clicked item lore" });
    
    registerNewExpression("Max Players", uk.co.umbaska.GattSk.Expressions.ExprMaxPlayers.class, Integer.class, ExpressionType.SIMPLE, new String[] { "max players" });
    
    registerNewExpression("Spawn Reason of Entity from Event", uk.co.umbaska.GattSk.Expressions.ExprSpawnReason.class, String.class, ExpressionType.SIMPLE, new String[] { "spawn reason" });
    registerNewExpression("Spawn Reason of Entity", uk.co.umbaska.GattSk.Expressions.ExprSpawnReasonOfEntity.class, String.class, ExpressionType.SIMPLE, new String[] { "spawn reason (of|for) %entity%" });
    registerNewExpression("Scoreboard - Get Score", uk.co.umbaska.GattSk.Expressions.ExprGetScore.class, Integer.class, ExpressionType.PROPERTY, new String[] { "value [of] %string% objective %string% for [score] %string%" });
    
    registerNewExpression("Scoreboard - Get Objective Type", uk.co.umbaska.GattSk.Expressions.ExprGetObjectiveType.class, String.class, ExpressionType.PROPERTY, new String[] { "objective type of %string% (from|in) [score][board] %scoreboard%" });
    registerNewExpression("Scoreboard - Get Objective from Display Slot", uk.co.umbaska.GattSk.Expressions.ExprGetObjectiveDisplay.class, Objective.class, ExpressionType.PROPERTY, new String[] { "objective in [[display]slot] %displayslot% from [score][board] %string%" });
    registerNewExpression("Scoreboard - Get Objective", uk.co.umbaska.GattSk.Expressions.ExprGetObjective.class, String.class, ExpressionType.PROPERTY, new String[] { "objective %string% from [score][board] %string%" });
    

    registerNewExpression("Location based on Direction", ExprDirectionLocation.class, Location.class, ExpressionType.COMBINED, new String[] { "[the] (location|position) %number% (block|meter)[s] in [the] direction %direction% of %location%" });
    registerNewExpression("Location based on Direction", ExprDirectionLocation.class, Location.class, ExpressionType.COMBINED, new String[] { "(location|position) [of] direction %direction% (*|times|multiplied by length) %number% (from|with) [origin] %location%" });
    Main.getInstance().getLogger().info("When Gatt and BaeFell work together, amazing things happen! \nGO! SUPER GATTFELL REGISTER SEQUENCE!\nAchievement Get! Used the new Umbaska Version");
    registerNewSimpleExpression("Freeze", uk.co.umbaska.Misc.NotVersionAffected.ExprFreeze.class, Boolean.class, "freeze state", "player", Boolean.valueOf(false));
    registerNewSimpleExpression("Can Collide", uk.co.umbaska.Misc.NotVersionAffected.ExprCanMoveEntities.class, Boolean.class, "[can] collide [with entities]", "player", Boolean.valueOf(false));
    registerNewExpression("Entity from Variable", uk.co.umbaska.Misc.NotVersionAffected.ExprEntityFromVariable.class, Entity.class, ExpressionType.COMBINED, new String[] { "[umbaska] entity from [variable] %entity%" });
    registerNewExpression("Entity UUID", uk.co.umbaska.UUID.ExprEntityFromUUID.class, Entity.class, ExpressionType.COMBINED, new String[] { "[umbaska] entity from uuid %string%" });
    








    registerNewExpression("Fall Distance", uk.co.umbaska.Misc.NotVersionAffected.ExprFallDistance.class, Number.class, ExpressionType.SIMPLE, new String[] { "fall distance of %entity%" });
    registerNewSimpleExpression("Is Flying State", uk.co.umbaska.Misc.NotVersionAffected.ExprForceFly.class, Boolean.class, "(is flying|force fly)", "player", Boolean.valueOf(false));
    registerNewExpression("Unbreakable Itemstack", uk.co.umbaska.Misc.NotVersionAffected.ExprUnbreakable.class, ItemStack.class, ExpressionType.PROPERTY, new String[] { "[a[n]] unbreakable %itemstacks%" });
    

    registerNewExpression("Entity Within Two Points", uk.co.umbaska.Misc.UM2_0.ExprEntitiesWithin.class, Entity.class, ExpressionType.COMBINED, new String[] { "[all] entities (from|within|in) %location% (and|to) %location%" });
    registerNewExpression("Offset Location", uk.co.umbaska.Misc.UM2_0.ExprOffsetLocation.class, Location.class, ExpressionType.SIMPLE, new String[] { "%location% offset by %number%, %number%(,| and) %number%" });
    registerNewSimpleExpression("World of Location", uk.co.umbaska.Misc.NotVersionAffected.ExprWorldOfLocation.class, World.class, "[umbaska] world", "location", Boolean.valueOf(false));
    registerNewSimpleExpression("Skull Block Set Owner", ExprBlockSkullOwner.class, String.class, "[block ]skull owner", "block", Boolean.valueOf(false));
    registerNewSimpleExpression("Skull Item Set Owner", uk.co.umbaska.Misc.UM2_0.ExprItemStackSkullOwner.class, String.class, "[itemstack ]skull owner", "itemstack", Boolean.valueOf(false));
    registerNewSimpleExpression("Skull Item Set URL", uk.co.umbaska.Misc.UM2_0.ExprItemStackSkullOwnURL.class, String.class, "skull url", "itemstack", Boolean.valueOf(false));
    
    registerNewExpression("Skull from URL", uk.co.umbaska.Misc.UM2_0.ExprSkullOwnerURL.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "%itemstack% [with] [skull] url %string%" });
    


    registerNewExpression("Result Slot", uk.co.umbaska.Misc.UM2_0.ExprResultSlot.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "result slot of %player%" });
    
    registerNewExpression("Splash Potion Entity", uk.co.umbaska.Misc.NotVersionAffected.ExprSplashPotionEntity.class, Entity.class, ExpressionType.SIMPLE, new String[] { "(thrown|splash) potion (from|using) [item] %itemstack%" });
    registerNewExpression("Blank Enderpearl*", uk.co.umbaska.Misc.NotVersionAffected.ExprBlankEnderpearl.class, Entity.class, ExpressionType.SIMPLE, new String[] { "blank %entity%" });
    
    registerNewExpression("Closest Entity to Entity", ExprClosestEntity.class, Entity.class, ExpressionType.SIMPLE, new String[] { "closest entity from [entity] %entity%" });
    


    registerNewExpression("Arc Tan", uk.co.umbaska.MathsExpressions.ExprArcTan.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] arc tan[gent] %number%" });
    registerNewExpression("Arc Sine", uk.co.umbaska.MathsExpressions.ExprArcSine.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] arc sin[e] %number%" });
    registerNewExpression("Arc Cos", uk.co.umbaska.MathsExpressions.ExprArcCos.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] arc cos[ine] %number%" });
    registerNewExpression("Tan", uk.co.umbaska.MathsExpressions.ExprTan.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] tan[gent] %number%" });
    registerNewExpression("Sine", uk.co.umbaska.MathsExpressions.ExprSine.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] sin[e] %number%" });
    registerNewExpression("Cos", uk.co.umbaska.MathsExpressions.ExprCos.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] cos[ine] %number%" });
    registerNewExpression("Atan", ExprAtan.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] atan %number%" });
    registerNewExpression("Atan2", ExprAtan.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] atan 2 %number%(,| and) %number%" });
    registerNewExpression("Hyperbolic Tan", uk.co.umbaska.MathsExpressions.ExprHyperbolicTan.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] hyperbolic tan[gent] %number%" });
    registerNewExpression("Hyperbolic Sine", uk.co.umbaska.MathsExpressions.ExprHyperbolicSin.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] hyperbolic sin[e] %number%" });
    registerNewExpression("Hyperbolic Cos", ExprHyperbolicCos.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] hyperbolic cos[ine] %number%" });
    
    registerNewExpression("Logarithm", uk.co.umbaska.MathsExpressions.ExprLogarithm.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] [natural ]log[arithm] %number%" });
    registerNewExpression("Base10", uk.co.umbaska.MathsExpressions.ExprBase10.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] base(-| )10 [log[arithm]] %number%" });
    registerNewExpression("Signum", ExprSignum.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] signum %number%" });
    registerNewExpression("Square Root", uk.co.umbaska.MathsExpressions.ExprSqrt.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] (sqrt|square root) [of] %number%" });
    registerNewExpression("Fectorial", ExprFactorial.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] %number% factorial" });
    registerNewExpression("Fectorial", ExprFactorial.class, Number.class, ExpressionType.SIMPLE, new String[] { "[umbaska] %number%!" });
    registerNewExpression("Simple Vector", uk.co.umbaska.ParticleProjectiles.Expressions.ExprSimpleVector.class, org.bukkit.util.Vector.class, ExpressionType.SIMPLE, new String[] { "[umbaska] (vector from|new vector [from]) %number%, %number%, %number%" });
    

    Bukkit.getLogger().info("[Umbaska] Registering Armor Stand related expressions");
    registerNewExpression("Closest Entity from Location", uk.co.umbaska.Misc.UM2_0.ExprClosestEntityFromLocation.class, Entity.class, ExpressionType.SIMPLE, new String[] { "closest entity from [location] %location%" });
    registerNewSimpleExpression("Armor Stand - Is Marker", uk.co.umbaska.ArmourStands.ExprMarker.class, Boolean.class, "[has] marker", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Has NoAI", ExprNoAI.class, Boolean.class, "no[ ]ai[ state]", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Is Silent", uk.co.umbaska.ArmourStands.ExprSilent.class, Boolean.class, "silent[ state]", "entity", Boolean.valueOf(false));
    


    registerNewSimpleExpression("Armor Stand - Show Arms", uk.co.umbaska.ArmourStands.ExprsArms.class, Boolean.class, "[show] arms", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Show Base", uk.co.umbaska.ArmourStands.ExprsBasePlate.class, Boolean.class, "[show] base plate", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Has Gravity", uk.co.umbaska.ArmourStands.ExprsGravity.class, Boolean.class, "[has] gravity", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Is Small", uk.co.umbaska.ArmourStands.ExprsSmall.class, Boolean.class, "[is] small", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Is Visible", ExprsVisible.class, Boolean.class, "[is] visible", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Right Leg X", ExprsRightLegDirectionX.class, Number.class, "right leg (x angle|angle x)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Right Leg Y", ExprsRightLegDirectionY.class, Number.class, "right leg (y angle|angle y)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Right Leg Z", ExprsRightLegDirectionZ.class, Number.class, "right leg (z angle|angle z)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Left Leg X", uk.co.umbaska.ArmourStands.Legs.ExprsLeftLegDirectionX.class, Number.class, "left leg (x angle|angle x)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Left Leg Y", uk.co.umbaska.ArmourStands.Legs.ExprsLeftLegDirectionY.class, Number.class, "left leg (y angle|angle y)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Left Leg Z", uk.co.umbaska.ArmourStands.Legs.ExprsLeftLegDirectionZ.class, Number.class, "left leg (z angle|angle z)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Right Arm X", uk.co.umbaska.ArmourStands.Arms.ExprsRightArmDirectionX.class, Number.class, "right arm (x angle|angle x)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Right Arm Y", uk.co.umbaska.ArmourStands.Arms.ExprsRightArmDirectionY.class, Number.class, "right arm (y angle|angle y)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Right Arm Z", uk.co.umbaska.ArmourStands.Arms.ExprsRightArmDirectionZ.class, Number.class, "right arm (z angle|angle z)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Left Arm X", uk.co.umbaska.ArmourStands.Arms.ExprsLeftArmDirectionX.class, Number.class, "left arm (x angle|angle x)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Left Arm Y", uk.co.umbaska.ArmourStands.Arms.ExprsLeftArmDirectionY.class, Number.class, "left arm (y angle|angle y)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Left Arm Z", uk.co.umbaska.ArmourStands.Arms.ExprsLeftArmDirectionZ.class, Number.class, "left arm (z angle|angle z)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Head X", ExprsHeadDirectionX.class, Number.class, "head (x angle|angle x)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Head Y", ExprsHeadDirectionY.class, Number.class, "head (y angle|angle y)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Head Z", ExprsHeadDirectionZ.class, Number.class, "head (z angle|angle z)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Body X", uk.co.umbaska.ArmourStands.ExprsBodyDirectionX.class, Number.class, "body (x angle|angle x)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Body Y", uk.co.umbaska.ArmourStands.ExprsBodyDirectionY.class, Number.class, "body (y angle|angle y)", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Armor Stand - Body Z", uk.co.umbaska.ArmourStands.ExprsBodyDirectionZ.class, Number.class, "body (z angle|angle z)", "entity", Boolean.valueOf(false));
    



    registerNewSimpleExpression("Zombie Villager State", uk.co.umbaska.Misc.UM2_0.ExprZombieVillager.class, Boolean.class, "zombie villager state", "entity", Boolean.valueOf(false));
    
    registerNewExpression("Glow", uk.co.umbaska.Misc.ExprBetterGlow.class, ItemStack.class, ExpressionType.SIMPLE, new String[] { "[a[n]] [umbaska] glow[ing] %itemstack%" });
    registerNewExpression("Absorption Hearts", uk.co.umbaska.Misc.ExprAbsorptionHearts.class, Number.class, ExpressionType.SIMPLE, new String[] { "absorption hearts of %player%" });
    

    registerNewExpression("No AI Entity", uk.co.umbaska.Misc.ExprNoAIEntity.class, Entity.class, ExpressionType.PROPERTY, new String[] { "%entity% with no[( |-)]ai" });
    


    registerNewExpression("Split All Characeters", uk.co.umbaska.Misc.UM2_0.ExprSplitAtAllCharacters.class, String.class, ExpressionType.COMBINED, new String[] { "%string% split at all characters" });
    

    registerNewExpression("Banner - New Banner", ExprNewBannerFrom.class, ItemStack.class, ExpressionType.COMBINED, new String[] { "%color% banner with layers" });
    registerNewExpression("Banner - New Banner", ExprNewBannerFrom.class, ItemStack.class, ExpressionType.COMBINED, new String[] { "banner colo[u]red %color% with layers" });
    registerNewExpression("Banner - New Layer", ExprNewLayer.class, ItemStack.class, ExpressionType.COMBINED, new String[] { "%itemstack% [(and|,)] colo[u]r[ed] %color% [(and|with)] pattern %bannerpattern%" });
    
    registerNewExpression("Banner - Layer of Block", uk.co.umbaska.Misc.Banners.ExprBannerLayer.class, org.bukkit.block.banner.Pattern.class, ExpressionType.SIMPLE, new String[] { "[pattern] layer %integer% of %block%" });
    

    if (use_bungee.booleanValue() == true) {
      registerNewExpression("Bungee - Bungee UUID", ExprBungeeUUID.class, java.util.UUID.class, ExpressionType.PROPERTY, new String[] { "Bungee uuid of %player%" });
      registerNewExpression("Bungee - All Servers", ExprAllServers.class, String.class, ExpressionType.SIMPLE, new String[] { "all Bungee[ ][cord] servers" });
      registerNewExpression("Bungee - All Players", uk.co.umbaska.Bungee.ExprBungeeAllPlayers.class, String.class, ExpressionType.SIMPLE, new String[] { "all Bungee[ ][cord] players" });
      registerNewExpression("Bungee - Players on Server", uk.co.umbaska.Bungee.ExprBungeePlayersOnServer.class, Integer.class, ExpressionType.SIMPLE, new String[] { "players on Bungee[ ][cord] server %string%" });
      registerNewExpression("Bungee - Server of Player", uk.co.umbaska.Bungee.ExprBungeeServerOfPlayer.class, String.class, ExpressionType.SIMPLE, new String[] { "Bungee[ ][cord] server of %string%" });
      registerNewExpression("Bungee - Server Count", ExprBungeeServerCount.class, Integer.class, ExpressionType.SIMPLE, new String[] { "players on Bungee[ ][cord] proxy" });
    }
    

    registerNewExpression("Get Player from Fake Player", ExprGetPlayer.class, Player.class, ExpressionType.SIMPLE, "player from fake player %string%");
    


    registerNewExpression("JSON - JSON Message", uk.co.umbaska.JSON.ExprJsonMessage.class, JSONMessage.class, ExpressionType.SIMPLE, new String[] { "json [of] %string%" });
    registerNewExpression("JSON - Message Append", uk.co.umbaska.JSON.ExprJsonAppend.class, JSONMessage.class, ExpressionType.SIMPLE, new String[] { "%umbjsonmessage% then %string%" });
    registerNewExpression("JSON - Message Run/Suggest Command", uk.co.umbaska.JSON.ExprJsonMessageCommand.class, JSONMessage.class, ExpressionType.SIMPLE, new String[] { "%umbjsonmessage% suggest %string%", "%umbjsonmessage% run %string%" });
    registerNewExpression("JSON - Message Style", ExprJsonMessageStyle.class, JSONMessage.class, ExpressionType.SIMPLE, new String[] { "%umbjsonmessage% styled %colors%" });
    registerNewExpression("JSON - Show Tooltip", uk.co.umbaska.JSON.ExprJsonMessageTooltip.class, JSONMessage.class, ExpressionType.SIMPLE, new String[] { "%umbjsonmessage% tooltip %string%" });
    registerNewExpression("JSON - Open URL", uk.co.umbaska.JSON.ExprJsonMessageURL.class, JSONMessage.class, ExpressionType.SIMPLE, new String[] { "%umbjsonmessage% open %string%" });
    
    registerNewExpression("Server Ping IP", uk.co.umbaska.Misc.NotVersionAffected.ExprServerPingIP.class, String.class, ExpressionType.SIMPLE, new String[] { "server ping ip" });
    

    registerNewExpression(ExprBlitzkrieg.class, String.class, ExpressionType.SIMPLE, new String[] { "blitzkrieg" });

    registerNewSimpleExpression("1.9 - Glowing", uk.co.umbaska.ArmourStands.ExprGlowingEntity.class, Boolean.class, "glowing state", "entity", Boolean.valueOf(false));
    registerNewExpression("New Potion Effect", ExprNewPotionEffect.class, PotionEffect.class, ExpressionType.COMBINED, new String[] { "new potion effect [of][ ][type] %potioneffecttype% [of][ ][tier][ ]%number% (to last|with durability|time) %number%" });
    registerNewExpression("New Bukkit Color", ExprNewBukkitColor.class, Color.class, ExpressionType.COMBINED, new String[] { "new color from [rgb] %number%, %number%(,| and) %number%" });
    
    registerNewSimpleExpression("1.9 - Offhand Item Player", ExprOffhandItemPlayer.class, ItemStack.class, "[player] (off[ ]hand|secondary) item", "player", Boolean.valueOf(false));
    registerNewSimpleExpression("1.9 - Offhand Item Entity", ExprOffhandItem.class, ItemStack.class, "[entity] (off[ ]hand|secondary) item", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("1.9 - Mainhand Item Player", ExprMainhandItemPlayer.class, ItemStack.class, "[player] (main[ ]hand|primary) item", "player", Boolean.valueOf(false));
    registerNewSimpleExpression("1.9 - Mainhand Item Entity", ExprMainhandItem.class, ItemStack.class, "[entity] (main[ ]hand|primary) item", "entity", Boolean.valueOf(false));
    
    registerNewSimpleExpression("Area Effect Cloud - Cloud Color", ExprEffectCloudColor.class, Color.class, "[area][ ][effect][ ][cloud] colo[u]r", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Duration", ExprEffectCloudDuration.class, Number.class, "[area][ ][effect][ ][cloud] duration", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Duration on Use", ExprEffectCloudDurationOnUse.class, Number.class, "[area][ ][effect][ ][cloud] duration on use", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Particle", ExprEffectCloudParticle.class, ParticleEnum/*Area*/.class, "[area][ ][effect][ ][cloud] particle", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Radius", ExprEffectCloudRadius.class, Number.class, "[area][ ][effect][ ][cloud] radius", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Radius on Use", ExprEffectCloudRadiusOnUse.class, Number.class, "[area][ ][effect][ ][cloud] radius on use", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Radius per Tick", ExprEffectCloudRadiusPerTick.class, Number.class, "[area][ ][effect][ ][cloud] radius per tick", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Duration", ExprEffectCloudDuration.class, Number.class, "[area][ ][effect][ ][cloud] duration", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Reapplication Delay", ExprEffectCloudReapplicationDelay.class, Number.class, "[area][ ][effect][ ][cloud] reapplication delay", "entity", Boolean.valueOf(false));
    registerNewSimpleExpression("Area Effect Cloud - Cloud Wait Time", ExprEffectCloudWaitTime.class, Number.class, "[area][ ][effect][ ][cloud] wait time", "entity", Boolean.valueOf(false));
    registerNewExpression("Area Effect Cloud - Cloud Potion Effects", ExprEffectCloudDuration.class, PotionEffect.class, ExpressionType.SIMPLE, new String[] { "potion effects of [area][ ][effect][ ][cloud] %entity%" });
    

    registerNewExpression("Boss Bar - Serialise Boss Bar", ExprSerialisedBossBar.class, String.class, ExpressionType.SIMPLE, new String[] { "seriali(z|s)ed [(contents|data)] [of] [boss][ ][bar] %bossbar%" });
    registerNewExpression("Boss Bar - Get Boss Bar from Data", ExprGetBarFromSerialised.class, BossBar.class, ExpressionType.SIMPLE, new String[] { "boss[ ]bar from data %string%" });

    registerNewExpression("Boss Bar - New Boss Bar", ExprNewBossBar.class, BossBar.class, ExpressionType.COMBINED, new String[] { "new boss[ ]bar" });
    
    registerNewSimpleExpression("Boss Bar - Bar Colour", ExprBossBarColor.class, org.bukkit.boss.BarColor.class, "[boss][ ][bar] bar colo[u]r", "bossbar", Boolean.valueOf(false));
    registerNewSimpleExpression("Boss Bar - Bar Style", ExprBossBarColor.class, org.bukkit.boss.BarStyle.class, "[boss][ ][bar] bar style", "bossbar", Boolean.valueOf(false));
    registerNewSimpleExpression("Boss Bar - Bar Title", uk.co.umbaska.BossBars.ExprBossBarTitle.class, String.class, "[boss][ ][bar] title", "bossbar", Boolean.valueOf(false));
    registerNewSimpleExpression("Boss Bar - Bar Progress", uk.co.umbaska.BossBars.ExprBossBarProgress.class, Number.class, "[boss][ ][bar] progress", "bossbar", Boolean.valueOf(false));
    

    registerNewExpression("Boss Bar - Bar Flags", uk.co.umbaska.BossBars.ExprFlagsOfBar.class, BarFlag.class, ExpressionType.SIMPLE, new String[] { "flags of [boss][ ][bar] %bossbar%" });
    registerNewExpression("Boss Bar - Bar Players", uk.co.umbaska.BossBars.ExprPlayersOfBar.class, BarFlag.class, ExpressionType.SIMPLE, new String[] { "players of [boss][ ][bar] %bossbar%" });
  }
}
