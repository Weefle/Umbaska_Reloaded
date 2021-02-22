package uk.co.umbaska.Bungee;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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
    return "Send message";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<String>) expressions[1];
    this.msg = (Expression<String>) expressions[0];
    return true;
  }
}
