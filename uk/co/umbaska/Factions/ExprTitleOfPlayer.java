package uk.co.umbaska.Factions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprTitleOfPlayer
  extends SimplePropertyExpression<Player, String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  

  public String convert(Player player)
  {
    MPlayer mplayer = MPlayer.get(player);
    return mplayer.getTitle();
  }
  
  protected String getPropertyName()
  {
    return "faction title";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
    Player player = (Player)getExpr().getSingle(e);
    MPlayer mplayer = MPlayer.get(player);
    if (player == null)
      return;
    String title = (String)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      mplayer.setTitle(title);
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    return null;
  }
}
