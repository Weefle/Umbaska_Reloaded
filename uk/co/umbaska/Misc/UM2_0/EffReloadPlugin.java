package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import uk.co.umbaska.Main;


public class EffReloadPlugin
  extends Effect
{
  private Expression<String> pl;
  
  protected void execute(Event event)
  {
    Plugin p = Bukkit.getPluginManager().getPlugin((String)this.pl.getSingle(event));
    final String pl = (String)this.pl.getSingle(event);
    if (p != null) {
      Bukkit.getPluginManager().disablePlugin(p);
      Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable()
      {
        public void run() {
          try {
            Bukkit.getPluginManager().loadPlugin(new File("/plugins/" + pl + ".jar"));
          } catch (InvalidPluginException|InvalidDescriptionException e) {
            e.printStackTrace(); } } }, 60L);
    }
  }
  



  public String toString(Event event, boolean b)
  {
    return "Reload Plugin";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.pl = expressions[0];
    return true;
  }
}
