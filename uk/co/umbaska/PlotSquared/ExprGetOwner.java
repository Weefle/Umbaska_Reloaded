package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprGetOwner
  extends SimpleExpression<String>
{
  private Expression<String> plot;
  private Expression<World> world;
  
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
    this.plot = args[0];
    this.world = args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  
  @Nullable
  protected String[] get(Event arg0)
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
    Plot plot = Plot.getPlot(w.toString(), plotid);
    ArrayList<String> ownedPlots = new ArrayList();
    for (UUID owner : plot.getOwners()) {
      ownedPlots.add(Bukkit.getServer().getPlayer(owner).toString());
    }
    String[] out = new String[ownedPlots.size()];
    out = (String[])ownedPlots.toArray(out);
    
    return out;
  }
}
