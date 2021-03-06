package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.YAMLManager;






public class EffNewFile
  extends Effect
  implements Listener
{
  private Expression<String> file;
  
  protected void execute(Event event)
  {
    String filee = (String)this.file.getSingle(event);
    YAMLManager yaml = new YAMLManager();
    yaml.newFile(filee);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Delete YAML";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.file = (Expression<String>) expressions[0];
    return true;
  }
}
