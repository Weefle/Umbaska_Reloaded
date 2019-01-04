package uk.co.umbaska.Factions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffKickPlayer
  extends Effect
{
  private Expression<Player> player;
  private Expression<Faction> faction;
  
  protected void execute(Event event)
  {
    Player player = (Player)this.player.getSingle(event);
    Faction fac = (Faction)this.faction.getSingle(event);
    if (player == null)
      return;
    if (fac == null) {
      return;
    }
    MPlayer mplayer = MPlayer.get(player);
    fac.getMPlayers().remove(mplayer);
    if (mplayer.getRole() == Rel.LEADER) {
      fac.promoteNewLeader();
    }
    fac.uninvite(mplayer);
    mplayer.resetFactionData();
  }
  

  public String toString(Event event, boolean b)
  {
    return "faction kick player";
  }
  


  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.faction = (Expression<Faction>) expressions[1];
    this.player = (Expression<Player>) expressions[0];
    return true;
  }
}
