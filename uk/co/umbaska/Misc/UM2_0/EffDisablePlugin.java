package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;






public class EffDisablePlugin
  extends Effect
{
  private Expression<String> pl;
  
  protected void execute(Event event)
  {
    Plugin p = Bukkit.getPluginManager().getPlugin((String)this.pl.getSingle(event));
    if (p != null) {
      Bukkit.getPluginManager().disablePlugin(p);
    }
  }
  
  public String toString(Event event, boolean b) {
    return "Disable Plugin";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.pl = expressions[0];
    return true;
  }
}
