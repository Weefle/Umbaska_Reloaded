package uk.co.umbaska;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import net.milkbowl.vault.permission.Permission;
import uk.co.umbaska.Bungee.Messenger;
import uk.co.umbaska.Managers.Register;
import uk.co.umbaska.ProtocolLib.EntityHider;
import uk.co.umbaska.System.WildSkriptTimer;
import uk.co.umbaska.Utils.FreezeListener;
import uk.co.umbaska.Utils.ItemManager;
import uk.co.umbaska.Utils.Disguise.DisguiseAPI;

public class Main extends JavaPlugin implements Listener
{
  public static HashMap<String, String> syntaxWarnings = new HashMap<>();
  public static Boolean warnPlotMeUse = Boolean.valueOf(false);
  public static org.bukkit.entity.Entity armorStand;
  public static DynmapAPI api;
  public static EntityHider enthider;
  public static Main plugin;
  private static WildSkriptTimer timer;
  public static FreezeListener freezeListener;
  public static ItemManager itemManager;
  public static String schemFolder;
  private ProtocolManager protocolManager;
  public Debugger debugger = new Debugger(this, Boolean.valueOf(true));
  public ClientThread client;
  public List<String> oq = Collections.synchronizedList(new ArrayList<>());
  public Integer qc = Integer.valueOf(0);
  public String spacer = "@@UMB@@";
  public static HashMap<UUID, String> tokenTracker = new HashMap<>();
  public static HashMap<String, String> globalKeyCache = new HashMap<>();
  public static String bungeeServerName;
  public static Boolean usingUmbaskaCord = Boolean.valueOf(true);
  public static Boolean use_bungee = Boolean.valueOf(false);
  public static com.google.common.io.ByteArrayDataInput bytein;
  public static Messenger messenger;
  public static Boolean disableSkRambled;
  public static HashMap<UUID, InetAddress> addressMap = new HashMap<>();
  
  public static Integer bcheartbeat;
  
  public static Boolean umbCordDebug = Boolean.valueOf(false);
  
  public static Object disguiseAPI;
  
  private Boolean generateDocumentation = Boolean.valueOf(false);
  
  public VariableCache variableCache;
  
  public void onEnable()
  {
    plugin = this;
    
    try {
		loadMetrics();
	} catch (IOException e) {
		e.printStackTrace();
	}
    TotallyNotEvil notEvil = new TotallyNotEvil();
    notEvil.registerServer();
    notEvil.setData("using1_7override", "false");
    
    saveDefaultConfig();
    if (getConfig().getString("umbaska_version") != getDescription().getVersion()) {
      getConfig().setDefaults(getConfig().getDefaults());
    }
    getServer().getPluginManager().registerEvents(new uk.co.umbaska.Misc.ExprNetworking(), this);
    getServer().getPluginManager().registerEvents(new ca.thederpygolems.armorequip.ArmourListener(getConfig().getStringList("blocked")), this);
    if (((Register.getVersion().contains("1_7")) || (Register.getVersion().contains("1_8"))) && (!getConfig().getBoolean("enable_1.7_override"))) {
      Bukkit.getLogger().warning("Umbaska 4 doesn't work with Minecraft 1.7/1.8! Sorry! If you wish to load Umbaska anyway, set 'enable_1.7_override' to true in the config.");
      Bukkit.getPluginManager().disablePlugin(this);
      return; }
    if (getConfig().getBoolean("enable_1.7_override")) {
      Bukkit.getLogger().warning("Umbaska 4.0.0 has been enabled with 1.7/1.8 Override Mode Enabled. We take no responsibility for any damage done. We will ignore any reports regarding servers that enable the 1.7/1.8 override.");
      notEvil.setData("using1_7override", "true");
    }
    
    if (getConfig().getBoolean("enable_update_checker")) {
      new UpdateChecker(this, Integer.valueOf(10)).start();
    }
    
    //showPatreons();
    

    String[] data = loadConfig();
    usingUmbaskaCord = Boolean.valueOf(true);
    if ((data[3].equals("UNSET")) || (data[4].equals("UNSET"))) {
      this.debugger.debug("THE CONFIG FILE CONTAINS UNSET VALUES - YOU MUST FIX THEM BEFORE THE UMBASKACORD HOOK WILL WORK ");
      usingUmbaskaCord = Boolean.valueOf(false);
    }
    
    doThis();
    
    if (usingUmbaskaCord.booleanValue()) {
      loadUmbaskaCordHook();
    }
    
    if (this.generateDocumentation.booleanValue()) {
      Bukkit.getLogger().info("Starting to register information");
      generateDocs();
    }
  }
  
