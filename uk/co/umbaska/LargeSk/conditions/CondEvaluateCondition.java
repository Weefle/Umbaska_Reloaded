package uk.co.umbaska.LargeSk.conditions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaCondition;

@Name("Evaluate Condition")
@Syntaxes({"[evaluate] cond[ition] %string%"})
public class CondEvaluateCondition
  extends UmbaskaCondition
{
  private Expression<String> condition;
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.condition = expr[0];
    return true;
  }
  
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
}
