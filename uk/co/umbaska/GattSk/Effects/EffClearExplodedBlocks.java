package uk.co.umbaska.GattSk.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;





public class EffClearExplodedBlocks
  extends Effect
{
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "remove all exploded block";
  }
  
  protected void execute(Event event) {
    ((EntityExplodeEvent)event).blockList().clear();
  }
}
