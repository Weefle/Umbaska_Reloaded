package uk.co.umbaska.Factions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.massivecraft.factions.entity.MPlayer;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprPowerboostOfPlayer
  extends SimplePropertyExpression<Player, Double>
{
  public Class<? extends Double> getReturnType()
  {
    return Double.class;
  }
  
  public Double convert(Player player)
  {
    MPlayer mplayer = MPlayer.get(player);
    return Double.valueOf(mplayer.getPowerBoost());
  }
  
  protected String getPropertyName()
  {
    return "faction power";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player player = (Player)getExpr().getSingle(e);
    MPlayer mplayer = MPlayer.get(player);
    if (player == null)
      return;
    double power = ((Number)delta[0]).doubleValue();
    if (mode == Changer.ChangeMode.SET) {
      mplayer.setPowerBoost(Double.valueOf(power));
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }
}
