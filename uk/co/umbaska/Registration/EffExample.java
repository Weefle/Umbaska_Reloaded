package uk.co.umbaska.Registration;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("--Change--")
@Syntaxes({"--Change--"})
@Dependency("--Change--")
@DontRegister
public class EffExample
  extends UmbaskaEffect
{
  private Expression<Object> obj;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.obj = (Expression<Object>) expressions[0];
    return true;
  }
  
  protected void execute(Event e)
  {
    if (this.obj == null) {
      return;
    }
  }
}
