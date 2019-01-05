package uk.co.umbaska.Misc.NotVersionAffected;

import java.io.BufferedReader;
import java.io.Reader;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Managers.YAMLManager;






public class ExprYAMLValue
  extends SimpleExpression<Object>
{
  private Expression<String> file;
  private Expression<String> path;
  
  public Class<? extends Object> getReturnType()
  {
    return Object.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.file = (Expression<String>) args[0];
    this.path = (Expression<String>) args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "permission of cmd";
  }
  

  @Nullable
  protected Object[] get(Event arg0)
  {
    String f = (String)this.file.getSingle(arg0);
    String p = (String)this.path.getSingle(arg0);
    YAMLManager yaml = new YAMLManager();
    BufferedReader buf = new BufferedReader((Reader) yaml.getSingleYAML(f, p));
    








    return new Object[] { buf.lines() };
  }
}
