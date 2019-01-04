package uk.co.umbaska.PlotSquared;

import org.bukkit.World;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.util.MainUtil;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffMovePlot
  extends Effect
{
  private Expression<String> plot1;
  private Expression<String> plot2;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
    String pl1 = (String)this.plot1.getSingle(event);
    String pl2 = (String)this.plot2.getSingle(event);
    World w = (World)this.world.getSingle(event);
    if (pl1 == null)
      return;
    if (pl2 == null)
      return;
    if (w == null) {
      return;
    }
    PlotId plotid1 = PlotId.fromString(pl1);
    Plot plot1 = Plot.getPlot(w.toString(), plotid1);
    
    PlotId plotid2 = PlotId.fromString(pl2);
    Plot plot2 = Plot.getPlot(w.toString(), plotid2);
    
    MainUtil.moveData(plot1, plot2, null);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Move a plot to another plot";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.plot1 = (Expression<String>) expressions[0];
    this.plot2 = (Expression<String>) expressions[1];
    this.world = (Expression<World>) expressions[2];
    return true;
  }
}
