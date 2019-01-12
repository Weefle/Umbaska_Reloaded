package uk.co.umbaska.LargeSk.expressions;

import javax.annotation.Nullable;

import org.bukkit.Chunk;
import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.LargeSk.events.EvtPlayerChunkChange;

@Syntaxes({"old chunk"})
public class ExprOldChunk
  extends SimpleExpression<Chunk>
{
  public Class<? extends Chunk> getReturnType()
  {
    return Chunk.class;
  }
  
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerChunkChange.class))
    {
      Skript.error("You can only use the Old Chunk expression inside Chunk Change event");
      return false;
    }
    return true;
  }
  
  @Nullable
  protected Chunk[] get(Event e)
  {
    EvtPlayerChunkChange event = (EvtPlayerChunkChange)e;
    return new Chunk[] { event.getFrom() };
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "On Chunk Change - Old Chunk";
}
}
