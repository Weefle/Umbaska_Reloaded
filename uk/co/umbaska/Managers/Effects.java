package uk.co.umbaska.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.dynmap.DynmapAPI;

import ch.njol.skript.Skript;
import uk.co.umbaska.Main;
import uk.co.umbaska.AreaEffectCloud.EffAddPotionToCloud;
import uk.co.umbaska.AreaEffectCloud.EffRemovePotionFromCloud;
import uk.co.umbaska.AreaEffectCloud.EffSpawnAreaEffectCloud;
import uk.co.umbaska.Attributes.EffAddAttributeModifer;
import uk.co.umbaska.BossBars.EffAddPlayerToBossBar;
import uk.co.umbaska.BossBars.EffRemoveAllPlayersFromBar;
import uk.co.umbaska.BossBars.EffRemovePlayerFromBossBar;
import uk.co.umbaska.Bungee.EffBungeeBroadcast;
import uk.co.umbaska.Bungee.EffSendBungeeMsg;
import uk.co.umbaska.Dynmap.EffSetVisOfPlayer;
import uk.co.umbaska.Factions.EffClaimLand;
import uk.co.umbaska.Factions.EffCreateFaction;
import uk.co.umbaska.GattSk.Effects.EffCreateTeam;
import uk.co.umbaska.GattSk.Effects.EffDeleteScoreboard;
import uk.co.umbaska.GattSk.Effects.EffLoadWorld;
import uk.co.umbaska.GattSk.Effects.EffNewObjective;
import uk.co.umbaska.GattSk.Effects.EffSetObjectiveName;
import uk.co.umbaska.GattSk.Effects.EffSetScore;
import uk.co.umbaska.GattSk.Effects.EffSetTeamFF;
import uk.co.umbaska.GattSk.Effects.EffUnloadWorld;
import uk.co.umbaska.GattSk.Effects.SimpleScoreboards.EffSetTitle;
import uk.co.umbaska.GattSk.Effects.SimpleScoreboards.EffShowBoard;
import uk.co.umbaska.HologramBased.EffAddHoloLine;
import uk.co.umbaska.HologramBased.EffCreateFollowGram;
import uk.co.umbaska.HologramBased.EffDeleteHolo;
import uk.co.umbaska.HologramBased.EffHoloFollow;
import uk.co.umbaska.HologramBased.EffHoloStart;
import uk.co.umbaska.Misc.EffOpenInventory;
import uk.co.umbaska.Misc.EffOpenInventoryClose;
import uk.co.umbaska.Misc.EffSetCommandBlockInfo;
import uk.co.umbaska.Misc.JukeboxAPI.EffPlaySong;
import uk.co.umbaska.Misc.NotVersionAffected.EffCopyDir;
import uk.co.umbaska.Misc.NotVersionAffected.EffNothing_MFG;
import uk.co.umbaska.Misc.NotVersionAffected.EffPlaceDroppedItem;
import uk.co.umbaska.Misc.NotVersionAffected.EffShootShulkerBullet;
import uk.co.umbaska.Misc.UM2_0.EffLoadPlugin;
import uk.co.umbaska.Misc.UM2_0.EffPotionEffectNoParticles;
import uk.co.umbaska.NametagEdit.EffSetSuffix;
import uk.co.umbaska.ProtocolLib.EffHideEntity;
import uk.co.umbaska.ProtocolLib.EffToggleVisibility;
import uk.co.umbaska.ProtocolLib.EntityHider;
import uk.co.umbaska.ProtocolLib.Disguises.EffDisguiseAsEntity;
import uk.co.umbaska.ProtocolLib.Disguises.EffDisguiseAsPlayer;
import uk.co.umbaska.ProtocolLib.Disguises.EffUndisguise;
import uk.co.umbaska.ProtocolLib.FakePlayer.EffSetSkin;
import uk.co.umbaska.Replacers.EffBukkitEffectAll;
import uk.co.umbaska.Replacers.EffParticleAll;
import uk.co.umbaska.Replacers.EffSetResultSlot;
import uk.co.umbaska.Spawner.EffMFG_Drop;
import uk.co.umbaska.Spawner.EffSetDelay;
import uk.co.umbaska.System.EffCreateFile;
import uk.co.umbaska.System.EffDeleteFile;
import uk.co.umbaska.System.EffSetLine;
import uk.co.umbaska.System.EffWriteToFile;
import uk.co.umbaska.mcMMO.EffSendAdminMesssage;

public class Effects
{
  public static EntityHider enthider;
  public static Boolean enable_tag_features = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("enable_tag_features"));
  public static Boolean use_bungee = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("use_bungee"));
  public static Boolean forceGenTitleFeatures = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("force-generate-title-features"));
  public static Boolean forceGen18Features = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("force-generate-18-features"));
  public static Plugin dynmap;
  public static DynmapAPI api;
  public static Boolean debugInfo = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("debug_info"));
  
  private static String version = Register.getVersion();
  
  @SuppressWarnings({ "rawtypes", "unused" })
