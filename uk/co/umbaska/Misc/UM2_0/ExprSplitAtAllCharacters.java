package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;


public class ExprSplitAtAllCharacters
  extends SimpleExpression<String>
{
  private Expression<String> stringToSplit;
  
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
    this.stringToSplit = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return string split at all characters";
  }
  
  @Nullable
  protected String[] get(Event arg0) {
    return ((String)this.stringToSplit.getSingle(arg0)).split("");
  }
}
