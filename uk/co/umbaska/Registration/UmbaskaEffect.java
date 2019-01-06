package uk.co.umbaska.Registration;

import ch.njol.skript.lang.Effect;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

public abstract class UmbaskaEffect
  extends Effect
{
  public String toString(@Nullable Event e, boolean arg1)
  {
    return SyntaxElement.EFFECT.getName(getClass());
  }
}
