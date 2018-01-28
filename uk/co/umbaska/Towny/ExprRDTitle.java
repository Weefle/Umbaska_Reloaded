package uk.co.umbaska.Towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;






public class ExprRDTitle
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
    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return last online time of player";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    String p = ((Player)this.player.getSingle(arg0)).getName();
    String out = null;
    try {
      out = TownyUniverse.getDataSource().getResident(p).getTitle();
    } catch (NotRegisteredException e) {
      e.printStackTrace();
    }
    
    if (out == null) {
      return null;
    }
    
    return new String[] { out };
  }
}
