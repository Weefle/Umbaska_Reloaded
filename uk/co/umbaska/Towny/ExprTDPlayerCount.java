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







public class ExprTDPlayerCount
  extends SimpleExpression<Integer>
{
  private Expression<String> town;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
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
    return "return count of players in a town";
  }
  
  @Nullable
  protected Integer[] get(Event arg0)
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
    
    Integer i = Integer.valueOf(tw.getNumResidents());
    
    return new Integer[] { i };
  }
}
