package uk.co.umbaska.Bungee;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<String>) expressions[0];
    this.reason = (Expression<String>) expressions[1];
    return true;
  }
}
