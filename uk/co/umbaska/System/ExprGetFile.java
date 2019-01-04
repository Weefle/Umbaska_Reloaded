package uk.co.umbaska.System;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.FileManager;








public class ExprGetFile
  extends SimpleExpression<String>
{
  private Expression<String> file;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.file = (Expression<String>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set {v} to lines of %file%";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    String f = (String)this.file.getSingle(arg0);
    FileManager fm = new FileManager();
    try {
      return fm.getLinesOfFile(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
