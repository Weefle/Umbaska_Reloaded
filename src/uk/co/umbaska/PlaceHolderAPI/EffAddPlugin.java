package uk.co.umbaska.PlaceHolderAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import uk.co.umbaska.Managers.Register;









public class EffAddPlugin
  extends Effect
{
  private Expression<String> variable;
  
  protected void execute(Event event)
  {
    final String var = (String)this.variable.getSingle(event);
    PlaceholderAPI.registerPlaceholderHook(var, new PlaceholderHook()
    {
      public String onPlaceholderRequest(Player p, String identifier) {
        if (Register.placeholderMap.containsKey(var + "_" + identifier)) {
          return (String)Register.placeholderMap.get(var + "_" + identifier);
        }
        return null;
      }
    });
  }
  

  public String toString(Event event, boolean b)
  {
    return "add variable {nfell2009_says_hi_to_you} with value %string% to placeholders";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.variable = (Expression<String>) expressions[0];
    return true;
  }
}
