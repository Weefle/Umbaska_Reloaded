package uk.co.umbaska.LargeSk.conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class CondEvaluateCondition
  extends Condition
{
  private Expression<String> condition;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.condition = (Expression<String>) expr[0];
    return true;
  }
  
  @SuppressWarnings("unchecked")
public boolean check(Event e)
  {
    String cond = (String)this.condition.getSingle(e);
    try
    {
      ScriptLoader.setCurrentEvent("this", new Class[] { e.getClass() });
      Condition c = Condition.parse(cond, null);
      ScriptLoader.deleteCurrentEvent();
      boolean answer = c.check(e);
      if (answer == true) {
        return true;
      }
    }
    catch (Exception ignored) {}
    return false;
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Evaluate Condition";
}
}
