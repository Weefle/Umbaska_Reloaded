package uk.co.umbaska.Misc.NotVersionAffected.Chunks;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Chunk;
import org.bukkit.event.Event;








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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.Chunk = expressions[0];
    return true;
  }
}
