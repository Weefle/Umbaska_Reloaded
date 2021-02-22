package uk.co.umbaska.Factions;

import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprFactionAtLocation
  extends SimpleExpression<Faction>
{
  private Expression<Location> location;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.location = (Expression<Location>) exprs[0];
    return true;
  }
  
  protected Faction[] get(Event e)
  {
    Location loc = (Location)this.location.getSingle(e);
    Faction faction = BoardColl.get().getFactionAt(PS.valueOf(loc));
    
    return new Faction[] { faction };
  }
  

  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends Faction> getReturnType()
  {
    return Faction.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "faction ally list";
  }
}
