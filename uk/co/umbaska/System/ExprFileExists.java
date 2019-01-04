package uk.co.umbaska.System;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.FileManager;







public class ExprFileExists
  extends SimpleExpression<Boolean>
{
  private Expression<String> file;
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.file = (Expression<String>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set {v} to existance of %string%";
  }
  
  @Nullable
  protected Boolean[] get(Event arg0)
  {
    String f = (String)this.file.getSingle(arg0);
    FileManager fm = new FileManager();
    return new Boolean[] { Boolean.valueOf(fm.fileExists(f)) };
  }
}
