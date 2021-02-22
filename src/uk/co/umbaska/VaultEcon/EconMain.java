package uk.co.umbaska.VaultEcon;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class EconMain
  implements Listener
{
  private static Plugin p;
  private static EconMain instance;
  private Economy econ;
  private String version = "1.0";
  private FileConfiguration customConfig = null;
  private File customConfigFile = null;
  
  private VaultHandler handler;
  
  private String currencySymbol;
  private Double startingbalance;
  private Double minBalance;
  private Double maxbalance;
  
  public EconMain(Plugin p)
  {
    EconMain.p = p;
    this.setHandler(new VaultHandler(this));
    Bukkit.getPluginManager().registerEvents(this, p);
    p.getDataFolder().mkdirs();
    File file = new File(p.getDataFolder() + "/Econ/base.yml");
    Boolean created = Boolean.valueOf(false);
    try {
      created = Boolean.valueOf(file.createNewFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!created.booleanValue()) {
      p.getLogger().warning("Could not create Base.YML within the Users Folder. Oops.");
    }
    
    if (this.customConfigFile == null) {
      this.customConfigFile = new File(p.getDataFolder() + "/Econ/config.yml");
    }
    this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
    if (!this.customConfig.getString("economy-version").equalsIgnoreCase(this.version)) {
      p.getLogger().warning("Economy Config is out of date. Recreating it.");
      try
      {
        Reader defConfigStream = new InputStreamReader(p.getResource("Econ/config.yml"), "UTF8");
        if (defConfigStream != null) {
          YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
          this.customConfig.setDefaults(defConfig);
        }
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      saveConfig();
    }
    
    this.startingbalance = Double.valueOf(this.customConfig.getDouble("starting-balance"));
    this.minBalance = Double.valueOf(this.customConfig.getDouble("minimum-balance-allowed"));
    this.maxbalance = Double.valueOf(this.customConfig.getDouble("maximum-balance-allowed"));
    
    if (this.maxbalance.doubleValue() == -1.0D) {
      this.maxbalance = Double.valueOf(Double.POSITIVE_INFINITY);
    }
    if (this.minBalance.doubleValue() == -1.0D) {
      this.minBalance = Double.valueOf(Double.NEGATIVE_INFINITY);
    }
    

    if (!created.booleanValue()) {
      p.getLogger().warning("Could not create Base.YML within the Users Folder. Oops.");
    }
    if (!setupEconomy()) {
      p.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { "Umbaska Vault Economy Hook" }));
      return;
    }
  }
  
  @EventHandler
  public void onJoinSetup(PlayerJoinEvent e) {
    setupPlayerData(e.getPlayer());
  }
  
  public Double getMinBalance() {
    return this.minBalance;
  }
  
  public Double getMaxbalance() {
    return this.maxbalance;
  }
  
  public Double getStartingbalance() {
    return this.startingbalance;
  }
  
  public String getCurrencySymbol() {
    return this.currencySymbol;
  }
  
  public static Plugin getP() {
    return p;
  }
  
  public static EconMain getInstance() {
    return instance;
  }
  
  public Economy getEcon() {
    return this.econ;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  private void saveConfig() {
    try {
      this.customConfig.save(this.customConfigFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private boolean setupEconomy()
  {
    if (p.getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = p.getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    this.econ = ((Economy)rsp.getProvider());
    return this.econ != null;
  }
  
  private void setupPlayerData(Player p) {
    new EconPlayer(p);
  }
  
  public EconPlayer getPlayer(UUID uuid) {
    OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
    return EconPlayer.getEconPlayer(op);
  }
  
  public EconPlayer getPlayer(OfflinePlayer op) {
    return EconPlayer.getEconPlayer(op);
  }
  
  public EconPlayer getAccount(String playerName, String uuid) {
    return getPlayer(UUID.fromString(uuid));
  }

public VaultHandler getHandler() {
	return handler;
}

public void setHandler(VaultHandler handler) {
	this.handler = handler;
}
}
