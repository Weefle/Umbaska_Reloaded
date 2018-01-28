package uk.co.umbaska.ProtocolLib.FakePlayer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.event.Event;
import uk.co.umbaska.ProtocolLib.FakePlayerTracker;



public class EffMove
  extends Effect
{
  private Expression<String> name;
  private Expression<Location> loc;
  
  protected void execute(Event event)
  {
    String p = (String)this.name.getSingle(event);
    if (p == null) {
      return;
    }
    FakePlayerTracker.move(p, (Location)this.loc.getSingle(event));
  }
  

  public String toString(Event event, boolean b)
  {
    return "Move";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = expressions[0];
    this.loc = expressions[1];
    return true;
  }
}
