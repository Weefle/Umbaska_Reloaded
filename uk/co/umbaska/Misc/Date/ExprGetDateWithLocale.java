package uk.co.umbaska.Misc.Date;

import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Utils.StringToDateClass;








public class ExprGetDateWithLocale
  extends SimpleExpression<Date>
{
  private Expression<String> date;
  private Expression<String> format;
  private Expression<Locale> locale;
  
  public Class<? extends Date> getReturnType()
  {
    return Date.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.date = (Expression<String>) args[0];
    this.format = (Expression<String>) args[1];
    this.locale = (Expression<Locale>) args[2];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "date from format <locale>";
  }
  

  @Nullable
  protected Date[] get(Event arg0)
  {
    String date = (String)this.date.getSingle(arg0);
    String form = (String)this.format.getSingle(arg0);
    
    Date out = StringToDateClass.getDate(date, form, (Locale)this.locale.getSingle(arg0));
    return new Date[] { out };
  }
}
