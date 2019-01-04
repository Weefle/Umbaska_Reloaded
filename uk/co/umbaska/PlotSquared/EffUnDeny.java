package uk.co.umbaska.PlotSquared;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffUnDeny
  extends Effect
{
  private Expression<String> plot;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    String pl = (String)this.plot.getSingle(event);
    Player p = (Player)this.player.getSingle(event);
    if (pl == null)
      return;
    if (p == null) {
      return;
    }
    PlotId plotid = PlotId.fromString(pl);
    Plot plot = Plot.getPlot(p.getWorld().toString(), plotid);
    plot.addDenied(p.getUniqueId());
  }
  


  public String toString(Event event, boolean b)
  {
    return "Allow a player to a plot";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.plot = (Expression<String>) expressions[0];
    this.player = (Expression<Player>) expressions[1];
    return true;
  }
}
