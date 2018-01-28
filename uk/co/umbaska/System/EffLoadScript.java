package uk.co.umbaska.System;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.FileManager;







public class EffLoadScript
  extends Effect
{
  private Expression<String> file;
  
  protected void execute(Event event)
  {
    String f = (String)this.file.getSingle(event);
    FileManager fm = new FileManager();
    fm.loadScripts(f);
  }
  

  public String toString(Event event, boolean b)
  {
    return "load a script";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.file = expressions[0];
    return true;
  }
}
