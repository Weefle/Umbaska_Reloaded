package uk.co.umbaska.HologramBased;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;




public class EffCreateHologram
  extends Effect
  implements Listener
{
  private Expression<String> HologramName;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.HologramName = (Expression<String>) exprs[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "create hologram";
  }
  
  protected void execute(Event event)
  {
    String holoName = (String)this.HologramName.getSingle(event);
    if (holoName == null) {
      return;
    }
    Holograms.get(holoName);
  }
}
