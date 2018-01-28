package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.FreezeListener;

public class ExprFreeze extends SimplePropertyExpression<Player, Boolean>
{
  public Boolean convert(Player ent)
  {
    if (ent == null)
      return null;
    return Main.freezeListener.isFrozen(ent);
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player ent = (Player)getExpr().getSingle(e);
    if (ent == null)
      return;
    Boolean b = (Boolean)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      Main.freezeListener.setFreezeState(ent, b);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
    return null;
  }
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  

  protected String getPropertyName()
  {
    return "Player Frozen State";
  }
}
