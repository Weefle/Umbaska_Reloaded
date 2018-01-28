package uk.co.umbaska.Managers;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import com.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import com.plotsquared.bukkit.events.PlayerLeavePlotEvent;
import com.plotsquared.bukkit.events.PlayerPlotHelperEvent;
import com.plotsquared.bukkit.events.PlayerPlotTrustedEvent;
import com.plotsquared.bukkit.events.PlayerTeleportToPlotEvent;
import com.plotsquared.bukkit.events.PlotClearEvent;
import com.plotsquared.bukkit.events.PlotDeleteEvent;
import com.plotsquared.bukkit.events.PlotFlagAddEvent;
import com.plotsquared.bukkit.events.PlotFlagRemoveEvent;
import com.plotsquared.bukkit.events.PlotMergeEvent;
import com.plotsquared.bukkit.events.PlotRateEvent;
import com.plotsquared.bukkit.events.PlotUnlinkEvent;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import uk.co.umbaska.Factions.EvtFactionCreateEvent;
import uk.co.umbaska.Factions.EvtFactionDescriptionChangeEvent;
import uk.co.umbaska.Factions.EvtFactionDisbandEvent;
import uk.co.umbaska.Factions.EvtFactionNameChangeEvent;
import uk.co.umbaska.Main;
import uk.co.umbaska.Misc.EvtCropGrowEvent;
import uk.co.umbaska.Misc.EvtHeadRotateEvent;
import uk.co.umbaska.Misc.EvtRepairEvent;
import uk.co.umbaska.Misc.EvtTeleportCallEvent;
import uk.co.umbaska.VariableChangeEvent;
import uk.co.umbaska.WorldGuard.EvtRegionEnterEvent;

public class Register
{
  private static String version = null;
  public static Boolean debugInfo = Boolean.valueOf(Main.getInstance().getConfig().getBoolean("debug_info"));
  public static HashMap<String, Object> placeholderMap = new HashMap();
  

  public static HashMap<String, java.util.List<String>> effectList = new HashMap();
  public static HashMap<String, java.util.List<String>> expressionList = new HashMap();
  public static HashMap<String, java.util.List<String>> simpleexpressionList = new HashMap();
  
  public static HashMap<String, Object> pluginMap = new HashMap();
  
  public static String getVersion() {
    if (version == null) {
      version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toUpperCase();
    }
    return version;
  }
  
  public static Class getClass(String classname)
  {
    Class cls = null;
    try {
      cls = Class.forName("uk.co.umbaska." + classname + "_" + version);
    } catch (ClassNotFoundException e) {
      if (debugInfo.booleanValue()) {
        org.bukkit.Bukkit.getLogger().info("Umbaska »»» Can't Find Class for " + classname + " for version " + version);
      }
    }
    return cls;
  }
  
