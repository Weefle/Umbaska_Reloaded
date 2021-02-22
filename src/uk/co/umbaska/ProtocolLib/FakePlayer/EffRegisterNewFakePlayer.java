package uk.co.umbaska.ProtocolLib.FakePlayer;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.ProtocolLib.FakePlayerTracker;



public class EffRegisterNewFakePlayer
  extends Effect
{
  private Expression<String> name;
  
  protected void execute(Event event)
  {
    String p = (String)this.name.getSingle(event);
    if (p == null) {
      return;
    }
    FakePlayerTracker.registerNewPlayer(p);
  }
  


  public String toString(Event event, boolean b)
  {
    return "Register new Fake Player";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = (Expression<String>) expressions[0];
    return true;
  }
}
