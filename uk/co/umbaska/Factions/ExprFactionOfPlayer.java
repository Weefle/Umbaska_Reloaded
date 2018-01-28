package uk.co.umbaska.Factions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprFactionOfPlayer
  extends SimplePropertyExpression<Player, Faction>
{
  public Class<? extends Faction> getReturnType()
  {
    return Faction.class;
  }
  
  public Faction convert(Player player)
  {
    MPlayer mplayer = MPlayer.get(player);
    return mplayer.getFaction();
  }
  
  protected String getPropertyName()
  {
    return "faction of player";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player player = (Player)getExpr().getSingle(e);
    MPlayer mplayer = MPlayer.get(player);
    if (player == null)
      return;
    Faction faction = (Faction)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      mplayer.setFaction(faction);
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Faction.class });
    return null;
  }
}
