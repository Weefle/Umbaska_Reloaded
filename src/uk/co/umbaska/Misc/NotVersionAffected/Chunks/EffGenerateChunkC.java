package uk.co.umbaska.Misc.NotVersionAffected.Chunks;

import org.bukkit.Chunk;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;








public class EffGenerateChunkC
  extends Effect
{
  private Expression<Chunk> Chunk;
  
  protected void execute(Event event)
  {
    ((Chunk)this.Chunk.getSingle(event)).load(true);
  }
  

  public String toString(Event event, boolean b)
  {
    return "generate chunk";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.Chunk = (Expression<org.bukkit.Chunk>) expressions[0];
    return true;
  }
}
