package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.util.MainUtil;
import javax.annotation.Nullable;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprTopCorner
  extends SimpleExpression<org.bukkit.Location>
{
  private Expression<String> plot;
  private Expression<World> world;
  
  public Class<? extends org.bukkit.Location> getReturnType()
  {
    return org.bukkit.Location.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.plot = args[0];
    this.world = args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  
  @Nullable
  protected org.bukkit.Location[] get(Event arg0)
  {
    String pl = (String)this.plot.getSingle(arg0);
    World w = (World)this.world.getSingle(arg0);
    if (pl == null) {
      return null;
    }
    if (w == null) {
      return null;
    }
    PlotId plotid = PlotId.fromString(pl);
    org.bukkit.Location out = (org.bukkit.Location)MainUtil.getPlotTopLocAbs(w.toString(), plotid).toBukkitLocation();
    return new org.bukkit.Location[] { out };
  }
}
