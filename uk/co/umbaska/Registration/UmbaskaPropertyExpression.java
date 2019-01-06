package uk.co.umbaska.Registration;

import ch.njol.skript.expressions.base.PropertyExpression;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

public abstract class UmbaskaPropertyExpression<F, T>
  extends PropertyExpression<F, T>
{
  public String toString(@Nullable Event e, boolean arg1)
  {
    Name name = (Name)getClass().getAnnotation(Name.class);
    if (name == null) {
      return "Umbaska PropertyExpression at " + getClass().getCanonicalName();
    }
    return name.value();
  }
}
