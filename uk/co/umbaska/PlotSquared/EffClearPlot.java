package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.util.MainUtil;
import org.bukkit.World;
import org.bukkit.event.Event;







public class EffClearPlot
  extends Effect
{
  private Expression<String> plot;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
    String pl = (String)this.plot.getSingle(event);
    World w = (World)this.world.getSingle(event);
    if (pl == null)
      return;
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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.plot = expressions[0];
    this.world = expressions[1];
    return true;
  }
}
