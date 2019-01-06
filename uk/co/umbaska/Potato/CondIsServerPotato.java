package uk.co.umbaska.Potato;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.DocsIgnore;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaCondition;

@Name("If server is a Potato")
@Syntaxes({"[(this|the)] server is [(a|an|the)] [instance of] [[the] only (true|real)] potato"})
@DocsIgnore
public class CondIsServerPotato
  extends UmbaskaCondition
{
  public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
  
  public boolean check(Event e)
  {
    return PotatoUtil.getPotatoState();
  }
}
