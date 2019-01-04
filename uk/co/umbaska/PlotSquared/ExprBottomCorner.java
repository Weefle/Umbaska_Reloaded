package uk.co.umbaska.PlotSquared;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.util.MainUtil;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;







public class ExprBottomCorner
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
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.plot = (Expression<String>) args[0];
    this.world = (Expression<World>) args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  

  @Nullable
  protected org.bukkit.Location[] get(Event arg0)
  {
    String plot = (String)this.plot.getSingle(arg0);
    World w = (World)this.world.getSingle(arg0);
    
    if (plot == null)
      return null;
    if (w == null) {
      return null;
    }
    
    PlotId plotid = PlotId.fromString(plot);
    org.bukkit.Location out = (org.bukkit.Location)MainUtil.getPlotBottomLocAbs(w.toString(), plotid).toBukkitLocation();
    return new org.bukkit.Location[] { out };
  }
}
