package uk.co.umbaska.PlotMe;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.worldcretornica.plotme.Plot;
import com.worldcretornica.plotme.PlotManager;
import java.util.HashMap;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;








public class ExprGetPlayerPlots
  extends SimpleExpression<String>
{
  private Expression<Player> player;
  
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
    if (!Main.warnPlotMeUse.booleanValue()) {
      Skript.error("PlotMe is no longer being updated! Due to this the PlotMe expressions and effects in Umbaska have become deprecated. It's suggested to upgrade to PlotSquared! There are currently no syntax changes between the two");
    }
    


    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    Player p = (Player)this.player.getSingle(arg0);
    
    if (p == null) {
      return null;
    }
    HashMap<String, Plot> plots = new HashMap();
    plots = PlotManager.getPlots(p);
    String out = plots.toString();
    
    out = out.replace("{", "");
    out = out.replace("}", "");
    return new String[] { out };
  }
}
