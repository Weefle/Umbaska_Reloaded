package uk.co.umbaska.ProtocolLib.FakePlayer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.ProtocolLib.FakePlayerTracker;


public class EffSetSkin
  extends Effect
{
  private Expression<String> name;
  private Expression<String> userskin;
  
  protected void execute(Event event)
  {
    String p = (String)this.name.getSingle(event);
    if (p == null) {
      return;
    }
    FakePlayerTracker.setSkin(p, (String)this.userskin.getSingle(event));
  }
  


  public String toString(Event event, boolean b)
  {
    return "Set User Skin";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = expressions[0];
    this.userskin = expressions[1];
    return true;
  }
}
