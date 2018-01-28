package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import uk.co.umbaska.Managers.YAMLManager;




public class EffWriteYAML
  extends Effect
  implements Listener
{
  private Expression<String> file;
  private Expression<String> path;
  private Expression<String> value;
  
  protected void execute(Event event)
  {
    String filee = (String)this.file.getSingle(event);
    String pathe = (String)this.path.getSingle(event);
    String valuee = (String)this.value.getSingle(event);
    YAMLManager yaml = new YAMLManager();
    yaml.writeYAML(filee, pathe, valuee);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Write YAML";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.value = expressions[0];
    this.path = expressions[1];
    this.file = expressions[2];
    return true;
  }
}
