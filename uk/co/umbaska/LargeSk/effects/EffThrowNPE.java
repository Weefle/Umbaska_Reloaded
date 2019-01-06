package uk.co.umbaska.LargeSk.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("Throw a NullPointerException")
@Syntaxes({"throw [a] (npe|null[ ]pointer[ ]exception)"})
public class EffThrowNPE
  extends UmbaskaEffect
{
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  protected void execute(Event arg0)
  {
    throw new NullPointerException("Here you are");
  }
}
