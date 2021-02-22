package uk.co.umbaska.PlotMe;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.IWorld;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;







public class ExprGetOwner
  extends SimpleExpression<String>
{
  private Expression<String> plot;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.plot = (Expression<String>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    


    String plot = (String)this.plot.getSingle(arg0);
    
    if (plot == null) {
      return null;
    }
    
    if (!PlotMeCoreManager.getInstance().isValidId((IWorld) Bukkit.getWorld(PlotMeCoreManager.getInstance().getPlotById(plot, plot).getWorld()), plot)) {
      return new String[] { null };
    }
    Plot p = PlotMeCoreManager.getInstance().getPlotById(plot, plot);
    String owner = p.getOwner();
    return new String[] { owner };
  }
}
