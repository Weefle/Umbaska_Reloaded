package uk.co.umbaska.Misc.Date;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Nullable;
import org.bukkit.event.Event;








public class ExprGetMillisecond
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
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.date = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "millisecond from date";
  }
  
  @Nullable
  protected Integer[] get(Event arg0)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime((Date)this.date.getSingle(arg0));
    return new Integer[] { Integer.valueOf(cal.get(14)) };
  }
}
