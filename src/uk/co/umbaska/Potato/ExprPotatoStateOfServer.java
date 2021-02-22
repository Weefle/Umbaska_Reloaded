package uk.co.umbaska.Potato;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprPotatoStateOfServer
  extends SimpleExpression<Boolean>
{
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  
  public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  @Nullable
  protected Boolean[] get(Event e)
  {
    boolean state = PotatoUtil.getPotatoState();
    try
    {
      throw new PotatoException("Too much potatoes!");
    }
    catch (PotatoException ex)
    {
      ex.printStackTrace();
    }
    return new Boolean[] {state};
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Skript.error("Your server will always be a Potato", ErrorQuality.SEMANTIC_ERROR);
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Potato State of Server";
}
}
