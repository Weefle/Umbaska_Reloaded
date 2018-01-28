package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;









public class EffNothing_MFG
  extends Effect
{
  protected void execute(Event event)
  {
    String potato = "potato";
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
