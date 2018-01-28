package uk.co.umbaska.Factions;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.massivecraft.factions.entity.Faction;
import org.bukkit.event.Event;


public class EffDisbandFaction
  extends Effect
{
  private Expression<Faction> faction;
  
  protected void execute(Event event)
  {
    Faction fac = (Faction)this.faction.getSingle(event);
    
    if (fac == null) {
      return;
    }
    fac.detach();
  }
  
  public String toString(Event event, boolean b)
  {
    return "disband faction";
  }
  


  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.faction = expressions[0];
    return true;
  }
}
