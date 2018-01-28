package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;







public class ExprGetDigits
  extends SimpleExpression<String>
{
  private Expression<String> cmd;
  
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
    this.cmd = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "permission of cmd";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String src = (String)this.cmd.getSingle(arg0);
    
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < src.length(); i++) {
      char c = src.charAt(i);
      if (Character.isDigit(c)) {
        builder.append(c);
      }
    }
    String out = builder.toString();
    return new String[] { out };
  }
}
