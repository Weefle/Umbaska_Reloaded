package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.Main;

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
