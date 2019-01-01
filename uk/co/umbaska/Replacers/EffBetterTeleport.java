package uk.co.umbaska.Replacers;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;








public class EffBetterTeleport
  extends Effect
{
  private Expression<Entity> ent;
  private Expression<Location> to;
  
  protected void execute(Event event) {
	  
	  ent.getSingle(event).teleport(to.getSingle(event));
	  
  }
  
  public String toString(Event event, boolean b)
  {
    return "Umbaska Better Teleport (to fix vehicles)";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ent = (Expression<Entity>) expressions[0];
    this.to = (Expression<Location>) expressions[1];
    return false;
  }
}
