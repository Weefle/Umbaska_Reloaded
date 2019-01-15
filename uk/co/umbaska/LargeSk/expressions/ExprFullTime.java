package uk.co.umbaska.LargeSk.expressions;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprFullTime
  extends SimpleExpression<Long>
{
  private Expression<World> world;
  
  public Class<? extends Long> getReturnType()
  {
    return Long.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.world = (Expression<World>) expr[0];
    return true;
  }
  
  @Nullable
  protected Long[] get(Event e)
  {
    return new Long[] { Long.valueOf(((World)this.world.getSingle(e)).getFullTime()) };
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET) {
      ((World)this.world.getSingle(e)).setFullTime(((Long)delta[0]).longValue());
    } else if (mode == Changer.ChangeMode.ADD) {
      ((World)this.world.getSingle(e)).setFullTime(((World)this.world.getSingle(e)).getFullTime() + ((Long)delta[0]).longValue());
    } else if (mode == Changer.ChangeMode.RESET) {
      ((World)this.world.getSingle(e)).setFullTime(0L);
    } else if (mode == Changer.ChangeMode.REMOVE) {
      if (((World)this.world.getSingle(e)).getFullTime() > ((Long)delta[0]).longValue()) {
        ((World)this.world.getSingle(e)).setFullTime(((World)this.world.getSingle(e)).getFullTime() - ((Long)delta[0]).longValue());
      } else {
        ((World)this.world.getSingle(e)).setFullTime(0L);
      }
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.RESET) || (mode == Changer.ChangeMode.REMOVE)) {
      return (Class[])CollectionUtils.array(new Class[] { Long.class });
    }
    return null;
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Full Time";
}
}
