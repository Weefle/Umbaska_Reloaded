package uk.co.umbaska.System;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.FileManager;











public class CondFileExists
  extends Condition
{
  private Expression<String> file;
  
  public boolean init(Expression<?>[] expr, int i, Kleenean kl, SkriptParser.ParseResult pr)
  {
    this.file = expr[0];
    return true;
  }
  
  public String toString(@Nullable Event e, boolean b)
  {
    return "Relation of a town";
  }
  
  public boolean check(Event e)
  {
    String f = (String)this.file.getSingle(e);
    FileManager fm = new FileManager();
    if (fm.fileExists(f)) {
      return true;
    }
    return false;
  }
}
