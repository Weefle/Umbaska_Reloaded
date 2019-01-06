package uk.co.umbaska.LargeSk.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("Disable all plugins")
@Syntaxes({"disable all plugins", "disable every plugin"})
public class EffDisableAllPlugins
  extends UmbaskaEffect
{
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  protected void execute(Event arg0)
  {
    Bukkit.getPluginManager().disablePlugins();
  }
}
