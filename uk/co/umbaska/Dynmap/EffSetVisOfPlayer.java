package uk.co.umbaska.Dynmap;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.dynmap.DynmapAPI;
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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    this.vis = expressions[1];
    return true;
  }
}
