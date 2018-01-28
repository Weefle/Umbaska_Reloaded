package uk.co.umbaska.System;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.FileManager;






public class EffWriteToFile
  extends Effect
{
  private Expression<String> file;
  private Expression<String> string;
  
  protected void execute(Event event)
  {
    String f = (String)this.file.getSingle(event);
    String s = (String)this.string.getSingle(event);
    FileManager fm = new FileManager();
    fm.writeToFile(f, s, Boolean.valueOf(true));
  }
  

  public String toString(Event event, boolean b)
  {
    return "set line %integer% of %file% to %string%";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.string = expressions[0];
    this.file = expressions[1];
    return true;
  }
}