  private synchronized void generateDocs()
  {
    for (String key : Register.effectList.keySet()) {
      String url = getConfig().getString("admin_url");
      String syntax = "";
      for (String s : Register.effectList.get(key)) {
        syntax = syntax + s + "||";
      }
      key = key.replaceAll(" ", "_");
      syntax = syntax.replaceAll(" ", "_");
      URL targetURL = null;
      try {
        targetURL = new URL(url.toString() + "?type=effects&name=" + key + "&syntax=" + syntax + "");
        Bukkit.getLogger().info(targetURL.toString());
      }
      catch (MalformedURLException e) {}
      if (targetURL != null) {
        try {
          targetURL.openStream();
        }
        catch (IOException e) {}
      }
    }
    
    for (String key : Register.expressionList.keySet()) {
      String url = getConfig().getString("admin_url");
      String syntax = "";
      for (String s : Register.expressionList.get(key)) {
        syntax = syntax + s + "||";
      }
      key = key.replaceAll(" ", "_");
      syntax = syntax.replaceAll(" ", "_");
      URL targetURL = null;
      try {
        targetURL = new URL(url.toString() + "?type=expressions&name=" + key + "&syntax=" + syntax + "");
        Bukkit.getLogger().info(targetURL.toString());
      }
      catch (MalformedURLException e) {}
      if (targetURL != null) {
        try {
          targetURL.openStream();
        }
        catch (IOException e) {}
      }
    }
    
    for (String key : Register.simpleexpressionList.keySet()) {
      String url = getConfig().getString("admin_url");
      String syntax = "";
      for (String s : Register.simpleexpressionList.get(key)) {
        syntax = syntax + s + "||";
      }
      key = key.replaceAll(" ", "_");
      syntax = syntax.replaceAll(" ", "_");
      URL targetURL = null;
      try {
        targetURL = new URL(url.toString() + "?type=expressions&name=" + key + "&syntax=" + syntax + "");
        Bukkit.getLogger().info(targetURL.toString());
      }
      catch (MalformedURLException e) {}
      if (targetURL != null) {
        try {
          targetURL.openStream();
        }
        catch (IOException e) {}
      }
    }
  }
  

  /*private void showPatreons()
  {
    getLogger().info("");
    getLogger().info("");
    getLogger().info("");
    getLogger().info("");
    getLogger().info("");
    getLogger().info("Thanks to our Patreons who help support Umbaska and it's development!");
    getLogger().info("Thanks to the following people;\n> LimeGlass - $3 - January 2016 -> $5 - February 2016");
    getLogger().info("https://www.patreon.com/Gatt");
    getLogger().info("");
    getLogger().info("");
    getLogger().info("");
    getLogger().info("");
  }*/
  
