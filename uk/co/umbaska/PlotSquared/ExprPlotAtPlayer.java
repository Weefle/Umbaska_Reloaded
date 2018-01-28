package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotPlayer;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;







public class ExprPlotAtPlayer
  extends SimpleExpression<String>
{
  private Expression<Player> player;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle() {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return plot id at player";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    Player player = (Player)this.player.getSingle(arg0);
    if (player == null) {
      return null;
    }
    
    String plot = PlotPlayer.get(player.toString()).getCurrentPlot().getId().toString();
    if (plot == null) {
      return null;
    }
    
    return new String[] { plot };
  }
}
