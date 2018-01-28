package uk.co.umbaska.PlotMe;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.worldcretornica.plotme.PlotManager;
import org.bukkit.World;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;







public class EffMovePlot
  extends Effect
{
  private Expression<String> plot1;
  private Expression<String> plot2;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    


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
    if ((PlotManager.isValidId(pl1)) && 
      (PlotManager.isValidId(pl2))) {
      PlotManager.movePlot(w, pl1, pl2);
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "Move a plot to another plot";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.plot1 = expressions[0];
    this.plot2 = expressions[1];
    this.world = expressions[2];
    return true;
  }
}
