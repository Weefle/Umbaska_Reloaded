package uk.co.umbaska.JSON;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffJSONFormatSlotRun
  extends Effect
{
  private Expression<Location> location;
  private Expression<Chunk> chunk;
  
  protected void execute(Event event)
  {
    if (this.location.getSingle(event) != null) {
      ((Location)this.location.getSingle(event)).getChunk().unload(true);
    }
    else if (this.chunk.getSingle(event) != null) {
      ((Chunk)this.chunk.getSingle(event)).unload(true);
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "load chunk";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.chunk = (Expression<Chunk>) expressions[0];
    this.location = (Expression<Location>) expressions[0];
    return true;
  }
}