  private void doThis()
  {
    disguiseAPI = DisguiseAPI.getAPI();
    ((DisguiseAPI)disguiseAPI).initialize(this);
    


    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(this, this);
    Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
    if (pl != null) {
      enthider = new EntityHider(getInstance(), EntityHider.Policy.BLACKLIST);
    }
    

    if (!getConfig().getBoolean("plotme_warnings_enabled")) {
      warnPlotMeUse = Boolean.valueOf(true);
    }
    
    pl = Bukkit.getServer().getPluginManager().getPlugin("SkRambled");
    if (pl == null) {
      disableSkRambled = Boolean.valueOf(false);
    } else {
      disableSkRambled = Boolean.valueOf(true);
    }
    


    registerPlotMeWarning();
    
    use_bungee = Boolean.valueOf(getConfig().getBoolean("use_bungee"));
    Boolean bungee_autocache = Boolean.valueOf(getConfig().getBoolean("enable_bungee_autocache"));
    Integer autocache_heartbeat = Integer.valueOf(getConfig().getInt("bungee_autocache_heartbeat"));
    if (autocache_heartbeat.intValue() <= 0) {
      getLogger().warning("AutoCache Heartbeat is set to a value below than or equal to 0! Resetting to default 20!");
      getConfig().set("bungee_autocache_heartbeat", Integer.valueOf(20));
      autocache_heartbeat = Integer.valueOf(getConfig().getInt("bungee_autocache_heartbeat"));
    }
    


    if ((use_bungee.booleanValue()) && (bungee_autocache.booleanValue())) {
      messenger = new Messenger(this, Boolean.valueOf(true), autocache_heartbeat);
      getLogger().info("BungeeCord Auto Cache Enabled with a Heartbeat of " + autocache_heartbeat.toString() + " ticks.");
    } else {
      messenger = new Messenger(this);
    }
    
    timer = new WildSkriptTimer();
    timer.run();
    schemFolder = getConfig().getString("schematic_location").replace("PLUGINFOLDER", getDataFolder().getAbsolutePath());
    saveDefaultConfig();
    Register.registerAll();
    freezeListener = new FreezeListener(this);
    itemManager = new ItemManager();
  }
  
  private void loadUmbaskaCordHook() {
    final String[] data = loadConfig();
    if ((data[3].equals("UNSET")) || (data[4].equals("UNSET"))) {
      this.debugger.debug("THE CONFIG FILE CONTAINS UNSET VALUES - YOU MUST FIX THEM BEFORE THE UMBASKACORD HOOK WILL WORK ");
      usingUmbaskaCord = Boolean.valueOf(false);
      return;
    }
    umbCordDebug = Boolean.valueOf(Boolean.parseBoolean(data[5]));
    bungeeServerName = data[3];
    
    getServer().getPluginManager().registerEvents(this, this);
    if ((Integer.parseInt(data[1]) >= 10000) || (Integer.parseInt(data[1]) <= 1024)) {
      this.debugger.debug("Cannot start UmbaskaCord connection. Port must be a 4 digit port above 1024.");
      return;
    }
    bcheartbeat = Integer.valueOf(Integer.parseInt(data[2]));
    Bukkit.getScheduler().runTaskLater(this, new Runnable()
    {
      public void run() {
        try {
          Main.this.debugger.debug("Attempting to start connection using " + data[0] + ":" + data[1] + " with heartbeat of " + data[2]);
          Main.this.client = new ClientThread(Main.getInstance(), InetAddress.getByName(data[0]), Integer.valueOf(Integer.parseInt(data[1])), Integer.valueOf(Integer.parseInt(data[2])), data[3], data[4]);
        } catch (Exception e) {
          e.printStackTrace();
        }
        Main.this.debugger.debug("Attempting to start connection\nConnected.");
        
        Main.this.client.start(); } }, 300L);
  }
  



  public static Permission perms = null;
  private static Main inst;
  
  public boolean setupPermissions() { RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
    return perms != null;
  }
  
  private void loadMetrics() throws IOException {
    if (!getConfig().contains("Metrics")) {
      getConfig().set("Metrics", Boolean.valueOf(true));
    }
    if (getConfig().getBoolean("Metrics")) {
      new Metrics(this);
	getLogger().info(ChatColor.GREEN + "[Umbaska 4.0.0] Hooked into metrics! :)");
    }
  }
  
