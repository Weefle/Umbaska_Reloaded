package uk.co.umbaska.Factions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.Invitation;
import com.massivecraft.factions.entity.MPlayer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;


public class EffInvitePlayer
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
    Invitation inv = new Invitation();
    fac.invite(mplayer.getId(), inv);
  }
  

  public String toString(Event event, boolean b)
  {
    return "faction invite player";
  }
  


  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.faction = (Expression<Faction>) expressions[1];
    this.player = (Expression<Player>) expressions[0];
    return true;
  }
}
