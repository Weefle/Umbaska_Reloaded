package uk.co.umbaska.Towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.event.Event;






public class ExprPlotOwner
  extends SimpleExpression<String>
{
  private Expression<Location> location;
  
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
    return "return plot owner";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    Location l = (Location)this.location.getSingle(arg0);
    if (l == null) {
      return null;
    }
    

    String s = null;
    try {
      s = TownyUniverse.getTownBlock(l).getResident().toString();
    } catch (NotRegisteredException e) {
      e.printStackTrace();
    }
    
    if (s == null) {
      return null;
    }
    
    return new String[] { s };
  }
}
