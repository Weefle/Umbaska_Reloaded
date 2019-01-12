package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.LargeSk.events.EvtPlayerViolation;

public class ExprHackLastMinute
  extends SimpleExpression<Number>
{
  Number num;
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerViolation.class))
    {
      Skript.error("The Hack Description expression can only be used inside Player Violation Event.");
      return false;
    }
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "Hack Expression";
  }
  
  @Nullable
  protected Number[] get(Event e)
  {
    EvtPlayerViolation event = (EvtPlayerViolation)e;
    this.num = event.getViolations();
    return new Number[] { this.num };
  }
  
  public static void register()
  {
    Skript.registerExpression(ExprHackDescription.class, String.class, ExpressionType.SIMPLE, new String[] { "(hacks|cheats|violations) within last minute" });
  }
}
