package uk.co.umbaska.Misc.Date;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;








public class ExprGetHour
  extends SimpleExpression<Integer>
{
  private Expression<Date> date;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.date = (Expression<Date>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "hour from date";
  }
  
  @Nullable
  protected Integer[] get(Event arg0)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime((Date)this.date.getSingle(arg0));
    return new Integer[] { Integer.valueOf(cal.get(10)) };
  }
}
