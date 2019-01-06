package uk.co.umbaska.LargeSk.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import uk.co.umbaska.LargeSk.events.EvtPlayerChunkChange;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("On Chunk Change - New Chunk")
@Syntaxes({"new chunk"})
public class ExprNewChunk
  extends SimpleUmbaskaExpression<Chunk>
{
  public Class<? extends Chunk> getReturnType()
  {
    return Chunk.class;
  }
  
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerChunkChange.class))
    {
      Skript.error("You can only use the New Chunk expression inside Chunk Change event");
      return false;
    }
    return true;
  }
  
  @Nullable
  protected Chunk[] get(Event e)
  {
    EvtPlayerChunkChange event = (EvtPlayerChunkChange)e;
    return new Chunk[] { event.getTo() };
  }
}
