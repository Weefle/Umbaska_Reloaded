package uk.co.umbaska.PlotSquared;

import org.bukkit.World;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.util.MainUtil;
import com.worldcretornica.plotme_core.Plot;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffClearPlot
  extends Effect
{
  private Expression<String> plot;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
    String pl = (String)this.plot.getSingle(event);
    World w = (World)this.world.getSingle(event);
    if (pl == null) {
      return;
    }
    if (w == null) {
      return;
    }
    PlotId plotid = PlotId.fromString(pl);
    Plot plot = Plot.getPlot(w.toString(), plotid);
    MainUtil.clear(plot, false, null);
  }
  
  public String toString(Event event, boolean b)
  {
    return "Clear a plot";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.plot = (Expression<String>) expressions[0];
    this.world = (Expression<World>) expressions[1];
    return true;
  }
}
