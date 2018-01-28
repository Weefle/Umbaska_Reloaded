package uk.co.umbaska.Misc.NotVersionAffected.Chunks;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Event;







public class EffGenerateChunk
  extends Effect
{
  private Expression<Location> location;
  private Expression<Chunk> chunk;
  
  protected void execute(Event event)
  {
    ((Location)this.location.getSingle(event)).getChunk().load(true);
  }
  

  public String toString(Event event, boolean b)
  {
    return "generate chunk";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[0];
    return true;
  }
}
