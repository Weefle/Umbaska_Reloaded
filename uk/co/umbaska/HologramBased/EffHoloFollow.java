package uk.co.umbaska.HologramBased;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

public class EffHoloFollow
  extends Effect
{
  private Expression<String> HologramName;
  private Expression<Entity> ent;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.HologramName = exprs[0];
    this.ent = exprs[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "titles";
  }
  

  protected void execute(Event event)
  {
    Entity entity = (Entity)this.ent.getSingle(event);
    String holoName = (String)this.HologramName.getSingle(event);
    if (holoName == null) {
      return;
    }
    Hologram h = Holograms.get(holoName);
    h.setFollowEntity(entity);
  }
}
