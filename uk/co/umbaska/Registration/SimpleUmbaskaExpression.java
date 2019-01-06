package uk.co.umbaska.Registration;

import ch.njol.skript.lang.util.SimpleExpression;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

public abstract class SimpleUmbaskaExpression<T>
  extends SimpleExpression<T>
{
  public String toString(@Nullable Event e, boolean arg1)
  {
    return SyntaxElement.SIMPLE_EXPRESSION.getName(getClass());
  }
  
  public boolean isSingle()
  {
    return true;
  }
}
