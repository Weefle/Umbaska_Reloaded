package uk.co.umbaska.Factions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEnemiesOfFaction extends SimpleExpression<String>
{
  private Expression<Faction> faction;
  final Collection<Faction> factions = FactionColl.get().getAll();
  List<String> factionallies = new ArrayList<>();
  


  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.faction = (Expression<Faction>) exprs[0];
    return true;
  }
  
  protected String[] get(Event e)
  {
    this.factionallies.clear();
    Faction thefac = (Faction)this.faction.getSingle(e);
    for (Faction fac : this.factions) {
      if (fac != null) {
        try {
          if ((thefac.getRelationTo(fac) == Rel.ENEMY) && (fac.getName() != "SafeZone") && (fac.getName() != "WarZone"))
          {

            this.factionallies.add(fac.getName());
          }
        }
        catch (Exception e2) {}
      }
    }
    
    return (String[])this.factionallies.toArray(new String[this.factionallies.size()]);
  }
  

  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "faction ally list";
  }
}
