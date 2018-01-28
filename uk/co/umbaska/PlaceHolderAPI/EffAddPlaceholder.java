package uk.co.umbaska.PlaceHolderAPI;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.HashMap;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.Register;







public class EffAddPlaceholder
  extends Effect
{
  private Expression<String> variable;
  private Expression<String> value;
  private Expression<String> plugin;
  
  protected void execute(Event event)
  {
    String var = (String)this.variable.getSingle(event);
    String val = (String)this.value.getSingle(event);
    if (this.plugin != null) {
      String pl = (String)this.plugin.getSingle(event);
      var = pl + "_" + var;
    }
    if (Register.placeholderMap.containsKey(var)) {
      Register.placeholderMap.remove(var);
      Register.placeholderMap.put(var, val);
    } else {
      Register.placeholderMap.put(var, val);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "add variable {nfell2009_says_hi_to_you} with value %string% to placeholders";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.variable = expressions[0];
    this.value = expressions[1];
    if (i == 1) {
      this.plugin = expressions[2];
    }
    return true;
  }
}
