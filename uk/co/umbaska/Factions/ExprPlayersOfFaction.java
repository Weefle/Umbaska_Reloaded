package uk.co.umbaska.Factions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class ExprPlayersOfFaction
  extends SimpleExpression<Player>
{
  private Expression<Faction> faction;
  
  public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.faction = exprs[0];
    return true;
  }
  
  protected Player[] get(Event e)
  {
    List<Player> players = new ArrayList();
    Faction faction = (Faction)this.faction.getSingle(e);
    for (MPlayer p : faction.getMPlayers()) {
      players.add(p.getPlayer());
    }
    return (Player[])players.toArray();
  }
  

  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "faction player list";
  }
}
