package uk.co.umbaska.ProtocolLib.FakePlayer;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.ProtocolLib.FakePlayerTracker;



public class EffSetLocation
  extends Effect
{
  private Expression<String> name;
  private Expression<Location> loc;
  
  @SuppressWarnings("deprecation")
protected void execute(Event event)
  {
    String p = (String)this.name.getSingle(event);
    if (p == null) {
      return;
    }
    FakePlayerTracker.teleport(p, (Location)this.loc.getSingle(event));
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set User Skin";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = (Expression<String>) expressions[0];
    this.loc = (Expression<Location>) expressions[1];
    return true;
  }
}
