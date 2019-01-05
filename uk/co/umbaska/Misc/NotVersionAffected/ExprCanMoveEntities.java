package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprCanMoveEntities extends SimplePropertyExpression<Player, Boolean>
{
  public Boolean convert(Player ent)
  {
    if (ent == null)
      return null;
    return Boolean.valueOf(ent.spigot().getCollidesWithEntities());
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player ent = (Player)getExpr().getSingle(e);
    if (ent == null)
      return;
    Boolean b = (Boolean)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ent.spigot().setCollidesWithEntities(b.booleanValue());
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
    return "Collides With Entities";
  }
}
