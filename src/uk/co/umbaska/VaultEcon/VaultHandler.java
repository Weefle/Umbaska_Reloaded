package uk.co.umbaska.VaultEcon;

import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class VaultHandler implements Economy
{
  private EconMain econMain;
  
  public VaultHandler(EconMain econMain)
  {
    this.econMain = econMain;
  }
  
  public boolean isEnabled()
  {
    return this.econMain != null;
  }
  
  public String getName()
  {
    return "EconManager";
  }
  
  public String format(double amount)
  {
    Double dbl = Double.valueOf(amount);
    return dbl.toString();
  }
  
  public String currencyNameSingular()
  {
    return this.econMain.getCurrencySymbol();
  }
  
  public String currencyNamePlural()
  {
    return this.econMain.getCurrencySymbol();
  }
  
  public double getBalance(String playerName)
  {
    return getAccountBalance(playerName, null);
  }
  
  public double getBalance(OfflinePlayer offlinePlayer)
  {
    return getAccountBalance(offlinePlayer.getName(), offlinePlayer.getUniqueId().toString());
  }
  
  private double getAccountBalance(String playerName, String uuid)
  {
    EconPlayer account = this.econMain.getAccount(playerName, uuid);
    if (account == null) {
      return 0.0D;
    }
    return account.getBalance().doubleValue();
  }
  
  public EconomyResponse withdrawPlayer(String playerName, double amount)
  {
    return withdraw(playerName, null, amount);
  }
  
  public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount)
  {
    return withdraw(offlinePlayer.getName(), offlinePlayer.getUniqueId().toString(), amount);
  }
  
  private EconomyResponse withdraw(String playerName, String uuid, double amount)
  {
    if (amount < 0.0D) {
      return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
    }
    EconPlayer account = this.econMain.getAccount(playerName, uuid);
    if (account == null) {
      return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Account doesn't exist");
    }
    if (account.has(Double.valueOf(amount)).booleanValue())
    {
      account.removeBalance(Double.valueOf(amount));
      
      return new EconomyResponse(amount, account.getBalance().doubleValue(), EconomyResponse.ResponseType.SUCCESS, "");
    }
    return new EconomyResponse(0.0D, account.getBalance().doubleValue(), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
  }
  
  public EconomyResponse depositPlayer(String playerName, double amount)
  {
    return deposit(playerName, null, amount);
  }
  
  public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount)
  {
    return deposit(offlinePlayer.getName(), offlinePlayer.getUniqueId().toString(), amount);
  }
  
  private EconomyResponse deposit(String playerName, String uuid, double amount)
  {
    if (amount < 0.0D) {
      return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
    }
    EconPlayer account = this.econMain.getAccount(playerName, uuid);
    if (account == null) {
      return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Account doesn't exist");
    }
    account.addBalance(Double.valueOf(amount));
    
    return new EconomyResponse(amount, account.getBalance().doubleValue(), EconomyResponse.ResponseType.SUCCESS, "");
  }
  
  public boolean has(String playerName, double amount)
  {
    return getBalance(playerName) >= amount;
  }
  
  public boolean has(OfflinePlayer offlinePlayer, double amount)
  {
    return getBalance(offlinePlayer) >= amount;
  }
  
  public EconomyResponse createBank(String name, String player)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse createBank(String name, OfflinePlayer offlinePlayer)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse deleteBank(String name)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse bankHas(String name, double amount)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse bankWithdraw(String name, double amount)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse bankDeposit(String name, double amount)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse isBankOwner(String name, String playerName)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse isBankOwner(String name, OfflinePlayer offlinePlayer)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse isBankMember(String name, String playerName)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse isBankMember(String name, OfflinePlayer offlinePlayer)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public EconomyResponse bankBalance(String name)
  {
    return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Umbaska Economy does not support bank accounts!");
  }
  
  public java.util.List<String> getBanks()
  {
    return new java.util.ArrayList<>();
  }
  
  public boolean hasBankSupport()
  {
    return false;
  }
  
  public boolean hasAccount(String playerName)
  {
    return true;
  }
  
  public boolean hasAccount(OfflinePlayer offlinePlayer)
  {
    return true;
  }
  
  public boolean createPlayerAccount(String playerName)
  {
    return createAccount(playerName, null);
  }
  
  public boolean createPlayerAccount(OfflinePlayer offlinePlayer)
  {
    return createAccount(offlinePlayer.getName(), offlinePlayer.getUniqueId().toString());
  }
  
  private boolean createAccount(String playerName, String uuid)
  {
    if (hasAccount(playerName, uuid)) {
      return false;
    }
    this.econMain.getAccount(playerName, uuid);
    return true;
  }
  
  public int fractionalDigits()
  {
    return -1;
  }
  
  public boolean hasAccount(String playerName, String worldName)
  {
    return hasAccount(playerName);
  }
  
  public boolean hasAccount(OfflinePlayer offlinePlayer, String worldName)
  {
    return hasAccount(offlinePlayer);
  }
  
  public double getBalance(String playerName, String worldName)
  {
    return getBalance(playerName);
  }
  
  public double getBalance(OfflinePlayer offlinePlayer, String worldName)
  {
    return getBalance(offlinePlayer);
  }
  
  public boolean has(String playerName, String worldName, double amount)
  {
    return has(playerName, amount);
  }
  
  public boolean has(OfflinePlayer offlinePlayer, String worldName, double amount)
  {
    return has(offlinePlayer, amount);
  }
  
  public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount)
  {
    return withdrawPlayer(playerName, amount);
  }
  
  public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String worldName, double amount)
  {
    return withdrawPlayer(offlinePlayer, amount);
  }
  
  public EconomyResponse depositPlayer(String playerName, String worldName, double amount)
  {
    return depositPlayer(playerName, amount);
  }
  
  public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String worldName, double amount)
  {
    return depositPlayer(offlinePlayer, amount);
  }
  
  public boolean createPlayerAccount(String playerName, String worldName)
  {
    return createPlayerAccount(playerName);
  }
  
  public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String worldName)
  {
    return createPlayerAccount(offlinePlayer);
  }
}
