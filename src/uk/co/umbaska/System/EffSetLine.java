package uk.co.umbaska.System;

import java.io.IOException;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.FileManager;







public class EffSetLine
  extends Effect
{
  private Expression<String> file;
  private Expression<String> string;
  private Expression<Integer> line;
  
  protected void execute(Event event)
  {
    String f = (String)this.file.getSingle(event);
    String s = (String)this.string.getSingle(event);
    Integer l = (Integer)this.line.getSingle(event);
    FileManager fm = new FileManager();
    try {
      fm.setLineOfFile(f, s, l);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "set line %integer% of %file% to %string%";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.line = (Expression<Integer>) expressions[0];
    this.file = (Expression<String>) expressions[1];
    this.string = (Expression<String>) expressions[2];
    return true;
  }
}
