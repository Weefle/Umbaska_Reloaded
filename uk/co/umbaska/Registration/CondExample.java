package uk.co.umbaska.Registration;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("--Change--")
@Syntaxes({"--Change--"})
@Dependency("--Change--")
@DontRegister
public class CondExample
  extends UmbaskaCondition
{
  public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
  
  public boolean check(Event e)
  {
    boolean check = true;
    return check;
  }
}
