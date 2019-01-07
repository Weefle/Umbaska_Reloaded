package uk.co.umbaska.PlotSquared;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

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
    return false;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
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
    ArrayList<String> ownedPlots = new ArrayList();
    for (Iterator i$ = PS.get().getPlots().iterator(); i$.hasNext();)
    {
      plot = (Plot)i$.next();
      for (UUID owner : plot.getOwners()) {
        if (owner.equals(p.getUniqueId())) {
          ownedPlots.add(plot.getId().toString());
        }
      }
    }
    Plot plot;
    String[] out = new String[ownedPlots.size()];
    out = (String[])ownedPlots.toArray(out);
    
    return out;
  }
}
