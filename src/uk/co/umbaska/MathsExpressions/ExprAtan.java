package uk.co.umbaska.MathsExpressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;


public class ExprAtan
  extends SimpleExpression<Number>
{
  private Expression<Number> input1;
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.input1 = (Expression<Number>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "umb atan2";
  }
  
  @Nullable
  protected Number[] get(Event arg0)
  {
    return new Number[] { Double.valueOf(Math.atan(((Number)this.input1.getSingle(arg0)).doubleValue())) };
  }
}