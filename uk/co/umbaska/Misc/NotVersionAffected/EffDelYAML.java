package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.YAMLManager;





public class EffDelYAML
  extends Effect
  implements Listener
{
  private Expression<String> file;
  private Expression<String> path;
  
  protected void execute(Event event)
  {
    String filee = (String)this.file.getSingle(event);
    String pathe = (String)this.path.getSingle(event);
    YAMLManager yaml = new YAMLManager();
    yaml.deleteYAML(filee, pathe);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Delete YAML";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.path = (Expression<String>) expressions[0];
    this.file = (Expression<String>) expressions[1];
    return true;
  }
}
