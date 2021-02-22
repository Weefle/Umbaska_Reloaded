package uk.co.umbaska.Misc.NotVersionAffected.Chunks;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffGenerateChunk
  extends Effect
{
  private Expression<Location> location;
  
  protected void execute(Event event)
  {
    ((Location)this.location.getSingle(event)).getChunk().load(true);
  }
  

  public String toString(Event event, boolean b)
  {
    return "generate chunk";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = (Expression<Location>) expressions[0];
    return true;
  }
}
