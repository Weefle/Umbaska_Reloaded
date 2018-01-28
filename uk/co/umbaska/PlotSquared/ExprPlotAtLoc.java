package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import javax.annotation.Nullable;
import org.bukkit.event.Event;








public class ExprPlotAtLoc
  extends SimpleExpression<String>
{
  private Expression<org.bukkit.Location> location;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.location = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return plot id at location";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    org.bukkit.Location location = (org.bukkit.Location)this.location.getSingle(arg0);
    
    if (location == null) {
      return null;
    }
    com.intellectualcrafters.plot.object.Location loc = new com.intellectualcrafters.plot.object.Location();
    loc.setWorld(location.getWorld().toString());
    loc.setX((int)location.getX());
    loc.setY((int)location.getY());
    loc.setZ((int)location.getZ());
    loc.setPitch(location.getPitch());
    loc.setYaw(location.getYaw());
    String plot = Plot.getPlot(loc).getId().toString();
    
    return new String[] { plot };
  }
}
