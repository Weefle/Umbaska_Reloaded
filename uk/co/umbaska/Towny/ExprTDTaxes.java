package uk.co.umbaska.Towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import javax.annotation.Nullable;
import org.bukkit.event.Event;







public class ExprTDTaxes
  extends SimpleExpression<Double>
{
  private Expression<String> town;
  
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
    this.town = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return taxes of a town";
  }
  
  @Nullable
  protected Double[] get(Event arg0)
  {
    String t = (String)this.town.getSingle(arg0);
    Town tw = null;
    try {
      tw = TownyUniverse.getDataSource().getTown(t);
    } catch (NotRegisteredException e) {
      e.printStackTrace();
    }
    
    if (tw == null) {
      return null;
    }
    
    Double i = Double.valueOf(tw.getTaxes());
    
    return new Double[] { i };
  }
}
