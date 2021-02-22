package uk.co.umbaska.VaultEcon;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class EconPlayer
{
  private static HashMap<OfflinePlayer, EconPlayer> econPlayerHashMap = new HashMap<>();
  private OfflinePlayer player;
  
  public static EconPlayer getEconPlayer(OfflinePlayer p) { if (econPlayerHashMap.containsKey(p)) {
      return (EconPlayer)econPlayerHashMap.get(p);
    }
    return new EconPlayer(p);
  }
  


  private FileConfiguration customConfig = null;
  private File customConfigFile = null;
  private Double balance;
  
  public EconPlayer(Player p)
  {
    new EconPlayer(p);
  }
  
  public EconPlayer(OfflinePlayer p) {
    this.setPlayer(p);
    econPlayerHashMap.put(p, this);
    
    if (this.customConfigFile == null) {
      this.customConfigFile = new File(EconMain.getP().getDataFolder() + "/Econ/" + p.getUniqueId().toString() + ".yml");
    }
    this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
    this.balance = EconMain.getInstance().getStartingbalance();
    
    saveConfig();
  }
  
  private void saveConfig() {
    try {
      this.customConfig.save(this.customConfigFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public Double getBalance() {
    return this.balance;
  }
  
  public Double addBalance(Double amt) {
    if (getBalance().doubleValue() + amt.doubleValue() > EconMain.getInstance().getMaxbalance().doubleValue()) {
      this.balance = EconMain.getInstance().getMaxbalance();
    } else {
      this.balance = Double.valueOf(getBalance().doubleValue() + amt.doubleValue());
    }
    saveData();
    return getBalance();
  }
  
  public Boolean removeBalance(Double amt) {
    if (getBalance().doubleValue() - amt.doubleValue() < EconMain.getInstance().getMinBalance().doubleValue()) {
      this.balance = EconMain.getInstance().getMinBalance();
      return Boolean.valueOf(false);
    }
    this.balance = Double.valueOf(getBalance().doubleValue() - amt.doubleValue());
    
    saveData();
    return Boolean.valueOf(true);
  }
  
  public Boolean has(Double amt) {
    return Boolean.valueOf(getBalance().doubleValue() >= amt.doubleValue());
  }
  
  public void saveData() {
    this.customConfig.set("balance", this.balance);
    saveConfig();
  }

public OfflinePlayer getPlayer() {
	return player;
}

public void setPlayer(OfflinePlayer player) {
	this.player = player;
}
}
