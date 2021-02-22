package uk.co.umbaska.Dynmap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;






public class EffSetVisOfPlayer
  extends Effect
{
  private Expression<Player> player;
  private Expression<Boolean> vis;
  
  protected void execute(Event event)
  {
    Player p = (Player)this.player.getSingle(event);
    Boolean b = (Boolean)this.vis.getSingle(event);
    if (p == null) {
      return;
    }
    Main.api.setPlayerVisiblity(p, b.booleanValue());
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set a players visibility on Dynmap";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    this.vis = (Expression<Boolean>) expressions[1];
    return true;
  }
}
