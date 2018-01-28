package uk.co.umbaska.Towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.event.Event;







public class ExprPlotPrice
  extends SimpleExpression<Double>
{
  private Expression<Location> location;
  
  public Class<? extends Double> getReturnType()
  {
    return Double.class;
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
    return "return plot price";
  }
  
  @Nullable
  protected Double[] get(Event arg0)
  {
    Location l = (Location)this.location.getSingle(arg0);
    if (l == null) {
      return null;
    }
    
    Double s = Double.valueOf(TownyUniverse.getTownBlock(l).getPlotPrice());
    
    return new Double[] { s };
  }
}
