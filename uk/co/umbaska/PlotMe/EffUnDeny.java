package uk.co.umbaska.PlotMe;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotManager;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;







public class EffUnDeny
  extends Effect
{
  private Expression<String> plot;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    



    String pl = (String)this.plot.getSingle(event);
    String p = ((Player)this.player.getSingle(event)).toString();
    if (pl == null)
      return;
    if (p == null) {
      return;
    }
    if (PlotManager.isValidId(pl)) {
      Plot plot = PlotManager.getPlotById(pl, pl);
      plot.removeDenied(p);
      plot.addAllowed(p);
    }
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
