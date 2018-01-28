package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;

public class EffSendBungeeMsg extends ch.njol.skript.lang.Effect
{
  private Expression<String> player;
  private Expression<String> msg;
  
  protected void execute(Event event)
  {
    String s = (String)this.msg.getSingle(event);
    for (String p : (String[])this.player.getAll(event)) {
      Main.messenger.sendMsgToPlayer(p, s);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Change server";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[1];
    this.msg = expressions[0];
    return true;
  }
}
