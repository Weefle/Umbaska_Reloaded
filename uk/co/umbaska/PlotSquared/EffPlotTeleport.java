package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MainUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;








public class EffPlotTeleport
  extends Effect
{
  private Expression<Player> ply;
  private Expression<String> plot;
  private Expression<World> world;
  
  protected void execute(Event event)
  {
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
    PlotPlayer pp = PlotPlayer.get(p.getName());
    PlotId plotid = PlotId.fromString(pl);
    Plot plo = Plot.getPlot(w.toString(), plotid);
    com.intellectualcrafters.plot.object.Location loc = new com.intellectualcrafters.plot.object.Location();
    org.bukkit.Location location = p.getLocation();
    loc.setWorld(location.getWorld().toString());
    loc.setX((int)location.getX());
    loc.setY((int)location.getY());
    loc.setZ((int)location.getZ());
    loc.setPitch(location.getPitch());
    loc.setYaw(location.getYaw());
    MainUtil.teleportPlayer(pp, loc, plo);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Teleport player to plot";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ply = expressions[0];
    this.plot = expressions[1];
    this.world = expressions[2];
    return true;
  }
}
