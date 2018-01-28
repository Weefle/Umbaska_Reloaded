package uk.co.umbaska.Factions;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;


public class EffUnClaimLand
  extends Effect
{
  private Expression<Location> location;
  
  protected void execute(Event event)
  {
    Location location = (Location)this.location.getSingle(event);
    
    if (location == null) {
      return;
    }
    Chunk chunk = location.getWorld().getChunkAt(location);
    BoardColl.get().setFactionAt(PS.valueOf(chunk), FactionColl.get().getByName("Wilderness"));
  }
  

  public String toString(Event event, boolean b)
  {
    return "faction unclaim location";
  }
  


  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[0];
    return true;
  }
}