  public static void registerAll()
  {
    Effects.runRegister();
    Expressions.runRegister();
    Enums.runRegister();
    
    Skript.registerEvent("Portal Spawn", SimpleEvent.class, org.bukkit.event.entity.EntityCreatePortalEvent.class, new String[] { "on portal spawn" });
    
    Skript.registerEvent("Armour Equip", SimpleEvent.class, ca.thederpygolems.armorequip.ArmourEquipEvent.class, new String[] { "armo[u]r equip" });
    new uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler();
    Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Misc.HeadRotateEvent(), Main.plugin);
    
    Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Misc.RepairEvent(), Main.plugin);
    
    Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Misc.TeleportCallEvent(), Main.plugin);
    
    Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Misc.CropGrowEvent(), Main.plugin);
    
    Skript.registerEvent("Slime Split", SimpleEvent.class, org.bukkit.event.entity.SlimeSplitEvent.class, new String[] { "slime split" });
    Skript.registerEvent("Potion Splash", SimpleEvent.class, org.bukkit.event.entity.PotionSplashEvent.class, new String[] { "potion splash" });
    Skript.registerEvent("Sheep Wool Regrow", SimpleEvent.class, org.bukkit.event.entity.SheepRegrowWoolEvent.class, new String[] { "sheep wool regrow" });
    Skript.registerEvent("Leash Entity", SimpleEvent.class, org.bukkit.event.entity.PlayerLeashEntityEvent.class, new String[] { "[player ]leash" });
    Skript.registerEvent("Unleash Entity", SimpleEvent.class, org.bukkit.event.entity.EntityUnleashEvent.class, new String[] { "unleash" });
    Skript.registerEvent("Brew", SimpleEvent.class, BrewEvent.class, new String[] { "brew" });
    





















    org.bukkit.plugin.Plugin pl = org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("PlotSquared");
    if (pl != null) {
      Skript.registerEvent("PlotSquared Enter", SimpleEvent.class, PlayerEnterPlotEvent.class, new String[] { "plot enter" });
      EventValues.registerEventValue(PlayerEnterPlotEvent.class, String.class, new Getter()
      {
        public String get(PlayerEnterPlotEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Leave", SimpleEvent.class, PlayerLeavePlotEvent.class, new String[] { "plot leave" });
      EventValues.registerEventValue(PlayerLeavePlotEvent.class, String.class, new Getter()
      {
        public String get(PlayerLeavePlotEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Denied", SimpleEvent.class, com.plotsquared.bukkit.events.PlayerPlotDeniedEvent.class, new String[] { "plot denied" });
      EventValues.registerEventValue(com.plotsquared.bukkit.events.PlayerPlotDeniedEvent.class, String.class, new Getter()
      {
        public String get(com.plotsquared.bukkit.events.PlayerPlotDeniedEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Helper", SimpleEvent.class, PlayerPlotHelperEvent.class, new String[] { "plot helper" });
      EventValues.registerEventValue(PlayerPlotHelperEvent.class, String.class, new Getter()
      {
        public String get(PlayerPlotHelperEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Trusted", SimpleEvent.class, PlayerPlotTrustedEvent.class, new String[] { "plot trusted" });
      EventValues.registerEventValue(PlayerPlotTrustedEvent.class, String.class, new Getter()
      {
        public String get(PlayerPlotTrustedEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Teleport", SimpleEvent.class, PlayerTeleportToPlotEvent.class, new String[] { "plot (teleport|tp)" });
      EventValues.registerEventValue(PlayerTeleportToPlotEvent.class, String.class, new Getter()
      {
        public String get(PlayerTeleportToPlotEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Clear", SimpleEvent.class, PlotClearEvent.class, new String[] { "plot clear" });
      EventValues.registerEventValue(PlotClearEvent.class, String.class, new Getter()
      {
        public String get(PlotClearEvent event) { return event.getPlotId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Delete", SimpleEvent.class, PlotDeleteEvent.class, new String[] { "plot delete" });
      EventValues.registerEventValue(PlotDeleteEvent.class, String.class, new Getter()
      {
        public String get(PlotDeleteEvent event) { return event.getPlotId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Flag Add", SimpleEvent.class, PlotFlagAddEvent.class, new String[] { "plot flag add" });
      EventValues.registerEventValue(PlotFlagAddEvent.class, String.class, new Getter()
      {
        public String get(PlotFlagAddEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Flag Remove", SimpleEvent.class, PlotFlagRemoveEvent.class, new String[] { "plot flag remove" });
      EventValues.registerEventValue(PlotFlagRemoveEvent.class, String.class, new Getter()
      {
        public String get(PlotFlagRemoveEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Merge", SimpleEvent.class, PlotMergeEvent.class, new String[] { "plot merge" });
      EventValues.registerEventValue(PlotMergeEvent.class, ArrayList.class, new Getter()
      {
        public ArrayList<PlotId> get(PlotMergeEvent event) { return event.getPlots(); } }, 0);
      


      Skript.registerEvent("PlotSquared Rate", SimpleEvent.class, PlotRateEvent.class, new String[] { "plot rate" });
      EventValues.registerEventValue(PlotRateEvent.class, String.class, new Getter()
      {
        public String get(PlotRateEvent event) { return event.getPlot().getId().toString(); } }, 0);
      


      Skript.registerEvent("PlotSquared Unlink", SimpleEvent.class, PlotUnlinkEvent.class, new String[] { "plot unlink" });
      EventValues.registerEventValue(PlotUnlinkEvent.class, ArrayList.class, new Getter()
      {
        public ArrayList<PlotId> get(PlotUnlinkEvent event) { return event.getPlots(); } }, 0);
      


      Skript.registerEvent("PlotSquared Claim", SimpleEvent.class, PlayerClaimPlotEvent.class, new String[] { "plot claim" });
      EventValues.registerEventValue(PlayerClaimPlotEvent.class, String.class, new Getter()
      {
        public String get(PlayerClaimPlotEvent event) { return event.getPlot().getId().toString(); } }, 0);
    }
    


    EventValues.registerEventValue(BrewEvent.class, org.bukkit.block.Block.class, new Getter()
    {
      public org.bukkit.block.Block get(BrewEvent event) { return event.getBlock(); } }, 0);
    

    EventValues.registerEventValue(BrewEvent.class, ItemStack[].class, new Getter()
    {
      public ItemStack[] get(BrewEvent event) { return event.getContents().getContents(); } }, 0);
    

    Skript.registerEvent("Enchant Prepare", SimpleEvent.class, PrepareItemEnchantEvent.class, new String[] { "[item ]enchant prepare" });
    
    EventValues.registerEventValue(PrepareItemEnchantEvent.class, ItemStack.class, new Getter()
    {
      public ItemStack get(PrepareItemEnchantEvent event) { return event.getItem(); } }, 0);
    

    EventValues.registerEventValue(PrepareItemEnchantEvent.class, Player.class, new Getter()
    {
      public Player get(PrepareItemEnchantEvent event) { return event.getEnchanter(); } }, 0);
    

    Skript.registerEvent("Enchant", SimpleEvent.class, EnchantItemEvent.class, new String[] { "[umbaska ]enchant" });
    EventValues.registerEventValue(EnchantItemEvent.class, ItemStack.class, new Getter()
    {
      public ItemStack get(EnchantItemEvent event) { return event.getItem(); } }, 0);
    

    EventValues.registerEventValue(EnchantItemEvent.class, Player.class, new Getter()
    {
      public Player get(EnchantItemEvent event) { return event.getEnchanter(); } }, 0);
    


    if (Main.getInstance().getConfig().getBoolean("enable_variable_change_event")) {
      Skript.registerEvent("Variable Change Event", SimpleEvent.class, VariableChangeEvent.class, new String[] { "variable change" });
      EventValues.registerEventValue(VariableChangeEvent.class, String.class, new Getter()
      {
        public String get(VariableChangeEvent event) { return event.getVariable(); } }, 0);
      

      EventValues.registerEventValue(VariableChangeEvent.class, Object.class, new Getter()
      {
        public Object get(VariableChangeEvent event) { return event.getNewValue(); } }, 0);
    }
    

    Skript.registerEvent("Head Rotate", SimpleEvent.class, EvtHeadRotateEvent.class, new String[] { "head (rotat(e|ion)|move[ment])" });
    


    EventValues.registerEventValue(EvtHeadRotateEvent.class, Player.class, new Getter()
    {

      public Player get(EvtHeadRotateEvent event) {
        return event.getPlayer(); } }, 0);
    

    Skript.registerEvent("Repair", SimpleEvent.class, EvtRepairEvent.class, new String[] { "repair" });
    

    EventValues.registerEventValue(EvtRepairEvent.class, Player.class, new Getter()
    {

      public Player get(EvtRepairEvent event) {
        return event.getPlayer(); } }, 0);
    


    EventValues.registerEventValue(EvtRepairEvent.class, ItemStack.class, new Getter()
    {

      public ItemStack get(EvtRepairEvent event) {
        return event.getItem(); } }, 0);
    

    Skript.registerEvent("Teleport Call", SimpleEvent.class, EvtTeleportCallEvent.class, new String[] { "[player] teleport call" });
    


    EventValues.registerEventValue(EvtTeleportCallEvent.class, Player.class, new Getter()
    {

      public Player get(EvtTeleportCallEvent event) {
        return event.getPlayer(); } }, 0);
    

    Skript.registerEvent("Crop Grow", SimpleEvent.class, EvtCropGrowEvent.class, new String[] { "[umbaska] crop grow" });
    


    EventValues.registerEventValue(EvtCropGrowEvent.class, ch.njol.skript.util.BlockStateBlock.class, new Getter()
    {


      public ch.njol.skript.util.BlockStateBlock get(EvtCropGrowEvent event) {
        return event.getBlock(); } }, 0);
    

    Skript.registerEvent("Achievement Award", SimpleEvent.class, org.bukkit.event.player.PlayerAchievementAwardedEvent.class, new String[] { "achievement[ get]" });
    Skript.registerEvent("Note Play", SimpleEvent.class, org.bukkit.event.block.NotePlayEvent.class, new String[] { "note play" });
    Skript.registerEvent("Inventory Open", SimpleEvent.class, org.bukkit.event.inventory.InventoryOpenEvent.class, new String[] { "inventory open" });
    Skript.registerEvent("Health Regen", SimpleEvent.class, org.bukkit.event.entity.EntityRegainHealthEvent.class, new String[] { "[entity] health reg(ain|en)" });
    
    org.bukkit.Bukkit.getLogger().info("Registering Entity Interact Event");
    Skript.registerEvent("Entity Interact", SimpleEvent.class, PlayerInteractAtEntityEvent.class, new String[] { "([entity] interact|armo[u]r stand (right[ ]click|interact))" });
    Skript.registerEvent("Entity Interact", SimpleEvent.class, PlayerInteractAtEntityEvent.class, new String[] { "([entity] interact|armo[u]r stand (right[ ]click|interact))" });
    Skript.registerEvent("Entity Interact", SimpleEvent.class, PlayerInteractAtEntityEvent.class, new String[] { "([entity] interact|armo[u]r stand (right[ ]click|interact))" });
    Skript.registerEvent("Entity Interact", SimpleEvent.class, PlayerInteractAtEntityEvent.class, new String[] { "([entity] interact|armo[u]r stand (right[ ]click|interact))" });
    Skript.registerEvent("Entity Interact", SimpleEvent.class, PlayerInteractAtEntityEvent.class, new String[] { "([entity] interact|armo[u]r stand (right[ ]click|interact))" });
    EventValues.registerEventValue(PlayerInteractAtEntityEvent.class, org.bukkit.util.Vector.class, new Getter()
    {
      public org.bukkit.util.Vector get(PlayerInteractAtEntityEvent event) { return event.getClickedPosition(); } }, 0);
    










    if (!Main.disableSkRambled.booleanValue())
    {
      pl = org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("Factions");
      if (pl != null) {
        Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Factions.FactionDescriptionChangeEvent(), Main.plugin);
        
        Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Factions.FactionCreateEvent(), Main.plugin);
        
        Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Factions.FactionNameChangeEvent(), Main.plugin);
        
        Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.Factions.FactionDisbandEvent(), Main.plugin);
        

        Skript.registerEvent("Faction Description Change", SimpleEvent.class, EvtFactionDescriptionChangeEvent.class, new String[] { "faction description change" });
        


        EventValues.registerEventValue(EvtFactionDescriptionChangeEvent.class, Player.class, new Getter()
        {



          public Player get(EvtFactionDescriptionChangeEvent evtFactionDescriptionChangeEvent) {
            return evtFactionDescriptionChangeEvent.getPlayer(); } }, 0);
        


        EventValues.registerEventValue(EvtFactionDescriptionChangeEvent.class, com.massivecraft.factions.entity.Faction.class, new Getter()
        {



          public com.massivecraft.factions.entity.Faction get(EvtFactionDescriptionChangeEvent evtFactionDescriptionChangeEvent) {
            return evtFactionDescriptionChangeEvent.getFac(); } }, 0);
        

        EventValues.registerEventValue(EvtFactionDescriptionChangeEvent.class, String.class, new Getter()
        {



          public String get(EvtFactionDescriptionChangeEvent evtFactionDescriptionChangeEvent) {
            return evtFactionDescriptionChangeEvent.getNewDesc(); } }, 0);
        


        Skript.registerEvent("Faction Name Change", SimpleEvent.class, EvtFactionNameChangeEvent.class, new String[] { "faction name change" });
        


        EventValues.registerEventValue(EvtFactionNameChangeEvent.class, Player.class, new Getter()
        {


          public Player get(EvtFactionNameChangeEvent evtFactionNameChangeEvent) {
            return evtFactionNameChangeEvent.getPlayer(); } }, 0);
        


        EventValues.registerEventValue(EvtFactionNameChangeEvent.class, com.massivecraft.factions.entity.Faction.class, new Getter()
        {



          public com.massivecraft.factions.entity.Faction get(EvtFactionNameChangeEvent evtFactionNameChangeEvent) {
            return evtFactionNameChangeEvent.getFac(); } }, 0);
        

        Skript.registerEvent("Faction Create", SimpleEvent.class, EvtFactionCreateEvent.class, new String[] { "faction create" });
        

        EventValues.registerEventValue(EvtFactionCreateEvent.class, Player.class, new Getter()
        {


          public Player get(EvtFactionCreateEvent evtFactionCreateEvent) {
            return evtFactionCreateEvent.getPlayer(); } }, 0);
        


        EventValues.registerEventValue(EvtFactionCreateEvent.class, String.class, new Getter()
        {


          public String get(EvtFactionCreateEvent evtFactionCreateEvent) {
            return evtFactionCreateEvent.getFacName(); } }, 0);
        

        Skript.registerEvent("Faction Disband", SimpleEvent.class, EvtFactionDisbandEvent.class, new String[] { "faction disband" });
        

        EventValues.registerEventValue(EvtFactionDisbandEvent.class, Player.class, new Getter()
        {


          public Player get(EvtFactionDisbandEvent evtFactionDisbandEvent) {
            return evtFactionDisbandEvent.getPlayer(); } }, 0);
        


        EventValues.registerEventValue(EvtFactionDisbandEvent.class, String.class, new Getter()
        {


          public String get(EvtFactionDisbandEvent evtFactionDisbandEvent) {
            return evtFactionDisbandEvent.getFacName(); } }, 0);
      }
      



      pl = org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
      if (pl != null) {
        Main.plugin.getServer().getPluginManager().registerEvents(new uk.co.umbaska.WorldGuard.RegionEnterEvent(), Main.plugin);
        
        Skript.registerEvent("Region Enter", SimpleEvent.class, EvtRegionEnterEvent.class, new String[] { "[protected |protected]region enter" });
        


        EventValues.registerEventValue(EvtRegionEnterEvent.class, Player.class, new Getter()
        {

          public Player get(EvtRegionEnterEvent event) {
            return event.getPlayer(); } }, 0);
        

        EventValues.registerEventValue(EvtRegionEnterEvent.class, com.sk89q.worldguard.protection.regions.ProtectedRegion.class, new Getter()
        {


          public com.sk89q.worldguard.protection.regions.ProtectedRegion get(EvtRegionEnterEvent event) {
            return event.getRegion(); } }, 0);
      }
    }
  }
}
