package uk.co.umbaska.Misc.UM2_0;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;






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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.pl = (Expression<String>) expressions[0];
    return true;
  }
}
