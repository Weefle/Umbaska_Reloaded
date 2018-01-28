package uk.co.umbaska.System;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.FileManager;

public class ExprContent extends SimpleExpression<String>
{
  private Expression<String> file;
  
  protected String[] get(Event event)
  {
    String f = (String)this.file.getSingle(event);
    if (f == null) return null;
    FileManager fm = new FileManager();
    try {
      return fm.getLinesOfFile(f);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public boolean isSingle() {
    return false;
  }
  
  public Class<? extends String> getReturnType() {
    return String.class;
  }
  
  public String toString(Event event, boolean b) {
    return getClass().getName();
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.file = expressions[0];
    return true;
  }
}
