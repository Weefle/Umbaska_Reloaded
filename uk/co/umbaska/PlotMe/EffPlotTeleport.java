package uk.co.umbaska.PlotMe;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.PlotManager;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;






public class EffPlotTeleport
  extends Effect
{
  private Expression<Player> ply;
  private Expression<String> plot;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    


    Player p = (Player)this.ply.getSingle(event);
    String pl = (String)this.plot.getSingle(event);
    World w = (World)this.world.getSingle(event);
    if (p == null)
      return;
    if (pl == null)
      return;
    if (w == null) {
      w = p.getWorld();
    }
    if (PlotManager.isValidId(pl)) {
      Location bottom = PlotManager.getPlotBottomLoc(w, pl);
      Location top = PlotManager.getPlotBottomLoc(w, pl);
      p.teleport(new Location(w, bottom.getX() + (top.getBlockX() - bottom.getBlockX()) / 2, PlotManager.getMap(w).RoadHeight + 2, bottom.getZ() - 2.0D));
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Teleport player to plot";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ply = (Expression<Player>) expressions[0];
    this.plot = (Expression<String>) expressions[1];
    this.world = (Expression<World>) expressions[2];
    return true;
  }
}
