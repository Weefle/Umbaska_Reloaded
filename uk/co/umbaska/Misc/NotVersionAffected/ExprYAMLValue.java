package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
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
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.file = args[0];
    this.path = args[1];
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
    








    return new Object[] { null };
  }
}
