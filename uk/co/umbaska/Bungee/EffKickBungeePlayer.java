package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;

public class EffKickBungeePlayer extends ch.njol.skript.lang.Effect
{
  private Expression<String> player;
  private Expression<String> reason;
  
  protected void execute(Event event)
  {
    String s = (String)this.reason.getSingle(event);
    Main.messenger.kickPlayer((String)this.player.getSingle(event), s);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Change server";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    this.reason = expressions[1];
    return true;
  }
}
