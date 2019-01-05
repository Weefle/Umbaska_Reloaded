package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;









public class EffNothing_MFG
  extends Effect
{
  protected void execute(Event event)
  {
  }
  

  public String toString(Event event, boolean b)
  {
    return "this effect literally just returns. and does thing. :)";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
}
