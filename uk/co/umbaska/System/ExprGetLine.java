package uk.co.umbaska.System;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.io.IOException;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.FileManager;








public class ExprGetLine
  extends SimpleExpression<String>
{
  private Expression<Integer> line;
  private Expression<String> file;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.line = args[0];
    this.file = args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set {v} to line %integer% of %file%";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    String f = (String)this.file.getSingle(arg0);
    Integer l = (Integer)this.line.getSingle(arg0);
    FileManager fm = new FileManager();
    try {
      return new String[] { fm.getLineOfFile(f, l) };
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