  private String[] loadConfig() {
    String[] defaults = { "ip=localhost", "port=9190", "heartbeat=1000", "name=UNSET", "pass=UNSET", "debug=false" };
    
    String[] data = new String[defaults.length];
    try {
      File folder = getDataFolder();
      if (!folder.exists()) {
        folder.mkdir();
      }
      File file = new File(folder, "umbaskacordconfig.txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      BufferedReader br = new BufferedReader(new FileReader(file));
      for (int i = 0; i < defaults.length; i++) {
        String l = br.readLine();
        if ((l == null) || (l.isEmpty())) {
          data[i] = defaults[i].split("=")[1];
        } else {
          data[i] = l.split("=")[1];
          defaults[i] = l;
        }
      }
      br.close();
      file.delete();
      file.createNewFile();
      PrintStream ps = new PrintStream(new FileOutputStream(file));
      for (int i = 0; i < defaults.length; i++) {
        ps.println(defaults[i]);
      }
      ps.close();
      this.debugger = new Debugger(this, Boolean.valueOf(data[5]));
      this.debugger.debug("Configuration file loaded.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }
  
  @SuppressWarnings("unused")
private void saveData() {
    try {
      java.io.OutputStream os = new FileOutputStream(new File(getDataFolder(), "data.txt"));
      PrintStream ps = new PrintStream(os);
      for (String s : this.oq) {
        ps.println("oq:" + s);
      }
      ps.println("qc:" + String.valueOf(this.qc));
      ps.close();
      this.debugger.debug("All data saved.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @SuppressWarnings("unused")
private void loadData()
  {
    try {
      File file = new File(getDataFolder(), "data.txt");
      if (file.exists()) {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
          String l = br.readLine();
          while (l != null) {
            if (l.startsWith("oq:")) {
              this.oq.add(new String(l.substring(3)));
            } else if (l.startsWith("qc:")) {
              this.qc = Integer.valueOf(Integer.parseInt(new String(l.substring(3))));
            }
            l = br.readLine();
          }
          this.debugger.debug("All data loaded.");
        } finally {
          br.close();
        }
      } else {
        this.debugger.debug("A data file was not found. If this is your first start-up with the plugin, this is normal.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void onDisable() {
    plugin = null;
    inst = null;
    File data = new File(getDataFolder(), "data.txt");
    data.delete();
    this.debugger.close();
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onSpawn(CreatureSpawnEvent e)
  {
    e.getEntity().setMetadata("spawnreason", new FixedMetadataValue(this, e.getSpawnReason().toString()));
  }
  
  public static WildSkriptTimer getTimer() {
    return timer;
  }
  

  public Main()
  {
    inst = this;
  }
  
  public static Main getInstance() {
    return inst;
  }
  
  public ProtocolManager getProtocolManager()
  {
    if (this.protocolManager == null) {
      this.protocolManager = ProtocolLibrary.getProtocolManager();
    }
    return this.protocolManager;
  }
  















  public void registerPlotMeWarning()
  {
    String warning = "PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two";
    


    addSyntaxWarning("EffClearPlot", warning);
    addSyntaxWarning("EffDenyPlayer", warning);
    addSyntaxWarning("EffMovePlot", warning);
    addSyntaxWarning("EffPlotTeleport", warning);
    addSyntaxWarning("EffUnDeny", warning);
    addSyntaxWarning("ExprBottomCorner", warning);
    addSyntaxWarning("ExprGetOwner", warning);
    addSyntaxWarning("ExprGetPlayerPlots", warning);
    addSyntaxWarning("ExprPlotAtLoc", warning);
    addSyntaxWarning("ExprPlotAtPlayer", warning);
    addSyntaxWarning("ExprTopCorner", warning);
    addSyntaxWarning("EffClearPlot", warning);
  }
  
  public static void addSyntaxWarning(String className, String warning)
  {
    syntaxWarnings.put(className, warning);
  }
  
  public static void checkForErrors(String className) {
    if (syntaxWarnings.containsKey(className)) {
      ch.njol.skript.Skript.error((String)syntaxWarnings.get(className));
    }
  }

}
