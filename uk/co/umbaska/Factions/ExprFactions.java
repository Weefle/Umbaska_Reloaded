package uk.co.umbaska.Factions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

public class ExprFactions
  extends SimpleExpression<String>
{
  final Collection<Faction> factions = FactionColl.get().getAll();
  List<String> factionnames = new ArrayList();
  

  public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
  
  protected String[] get(Event e)
  {
    this.factionnames.clear();
    for (Faction fac : this.factions)
    {
      try
      {
        this.factionnames.add(fac.getName());
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    

    return (String[])this.factionnames.toArray(new String[this.factionnames.size()]);
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
    return "faction list";
  }
}
