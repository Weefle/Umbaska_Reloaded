package uk.co.umbaska.Misc.UM2_0;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;


public class EffLoadPlugin
  extends Effect
{
  private Expression<String> pl;
  
  protected void execute(Event event)
  {
    try
    {
      Bukkit.getPluginManager().loadPlugin(new File("/plugins/" + (String)this.pl.getSingle(event) + ".jar"));
    } catch (InvalidPluginException|InvalidDescriptionException e) {
      e.printStackTrace();
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "Load Plugin";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.pl = (Expression<String>) expressions[0];
    return true;
  }
}
