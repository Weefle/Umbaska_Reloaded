package uk.co.umbaska.Potato;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PotatoUtil
{
  public static boolean getPotatoState()
  {
    for (Player pl : Bukkit.getOnlinePlayers()) {
      if ((pl.getName().equals("Nicofisi")) || (pl.getName().equals("nfell2009")) || (StringUtils.containsIgnoreCase(pl.getName(), "bae")) || pl.getName().equals("Weefle")) {
        return true;
      }
    }
    return false;
  }
}
