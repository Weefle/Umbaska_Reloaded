package uk.co.umbaska.ProtocolLib.FakePlayer;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.ProtocolLib.FakePlayerTracker;

public class ExprSneaking
  extends SimplePropertyExpression<String, Boolean>
{
  public Boolean convert(String id)
  {
    if (id == null)
      return null;
    return FakePlayerTracker.isSneaking(id);
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    String id = (String)getExpr().getSingle(e);
    if (id == null)
      return;
    boolean b = ((Boolean)delta[0]).booleanValue();
    if (mode == Changer.ChangeMode.SET) {
      FakePlayerTracker.sneak(id, b);
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
    return "Fake Player is Sneaking";
  }
}
