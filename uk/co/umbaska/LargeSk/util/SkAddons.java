package uk.co.umbaska.LargeSk.util;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class SkAddons
{
  public static Runnable logAddons()
  {
    new Runnable()
    {
      public void run()
      {
        String list = "";
        Integer num = Integer.valueOf(0);
        for (Iterator i$ = Skript.getAddons().iterator(); i$.hasNext();)
        {
          addon = (SkriptAddon)i$.next();
          localInteger1 = num;Integer localInteger2 = num = Integer.valueOf(num.intValue() + 1);
          list = list + addon.getName() + ", ";
        }
        SkriptAddon addon;
        Integer localInteger1;
        Plugin skumb = Bukkit.getServer().getPluginManager().getPlugin("Umbaska");
        if (skumb != null)
        {
          addon = num;localInteger1 = num = Integer.valueOf(num.intValue() + 1);
          list = list + skumb.getName() + ", ";
        }
        list = list.substring(0, list.length() - 2);
        if (num.intValue() > 1) {
          Xlog.logInfo("You are using " + num + " addons for Skript. A list of them: " + list);
        } else {
          Xlog.logInfo("You are using only " + num + " addon for Skript. And the name of it is " + list);
        }
      }
    };
  }
}
