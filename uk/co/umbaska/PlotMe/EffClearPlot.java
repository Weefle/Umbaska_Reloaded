package uk.co.umbaska.PlotMe;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.worldcretornica.plotme.PlotManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;







public class EffClearPlot
  extends Effect
{
  private Expression<String> plot;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    


    String pl = (String)this.plot.getSingle(event);
    World w = (World)this.world.getSingle(event);
    if (pl == null)
      return;
    if (w == null) {
      return;
    }
    if (PlotManager.isValidId(pl)) {
      Location b = PlotManager.getPlotBottomLoc(w, pl);
      Location t = PlotManager.getPlotTopLoc(w, pl);
      PlotManager.clear(b, t);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Clear a plot";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.plot = expressions[0];
    this.world = expressions[1];
    return true;
  }
}
