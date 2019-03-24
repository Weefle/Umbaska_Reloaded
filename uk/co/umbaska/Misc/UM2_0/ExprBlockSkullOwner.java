package uk.co.umbaska.Misc.UM2_0;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprBlockSkullOwner
  extends SimplePropertyExpression<Block, String>
{
  public String convert(Block ent)
  {
    if (ent == null)
      return null;
    return ((Skull)ent).getOwner();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Block ent = (Block)getExpr().getSingle(e);
    if (ent == null)
      return;
    if (ent.getType() != Material.SKULL) {
      return;
    }
    String b = (String)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ((Skull)ent).setOwner(b);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    return null;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  

  protected String getPropertyName()
  {
    return "skull owner block";
  }
}
