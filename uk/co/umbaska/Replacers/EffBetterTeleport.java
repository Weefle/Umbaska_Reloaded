package uk.co.umbaska.Replacers;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;








public class EffBetterTeleport
  extends Effect
{
  private Expression<Entity> ent;
  private Expression<Location> to;
  
  protected void execute(Event event) {}
  
  public String toString(Event event, boolean b)
  {
    return "Umbaska Better Teleport (to fix vehicles)";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ent = expressions[0];
    this.to = expressions[1];
    return false;
  }
}
