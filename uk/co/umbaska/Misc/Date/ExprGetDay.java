package uk.co.umbaska.Misc.Date;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Nullable;
import org.bukkit.event.Event;








public class ExprGetDay
  extends SimpleExpression<DayOfWeek>
{
  private Expression<Date> date;
  
  public Class<? extends DayOfWeek> getReturnType()
  {
    return DayOfWeek.class;
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
    return "day of week from date";
  }
  
  @Nullable
  protected DayOfWeek[] get(Event arg0)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime((Date)this.date.getSingle(arg0));
    int day = cal.get(7);
    

    return new DayOfWeek[] { DayOfWeek.getDay(day) };
  }
}
