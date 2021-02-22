package uk.co.umbaska.Towny;

import java.text.SimpleDateFormat;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;








public class ExprRDLastOnline
  extends SimpleExpression<String>
{
  public static final SimpleDateFormat lastOnlineFormat = new SimpleDateFormat("MMMMM dd '@' HH:mm");
  
  private Expression<Player> player;
  
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
    this.player = (Expression<Player>) args[0];
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
    Resident r = null;
    try {
      r = TownyUniverse.getDataSource().getResident(p);
    } catch (NotRegisteredException e) {
      e.printStackTrace();
    }
    
    String s = lastOnlineFormat.format(Long.valueOf(r.getLastOnline()));
    
    return new String[] { s };
  }
}
