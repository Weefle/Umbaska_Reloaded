package uk.co.umbaska.Registration;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

public abstract class SimpleUmbaskaPropertyExpression<F, T>
  extends SimplePropertyExpression<F, T>
{
  public String toString(@Nullable Event e, boolean arg1)
  {
    return getPropertyName();
  }
  
  protected String getPropertyName()
  {
    return SyntaxElement.SIMPLE_PROPERTY_EXPRESSION.getName(getClass());
  }
}
