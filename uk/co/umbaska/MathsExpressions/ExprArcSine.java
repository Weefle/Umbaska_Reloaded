package uk.co.umbaska.MathsExpressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;


public class ExprArcSine
  extends SimpleExpression<Number>
{
  private Expression<Number> input;
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.input = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "umb arcsine";
  }
  

  @Nullable
  protected Number[] get(Event arg0)
  {
    return new Number[] { Double.valueOf(Math.asin(((Number)this.input.getSingle(arg0)).doubleValue())) };
  }
}
