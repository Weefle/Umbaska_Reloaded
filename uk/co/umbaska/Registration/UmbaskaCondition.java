package uk.co.umbaska.Registration;

import ch.njol.skript.lang.Condition;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

public abstract class UmbaskaCondition
  extends Condition
{
  public String toString(@Nullable Event e, boolean arg1)
  {
    return SyntaxElement.CONDITION.getName(getClass());
  }
}
