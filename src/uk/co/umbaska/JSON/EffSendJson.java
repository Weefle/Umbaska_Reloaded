package uk.co.umbaska.JSON;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;


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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.json = (Expression<JSONMessage>) expressions[0];
    this.players = (Expression<Player>) expressions[1];
    return true;
  }
}
