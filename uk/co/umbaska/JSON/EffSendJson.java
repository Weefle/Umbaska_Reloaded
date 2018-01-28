package uk.co.umbaska.JSON;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class EffSendJson
  extends Effect
{
  private Expression<JSONMessage> json;
  private Expression<Player> players;
  
  protected void execute(Event event)
  {
    JSONMessage j = (JSONMessage)this.json.getSingle(event);
    if (j == null) {
      return;
    }
    j.send((Player[])this.players.getAll(event));
  }
  
  public String toString(Event event, boolean b)
  {
    return "json";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.json = expressions[0];
    this.players = expressions[1];
    return true;
  }
}
