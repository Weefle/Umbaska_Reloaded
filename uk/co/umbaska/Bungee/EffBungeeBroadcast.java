package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;

public class EffBungeeBroadcast extends ch.njol.skript.lang.Effect
{
  private Expression<String> msg;
  
  protected void execute(Event event)
  {
    String message = (String)this.msg.getSingle(event);
    for (String server : Main.messenger.cache.allServers) {
      for (String ps : (java.util.List)Main.messenger.cache.playersOnlineServer.get(server)) {
        Main.messenger.sendMsgToPlayer(ps, message);
      }
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Change server";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.msg = expressions[0];
    return true;
  }
}