private static void registerNewEffect(String name, String cls, String syntax, Boolean multiversion) {
    if (Skript.isAcceptRegistrations()) {
      if (multiversion.booleanValue()) {
        Class newCls = Register.getClass(cls);
        if (newCls == null) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Effect for " + name + " due to Can't find Class!");
          return;
        }
        registerNewEffect(name, newCls, syntax);
      }
      else {
        try {
          registerNewEffect(name, Class.forName(cls), syntax);
        } catch (ClassNotFoundException e) {
          Bukkit.getLogger().info("Umbaska »»» Can't Register Effect for " + name + " due to Wrong Spigot/Bukkit Version!");
        }
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Effect for " + name + " due to Skript Not Accepting Registrations");
    }
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
private static void registerNewEffect(String name, Class cls, String[] syntax) {
    if (Skript.isAcceptRegistrations()) {
      Skript.registerEffect(cls, syntax);
      
      if (debugInfo.booleanValue()) {
        Bukkit.getLogger().info("Umbaska »»» Registered Effect for " + name + " with syntax \n" + syntax);
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Effect for " + name + " due to Skript Not Accepting Registrations");
    }
    List<String> expressions = (List)Register.effectList.get(name);
    if (expressions == null) {
      expressions = new ArrayList();
    }
    for (String s : syntax) {
      expressions.add(s);
    }
    Register.effectList.put(name, expressions);
  }
  
  @SuppressWarnings("rawtypes")
private static void registerNewEffect(String name, Class cls, String syntax) {
    if (Skript.isAcceptRegistrations()) {
      registerNewEffect(cls, syntax);
      
      if (debugInfo.booleanValue()) {
        Bukkit.getLogger().info("Umbaska »»» Registered Effect for " + name + " with syntax \n" + syntax);
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Effect for " + name + " due to Skript Not Accepting Registrations");
    }
    List<String> expressions = Register.effectList.get(name);
    if (expressions == null) {
      expressions = new ArrayList<>();
    }
    expressions.add(syntax);
    Register.effectList.put(name, expressions);
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
@Deprecated
  private static void registerNewEffect(Class cls, String syntax) {
    if (Skript.isAcceptRegistrations()) {
      Skript.registerEffect(cls, new String[] { syntax });
      if (debugInfo.booleanValue()) {
        Bukkit.getLogger().info("Umbaska »»» Registered Effect for " + cls.getName() + " with syntax\n " + syntax);
      }
    }
    else {
      Bukkit.getLogger().info("Umbaska »»» Can't Register Effect for " + cls.getName() + " due to Skript Not Accepting Registrations");
    }
  }
  


  public static void runRegister()
  {
	  Plugin /*pl = Bukkit.getServer().getPluginManager().getPlugin("PlotSquared");
	    Boolean registeredPlotSquared = Boolean.valueOf(false);
	    if (pl != null)
	    {
	      registeredPlotSquared = Boolean.valueOf(true);
	      registerNewEffect("PlotSquared - Teleport to Plot", uk.co.umbaska.PlotSquared.EffPlotTeleport.class, "teleport %player% to %string%[ in %world%]");
	      registerNewEffect("PlotSquared - Clear Plot", uk.co.umbaska.PlotSquared.EffClearPlot.class, "clear plot %string% in %world%");
	      registerNewEffect("PlotSquared - Move Plot", uk.co.umbaska.PlotSquared.EffMovePlot.class, "move %string% to %string% in %world%");
	      registerNewEffect("PlotSquared - Deny Player", uk.co.umbaska.PlotSquared.EffDenyPlayer.class, "deny %player% from %string%");
	      registerNewEffect("PlotSquared - Allow Player", uk.co.umbaska.PlotSquared.EffUnDeny.class, "allow %player% to %string%");
	      registerNewEffect("PlotSquared - WorldEdit", EffToggleWorldEdit.class, new String[] { "set worldedit of %player% to %boolean%", "toggle worldedit of %player%", "enable worldedit of %player%", "disable worldedit of %player%" });
	    }*/
	    pl = Bukkit.getServer().getPluginManager().getPlugin("PlotMe");
	    if (/*(!registeredPlotSquared.booleanValue()) && */
	      (pl != null))
	    {
      registerNewEffect("PlotMe - Plot Teleport", uk.co.umbaska.PlotMe.EffPlotTeleport.class, "teleport %player% to %string%[ in %world%]");
      registerNewEffect("PlotMe - Clear Plot", uk.co.umbaska.PlotMe.EffClearPlot.class, "clear plot %string% in %world%");
      registerNewEffect("PlotMe - Move Plot", uk.co.umbaska.PlotMe.EffMovePlot.class, "move %string% to %string% in %world%");
      registerNewEffect("PlotMe - Deny Player", uk.co.umbaska.PlotMe.EffDenyPlayer.class, "deny %player% from %string%");
      registerNewEffect("PlotMe - Allow Player", uk.co.umbaska.PlotMe.EffUnDeny.class, "allow %player% to %string%");
    }
    
    pl = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
    if (pl != null) {
      registerNewEffect("PlaceholderAPI - Add Placeholder", uk.co.umbaska.PlaceHolderAPI.EffAddPlaceholder.class, new String[] { "add [variable] %string% with [value] %string% to placeholders", "add [variable] %string% with [value] %string% using [plugin] %string% to placeholders" });
      registerNewEffect("PlaceholderAPI - Add Plugin?", uk.co.umbaska.PlaceHolderAPI.EffAddPlugin.class, "set plugin of %string% to %string% in placeholders");
    }
    
    registerNewEffect("World Border", uk.co.umbaska.WorldBorder.EffWorldBorder.class, new String[] { "set cent(re|er) of worldborder in %world% to %location%", "set cent(re|er) of worldborder in %world% to %number%, %number%", "set [damage] amount of worldborder in %world% to %number%", "set [damage] buffer of worldborder in %world% to %number%", "set size of worldborder in %world% to %number%", "set size of worldborder in %world% to %number%, %long%", "set [warning] distance of worldborder in %world% to %integer%", "set [warning] time of worldborder in %world% to %integer%" });
    












    pl = Bukkit.getServer().getPluginManager().getPlugin("NoteBlockAPI");
    if (pl != null) {
      registerNewEffect("NoteBlockAPI - Play MIDI", uk.co.umbaska.Sound.EffPlayTrack.class, "play (track|song|midi) %string% to %player%");
    }
    

    registerNewEffect("Set Spawner Type", uk.co.umbaska.Spawner.EffSetSpawner.class, "set spawner %location% to %string%");
    registerNewEffect("Set Spawner Spawn Delay", EffSetDelay.class, "set delay of %location% to %integer%");
    registerNewEffect("Drop Spawner", EffMFG_Drop.class, "drop a spawner at %location% based on %block%");
    registerNewEffect("Give Spawner", uk.co.umbaska.Spawner.EffMFG_GiveSpawner.class, "give a spawner to %player% based on %block%");
    registerNewEffect("Set Spawner to its type", uk.co.umbaska.Spawner.EffMFG_SetSpawner.class, "set spawner at %location% to its type");
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("Towny");
    if (pl != null)
    {
      registerNewEffect("Towny - Set Plot Owner", uk.co.umbaska.Towny.EffSetPlotOwner.class, "set owner of plot at %location% to %player%");
      registerNewEffect("Towny - Set Plot Price", uk.co.umbaska.Towny.EffSetPlotPrice.class, "set price of plot at %location% to %double%");
    }
    



    registerNewEffect("Drop Inventory", uk.co.umbaska.Misc.NotVersionAffected.EffDropAll.class, "force drop inventory of %player% at %location%");
    
    registerNewEffect("Create File", EffCreateFile.class, "create [new] file %string%");
    registerNewEffect("Delete File", EffDeleteFile.class, "(df|delete) [file] %string%");
    registerNewEffect("Set Line", EffSetLine.class, "set line %integer% in [file] %string% to %string%");
    registerNewEffect("Write Line", EffWriteToFile.class, "(write|wf) %string% to %string%)");
    
    registerNewEffect("Write YAML", uk.co.umbaska.Misc.NotVersionAffected.EffWriteYAML.class, "write %string% with [value] %string% to %string%");
    registerNewEffect("Copy File", uk.co.umbaska.Misc.NotVersionAffected.EffCopy.class, "copy file %string% to %string%");
    registerNewEffect("Copy Directory", EffCopyDir.class, "copy (d|dir|dire|directory) %string% to %string%");
    
    registerNewEffect("Load Script", uk.co.umbaska.System.EffLoadScript.class, "load script[s] from [folder] %string%");
    
    registerNewEffect("Set Command Block Data", EffSetCommandBlockInfo.class, new String[] { "set command of %block% to %string%", "set name of %block% to %string%" });
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
    if (pl != null)
    {
      Bukkit.getLogger().info("Loading ProtocolLib Effects");
      
      enthider = new EntityHider(Main.getInstance(), EntityHider.Policy.BLACKLIST);
      
      registerNewEffect("ProtocolLib - Hide Entity", EffHideEntity.class, "protocol hide %entity% from %players%");
      registerNewEffect("ProtocolLib - Show Entity", uk.co.umbaska.ProtocolLib.EffShowEntity.class, "protocol show %entity% to %players%");
      registerNewEffect("ProtocolLib - Toggle Visibility", EffToggleVisibility.class, "toggle visibility of %entity% for %players%");
      Skript.registerExpression(uk.co.umbaska.ProtocolLib.ExprCanSee.class, Boolean.class, ch.njol.skript.lang.ExpressionType.PROPERTY, new String[] { "visibility of %entity% for %player%" });
      

      registerNewEffect("Disguise", EffDisguiseAsEntity.class, "disguise %entity% as %entitydisguise% [with custom name %-string%]");
      registerNewEffect("Disguise", EffDisguiseAsPlayer.class, "disguise %entity% as player %string%");
      registerNewEffect("Undisguise", EffUndisguise.class, "undisguise %entity%");
    }
    








    pl = Bukkit.getServer().getPluginManager().getPlugin("JukeboxAPI");
    if (pl != null) {
      registerNewEffect("JukeboxAPI Send Song", EffPlaySong.class, "play song [from] [url] %string% to %players%");
      registerNewEffect("JukeboxAPI Send Song with Volume", uk.co.umbaska.Misc.JukeboxAPI.EffPlaySongVolume.class, "play song [from] [url] %string% to %players% [with] volume %number%");
      registerNewEffect("JukeboxAPI Send Sound Effect", uk.co.umbaska.Misc.JukeboxAPI.EffPlaySoundEffect.class, "play sound effect [from] [url] %string% to %players%");
      registerNewEffect("JukeboxAPI Send Sound Effect with Volume", uk.co.umbaska.Misc.JukeboxAPI.EffPlaySoundEffectVolume.class, "play sound effect [from] [url] %string% to %players% [with] volume %number%");
      registerNewEffect("JukeboxAPI Stop Current Song", uk.co.umbaska.Misc.JukeboxAPI.EffStopSong.class, "stop current song for %players%");
    }
    


    pl = Bukkit.getServer().getPluginManager().getPlugin("NametagEdit");
    
    if ((pl != null) && 
      (enable_tag_features.booleanValue() == true)) {
      registerNewEffect("NametagEdit - Set Prefix", uk.co.umbaska.NametagEdit.EffSetPrefix.class, "set prefix of %player% to %string%");
      registerNewEffect("NametagEdit - Set Suffix", EffSetSuffix.class, "set suffix of %player% to %string%");
      registerNewEffect("NametagEdit - Set Nametag", uk.co.umbaska.NametagEdit.EffSetNametag.class, "set name tag of %player% to %string% and %string%");
    }
    


    pl = Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
    if (pl != null)
    {


      PluginManager pm = Bukkit.getServer().getPluginManager();
      dynmap = pm.getPlugin("Dynmap");
      api = (DynmapAPI)dynmap;
      if (api == null) {
        Main.getInstance().getLogger().info(org.bukkit.ChatColor.RED + "[Umbaska] Damn son! There was a problem hooking into Dynmap. Sorry dude.");
      }
      else
      {
        registerNewEffect("Dynmap - Set Visibility", EffSetVisOfPlayer.class, "set Dynmap visibility of %player% to %boolean%");
      }
    }
    


    if (!Main.disableSkRambled.booleanValue()) {
      pl = Bukkit.getServer().getPluginManager().getPlugin("MassiveCore");
      if (pl != null) {
        pl = Bukkit.getServer().getPluginManager().getPlugin("Factions");
        if (pl != null)
        {
          registerNewEffect("Factions - Unclaim Land", uk.co.umbaska.Factions.EffUnClaimLand.class, "unclaim [faction] land at %location%");
          
          registerNewEffect("Factions - Claim Land", EffClaimLand.class, "claim land for [the] [faction] %faction% at %location%");
          
          registerNewEffect("Factions - Invite to Faction", uk.co.umbaska.Factions.EffInvitePlayer.class, "invite %player% to [the] [faction] %faction%");
          
          registerNewEffect("Factions - Kick Player", uk.co.umbaska.Factions.EffKickPlayer.class, "remove %player% from [the] [faction] %faction%");
          
          registerNewEffect("Factions - Disband Faction", uk.co.umbaska.Factions.EffDisbandFaction.class, "disband [the] [faction] %faction%");
          
          registerNewEffect("Factions - Create Faction", EffCreateFaction.class, "create a faction [with name] %string% with leader %player%");
        }
      }
      

      pl = Bukkit.getServer().getPluginManager().getPlugin("mcMMO");
      if (pl != null) {
        registerNewEffect("mcMMO - Send Party Message", uk.co.umbaska.mcMMO.EffSendPartyMessage.class, "send %string% to [the] party %party% from [a] player named %string%");
        
        registerNewEffect("mcMMO - Send Admin Message", EffSendAdminMesssage.class, "send %string% to [the] admin[chat| chat] from [a] player named %string%");
      }
    }
    

    pl = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    if (pl != null) {
      registerNewEffect("Paste Schematic", uk.co.umbaska.WorldEdit.EffPasteSchematic.class, "[umbaska] paste schematic %string% at %location%");
      registerNewEffect("Paste Schematic No Air", uk.co.umbaska.WorldEdit.EffPasteSchematicNoAir.class, "[umbaska] paste schematic %string% at %location% (ignoring air|with no air)");
      registerNewEffect("Place Schematic", uk.co.umbaska.WorldEdit.EffPlaceSchematic.class, "[umbaska] place schematic %string% at %location%");
      registerNewEffect("Place Schematic no Air", uk.co.umbaska.WorldEdit.EffPlaceSchematicNoAir.class, "[umbaska] place schematic %string% at %location% (ignoring|with no) air");
      registerNewEffect("Save Schematic", uk.co.umbaska.WorldEdit.EffSaveSchematic.class, "save schematic %string% based (off of|on) %player%['s selection]");
    }
    


    registerNewEffect("Leash Entity to Entity", uk.co.umbaska.Misc.EffLeashEntityEntity.class, "leash %entity% to %entity%");
    registerNewEffect("Load Plugin", EffLoadPlugin.class, "load plugin %string%");
    registerNewEffect("Disable Plugin", uk.co.umbaska.Misc.UM2_0.EffDisablePlugin.class, "disable plugin %string%");
    registerNewEffect("Reload Plugin", uk.co.umbaska.Misc.UM2_0.EffReloadPlugin.class, "reload plugin %string%");
    

    registerNewEffect("Remove Exploded Block", uk.co.umbaska.GattSk.Effects.EffRemoveExplodedBlock.class, "(remove|delete) %block% from [better][ ][new] exploded blocks");
    registerNewEffect("Clear Exploded Blocks", uk.co.umbaska.GattSk.Effects.EffClearExplodedBlocks.class, "(remove|delete|clear) all [better] exploded blocks");
    

    registerNewEffect("Scoreboard - New Scoreboard", uk.co.umbaska.GattSk.Effects.EffNewScoreboard.class, "create [a] new scoreboard [named] %string%");
    registerNewEffect("Scoreboard - Display Scoreboard", uk.co.umbaska.GattSk.Effects.EffSetPlayerScoreboard.class, "set scoreboard of %players% to %string%");
    registerNewEffect("Scoreboard - Set Score", EffSetScore.class, "set value of score %string% (for|in) [score][board] %string% objective %string% to %number%");
    registerNewEffect("Scoreboard - Reset Player Score", uk.co.umbaska.GattSk.Effects.EffResetScore.class, "reset [value] [of] score %string% (for|in) [score][board] %string%");
    registerNewEffect("Scoreboard - Create New Objective", EffNewObjective.class, "create [a] [new] %string% objective for [score][board] %string% (called|named) %string%");
    registerNewEffect("Scoreboard - Set Objective Display", uk.co.umbaska.GattSk.Effects.EffSetObjectiveDisplay.class, "set objective display slot for [objective] %string% in [score][board] %string% to %string%");
    registerNewEffect("Scoreboard - Set Objective Name", EffSetObjectiveName.class, "set objective display name for [objective] %string% in [score][board] %string% to %string%");
    registerNewEffect("Scoreboard - Unregister Objective", uk.co.umbaska.GattSk.Effects.EffUnregisterObjective.class, "unregister objective %string% in [score][board] %string%");
    registerNewEffect("Scoreboard - Delete Scoreboard", EffDeleteScoreboard.class, "delete score[ ]board %string%");
    registerNewEffect("Scoreboard - Create Team", EffCreateTeam.class, "create team %string% in [score][board] %string%");
    registerNewEffect("Scoreboard - Add Player to Team", uk.co.umbaska.GattSk.Effects.EffTeamPlayer.class, "(remove|add) [player] %offlineplayer% (from|to) team %string% in [score][board] %string%");
    registerNewEffect("Scoreboard - Set Team Prefix", uk.co.umbaska.GattSk.Effects.EffSetTeamPrefix.class, "set (suffix|prefix) for team %string% in [score][board] %string% to %string%");
    registerNewEffect("Scoreboard - Set Friendly Fire", EffSetTeamFF.class, "set friendly fire for team %string% in [score][board] %string% to %boolean%");
    registerNewEffect("Scoreboard - See Friendly Invisibles", uk.co.umbaska.GattSk.Effects.EffSetTeamSeeInvis.class, "set see friendly invisibles for team %string% in [score][board] %string% to %boolean%");
    registerNewEffect("Open Inventory", EffOpenInventory.class, "open %umbaskainv% [named %-string%] to %player%");
    registerNewEffect("Open Inventory", EffOpenInventoryClose.class, "open %umbaskainv% [named %-string%] to %player% (that closes|to close)");
    registerNewEffect("Open Inventory", uk.co.umbaska.Misc.NotVersionAffected.EffOpenInventoryRows.class, "open %umbaskainv% [named %-string%] with %integer% rows to %player%");
    

    registerNewEffect("Create World", uk.co.umbaska.GattSk.Effects.EffCreateWorld.class, "create [a] new world [name[d]] %string%");
    
    registerNewEffect("Delete World", uk.co.umbaska.GattSk.Effects.EffDeleteWorld.class, "delete world %string%");
    
    registerNewEffect("Unload World", EffUnloadWorld.class, "unload world %string%");
    
    registerNewEffect("Load World", EffLoadWorld.class, "load world %string%");
    
    registerNewEffect("Create World from Folder", uk.co.umbaska.GattSk.Effects.EffCreateWorldFrom.class, "create world named %string% from [folder] %string%");
    


    registerNewEffect("Load Chunk", uk.co.umbaska.Misc.NotVersionAffected.Chunks.EffLoadChunk.class, "load chunk at %location%");
    registerNewEffect("Unload Chunk", uk.co.umbaska.Misc.NotVersionAffected.Chunks.EffUnloadChunk.class, "unload chunk at %location%");
    registerNewEffect("Generate Chunk", uk.co.umbaska.Misc.NotVersionAffected.Chunks.EffGenerateChunk.class, "generate chunk at %location%");
    registerNewEffect("Load Chunk", uk.co.umbaska.Misc.NotVersionAffected.Chunks.EffLoadChunkC.class, "load chunk at %chunk%");
    registerNewEffect("Unload Chunk", uk.co.umbaska.Misc.NotVersionAffected.Chunks.EffUnloadChunkC.class, "unload chunk at %chunk%");
    registerNewEffect("Generate Chunk", uk.co.umbaska.Misc.NotVersionAffected.Chunks.EffGenerateChunkC.class, "generate chunk at %chunk%");
    
    registerNewEffect("Update Inventory", uk.co.umbaska.GattSk.Effects.EffUpdateInventory.class, "update inventory of %player%");
    registerNewEffect("Reset Recipes", uk.co.umbaska.GattSk.Effects.EffResetRecipes.class, "reset all [server] recipes");
    
    registerNewEffect("Do Nothing?", EffNothing_MFG.class, "do nothing");
    

    registerNewEffect("Create Hologram", uk.co.umbaska.HologramBased.EffCreateHologram.class, "create [a ]new holo[gram] named %string%");
    registerNewEffect("Set Hologram Line", uk.co.umbaska.HologramBased.EffSetHoloLine.class, "set holo[gram] line %integer% of holo[gram] %string% to %string%");
    
    registerNewEffect("Add Hologram Line", EffAddHoloLine.class, "set lines of holo[gram] %string% to %strings%");
    registerNewEffect("Delete Hologram Line", uk.co.umbaska.HologramBased.EffDeleteHoloLine.class, "(remove|clear|delete) lines of holo[gram] %string%");
    registerNewEffect("Move Hologram", uk.co.umbaska.HologramBased.EffMoveHolo.class, "move holo[gram] %string% to %location%");
    registerNewEffect("Hologram Follow Entity", EffHoloFollow.class, "make holo[gram] %string% follow %entity%");
    registerNewEffect("Start Hologram", EffHoloStart.class, "start holo[gram] %string%");
    registerNewEffect("Stop Hologram", uk.co.umbaska.HologramBased.EffHoloStop.class, "stop holo[gram] %string%");
    registerNewEffect("Delete Hologram", EffDeleteHolo.class, "delete holo[gram] %string%");
    
    registerNewEffect("Create Following Hologram", EffCreateFollowGram.class, "create [a ]new following holo[gram] (to|that) follow[s] %entity% with [text] %strings%");
    
    registerNewEffect("Set Hologram Type", uk.co.umbaska.HologramBased.EffSetHoloType.class, "set holo[gram] type to (0¦wither skull[s]|1¦armor stand[s])");
    
    registerNewEffect("Break Block", uk.co.umbaska.Misc.NotVersionAffected.EffPlayerBreak.class, "make %player% break [block] at %location%");
    registerNewEffect("Break Block w/ Tool", uk.co.umbaska.Misc.NotVersionAffected.EffPlayerBreakTool.class, "make %player% break [block] at %location% using [tool] %itemstack%");
    
    registerNewEffect("New Board - Simple Scoreboard", uk.co.umbaska.GattSk.Effects.SimpleScoreboards.EffNewSimpleScoreboard.class, "create [a] new simple scoreboard [named] %string%");
    registerNewEffect("Set Slot - Simple Scoreboard", uk.co.umbaska.GattSk.Effects.SimpleScoreboards.EffSetSlot.class, "set slot %number% of simple [score][board] %string% to %string%");
    registerNewEffect("Set Title - Simple Scoreboard", EffSetTitle.class, "set [display] title of simple [score][ ][board] %string% to %string%");
    registerNewEffect("Show Board - Simple Scoreboard", EffShowBoard.class, "set simple [score][board] of %players% to %string%");
    registerNewEffect("Clear Slot - Simple Scoreboard", uk.co.umbaska.GattSk.Effects.SimpleScoreboards.EffClearSlot.class, "clear slot %number% of simple [score][board] %string%");
    registerNewEffect("Delete Board - Simple Scoreboard", uk.co.umbaska.GattSk.Effects.SimpleScoreboards.EffDeleteBoard.class, "delete simple [score][ ][board] %string%");
    

    registerNewEffect("mon3?", uk.co.umbaska.Misc.NotVersionAffected.EffMon3.class, "mon3");
    registerNewEffect("rav3?", uk.co.umbaska.Misc.NotVersionAffected.EffRav3.class, "rav3");
    
    registerNewEffect("Set Cursor Item", uk.co.umbaska.GattSk.Effects.InventoryClick.EffSetCursorItem.class, "set cursor item to %itemstack%");
    registerNewEffect("Set Clicked Item", uk.co.umbaska.GattSk.Effects.InventoryClick.EffSetClickedItem.class, "set clicked item to %itemstack%");
    
    registerNewEffect("Set Items within Entity", uk.co.umbaska.Misc.NotVersionAffected.EffSetItemInEntity.class, "set [umbaska] item[s] within %entity% to %itemstack%");
    registerNewEffect("Remove all Potion Effects", uk.co.umbaska.Misc.NotVersionAffected.EffRemoveAllPotionEffects.class, "remove all potion effects from %entity%");
    
    registerNewEffect("Scatter Entities", uk.co.umbaska.Misc.NotVersionAffected.EffScatter.class, "scatter %entities% around %integer%(,| and) %integer% [in] [world] %world% (with|for) rad[ius] of %integer% [ignoring %-itemstacks%] [with delay of %-integer% [between teleports]]");
    
    registerNewEffect("Add Potion with no Particles", EffPotionEffectNoParticles.class, "apply [potion of] %potioneffecttype% [potion] [[[of] tier] %-number%] to %livingentities% for %timespan% (and|to) hide [particle[ effects]]");
    
    if (!Main.getInstance().getConfig().contains("force-generate-title-features")) {
      Main.getInstance().getConfig().set("force-generate-title-features", Boolean.valueOf(false));
      forceGenTitleFeatures = Boolean.valueOf(false);
    }
    registerNewEffect("Place Dropped Item", EffPlaceDroppedItem.class, "place %itemstack% at %location%");
    
    registerNewEffect("Set Attribute", uk.co.umbaska.Attributes.EffSetAttribute.class, "set [entity] attribute %attribute% of %entity% to %number%");
    
    registerNewEffect("Add Attribute Modifier", EffAddAttributeModifer.class, "add attribute modifier %attributemodifier% to %attribute% of %entity%");
    registerNewEffect("Remove Attribute Modifier", EffAddAttributeModifer.class, "remove attribute modifier %attributemodifier% from %attribute% of %entity%");
    
    registerNewEffect("Title", uk.co.umbaska.Misc.EffSendTitle.class, "send [a ]title from %string% and %string% to %players% for %number%, %number%, %number%");
    registerNewEffect("Action Bar", uk.co.umbaska.Misc.EffActionBar.class, "send [a ]action bar from %string% to %players%");
    registerNewEffect("Set Tab Footer and Header", uk.co.umbaska.Misc.EffTabList.class, "(send|set) [advanced ]header %string% (and|with) footer %string% (to|for) %players%");
    Main.getInstance().getLogger().info("It appears you might be using a 1.8 Build! I'm going to attempt to register some things related to it :)");
    registerNewEffect("Spawn Armor Stand", uk.co.umbaska.ArmourStands.EffSpawnArmorStand.class, "[umbaska] spawn [a][n] (armour|armor) stand at %locations%");
    registerNewEffect("Trail Entity", uk.co.umbaska.Misc.EffTrailEntity.class, "[umbaska] trail %entities% with [%number% of ]%particleenum%[:%number%] [[ with] data %number%] [[(with|and)] secondary data %number%]");
    
    registerNewEffect("Play Entity Effect", uk.co.umbaska.Misc.NotVersionAffected.EffPlayEntityEffect.class, "play [entity] [effect] %entityeffect% (for|on|at) %entity%");
    
    Main.getInstance().getLogger().info("[Umbaska > SkQuery] Attempting to register new Spawn Particle Effect.");
    registerNewEffect("Better Particle", uk.co.umbaska.Replacers.EffParticle.class, "[(1.8|Umbaska|skquery isnt updated)] (summon|play|create|activate|spawn) %number% [of] %particleenum%[:%number%] [offset (at|by|from) %number%, %number%(,| and) %number%] at %locations% (to|for) %players% [[ with] data %number%] [[(with|and)] secondary data %number%]");
    registerNewEffect("Better Particle All", EffParticleAll.class, "[(1.8|Umbaska|skquery isnt updated)] (summon|play|create|activate|spawn) %number% [of] %particleenum%[:%number%] [offset (at|by|from) %number%, %number%(,| and) %number%] at %locations% [[ with] data %number%] [[(with|and)] secondary data %number%]");
    registerNewEffect("Better Effect", uk.co.umbaska.Replacers.EffBukkitEffect.class, "(summon|play|create|activate|spawn) [bukkit] [effect] %bukkiteffect% at %locations% to %players% [[with] [data] %integer%] [[(with|and)] secondary data %integer%]");
    registerNewEffect("Better Effect All", EffBukkitEffectAll.class, "(summon|play|create|activate|spawn) [bukkit] [effect] %bukkiteffect% at %locations% [[with] [data] %integer%] [[(with|and)] secondary data %integer%]");
    

    if (Main.usingUmbaskaCord.booleanValue()) {
      registerNewEffect("Set Bungee Server Icon", uk.co.umbaska.UmbaskaCord.EffSetBungeeIcon.class, "set bungee server icon to [url] %string%");
      registerNewEffect("Set Bungee MOTD", uk.co.umbaska.UmbaskaCord.EffSetBungeeMOTD.class, "set bungee motd to %string%");
      registerNewEffect("Set Bungee Player List", uk.co.umbaska.UmbaskaCord.EffSetBungeePlayerList.class, "set bungee player list to %string%");
      registerNewEffect("Send Bungee Title", uk.co.umbaska.UmbaskaCord.EffSendBungeeTitle.class, "send [a ] bungee[ ][cord] title from %string% and %string% to %string% for %number%, %number%, %number%");
      registerNewEffect("Send Bungee Title - All Players", uk.co.umbaska.UmbaskaCord.EffSendBungeeTitleAll.class, "send [a ] bungee[ ][cord] title from %string% and %string% to (all|every(body| player)) for %number%, %number%, %number%");
    }
    

    if (use_bungee.booleanValue()) {
      registerNewEffect("Change Server", uk.co.umbaska.Bungee.EffChangeServer.class, "send %player% to [server] %string%");
      registerNewEffect("Bungee Kick Player", uk.co.umbaska.Bungee.EffKickBungeePlayer.class, "bungee[ ][cord] kick %string% (due to|for) [reason] %string%");
      registerNewEffect("Bungee Message", EffSendBungeeMsg.class, "bungee[ ][cord] (send|tell|message) %string% to %string%");
      registerNewEffect("Bungee Broadcast / Alert", EffBungeeBroadcast.class, "bungee[ ][cord] (broadcast|[send ]alert)%string%");
    }
    


    registerNewEffect("Register Player", uk.co.umbaska.ProtocolLib.FakePlayer.EffRegisterNewFakePlayer.class, "register new fake player [with id] %string%");
    registerNewEffect("Set Display Name Fake player", uk.co.umbaska.ProtocolLib.FakePlayer.EffSetDisplayedName.class, "set displayed name of fake player %string% to %string%");
    registerNewEffect("Set location fake player", uk.co.umbaska.ProtocolLib.FakePlayer.EffSetLocation.class, "set location of fake player %string% to %location%");
    registerNewEffect("Set Skin fake player", EffSetSkin.class, "set skin of fake player %string% to %string%");
    


    registerNewEffect("Umbaska JSON", uk.co.umbaska.JSON.EffSendJson.class, "[jsonmessage] (send|message) [umbaska] %umbjsonmessage% to %players%");
    
    registerNewEffect("Set Result Slot", EffSetResultSlot.class, "set result slot of %player% to %itemstack%");
    
    
    
    registerNewEffect("1.9 - Shoot Shulker Bullet", EffShootShulkerBullet.class, "make %entity% (shoot|launch) shulker bullet at %entity%");
    
    registerNewEffect("Area Effect Cloud - Spawn Area Effect Cloud", EffSpawnAreaEffectCloud.class, "spawn [a[n]] area effect cloud at %location%");
    registerNewEffect("Area Effect Cloud - Add Potion", EffAddPotionToCloud.class, "add %umbpotioneffect% to [area][ ][effect][ ][cloud] %entity%");
    registerNewEffect("Area Effect Cloud - Remove Potion", EffRemovePotionFromCloud.class, "remove %umbpotioneffect% from [area][ ][effect][ ][cloud] %entity%");
    


    registerNewEffect("Boss Bar - Add Player to Boss Bar", EffAddPlayerToBossBar.class, "add %player% to [boss][ ][bar] %bossbar%");
    registerNewEffect("Boss Bar - Remove Player from Boss Bar", EffRemovePlayerFromBossBar.class, "remove %player% from [boss][ ][bar]%bossbar%");
    registerNewEffect("Boss Bar - Remove all players from Boss bar", EffRemoveAllPlayersFromBar.class, "remove all players from [boss][ ][bar]%bossbar%");
    registerNewEffect("Boss Bar - Show Bar", uk.co.umbaska.BossBars.EffShowBossBar.class, "show [boss][ ][bar] %bossbar%");
    registerNewEffect("Boss Bar - Hide Bar", uk.co.umbaska.BossBars.EffHideBossBar.class, "hide [boss][ ][bar] %bossbar%");
    registerNewEffect("Boss Bar - Add Flag", uk.co.umbaska.BossBars.EffAddFlagToBar.class, "add %barflag% to [boss][ ][bar] %bossbar%");
  }

public static String getVersion() {
	return version;
}

public static void setVersion(String version) {
	Effects.version = version;
}
}
