package uk.co.umbaska.Misc.UM2_0;

import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;




public class ExprCommandBlockValue
  extends SimplePropertyExpression<Block, String>
{
  public String convert(Block blc)
  {
    if (blc == null)
      return null;
    return ((CommandBlock)blc).getCommand();
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Block ent = (Block)getExpr().getSingle(e);
    if (ent == null)
      return;
    String b = (String)delta[0];
    ((CommandBlock)ent).setCommand(b);
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    if (mode == Changer.ChangeMode.ADD)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    if (mode == Changer.ChangeMode.REMOVE)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  

  protected String getPropertyName()
  {
    return "Command Block Command";
  }
}
