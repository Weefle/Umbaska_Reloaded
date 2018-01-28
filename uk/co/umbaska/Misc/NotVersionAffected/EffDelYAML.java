package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.path = expressions[0];
    this.file = expressions[1];
    return true;
  }
}
