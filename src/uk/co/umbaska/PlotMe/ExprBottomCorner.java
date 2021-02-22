package uk.co.umbaska.PlotMe;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.IWorld;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;







public class ExprBottomCorner
  extends SimpleExpression<Location>
{
  private Expression<String> plot;
  private Expression<World> world;
  
  public Class<? extends Location> getReturnType()
  {
    return Location.class;
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
  protected Location[] get(Event arg0)
  {
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    


    String plot = (String)this.plot.getSingle(arg0);
    World w = (World)this.world.getSingle(arg0);
    
    if (plot == null)
      return null;
    if (w == null) {
      return null;
    }
    
    if (!PlotMeCoreManager.getInstance().isValidId((IWorld) w, plot)) {
      return new Location[] { null };
    }
    Location out = (Location) PlotMeCoreManager.getInstance().getPlotBottomLoc((IWorld) w, plot);
    return new Location[] { out };
  }
}
