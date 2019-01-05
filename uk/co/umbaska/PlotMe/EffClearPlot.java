package uk.co.umbaska.PlotMe;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;

import com.worldcretornica.plotme_core.ClearReason;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.ICommandSender;
import com.worldcretornica.plotme_core.api.IWorld;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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
    if (PlotMeCoreManager.getInstance().isValidId((IWorld) w, pl)) {
      ICommandSender sender = (ICommandSender) Bukkit.getOnlinePlayers();
      PlotMeCoreManager.getInstance().clear((IWorld) w, PlotMeCoreManager.getInstance().getPlotById(pl, pl), sender, ClearReason.Clear);
    }
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
