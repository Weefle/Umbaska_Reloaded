package uk.co.umbaska.System;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Managers.FileManager;







public class ExprFileInDir
  extends SimpleExpression<String>
{
  private Expression<String> file;
  private Expression<Boolean> recursive;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.file = args[0];
    this.recursive = args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set {v} to files in %string% (recursive|r) %boolean%";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    String f = (String)this.file.getSingle(arg0);
    Boolean r = (Boolean)this.recursive.getSingle(arg0);
    FileManager fm = new FileManager();
    if (!r.booleanValue()) {
      return fm.unrecursiveFileListing(f);
    }
    return fm.recursiveFileListing(f);
  }
}
