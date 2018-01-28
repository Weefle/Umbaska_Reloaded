package uk.co.umbaska.Towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;





public class EffSetPlotOwner
  extends Effect
{
  private Expression<Location> location;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    String p = ((Player)this.player.getSingle(event)).toString();
    if (l == null)
      return;
    if (p == null) {
      return;
    }
    Resident r = null;
    try {
      r = TownyUniverse.getDataSource().getResident(p);
    } catch (NotRegisteredException e) {
      e.printStackTrace();
    }
    TownyUniverse.getTownBlock(l).setResident(r);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set a plot owner";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[0];
    this.player = expressions[1];
    return true;
  }
}
