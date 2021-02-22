package uk.co.umbaska.HologramBased;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffHoloFollow
  extends Effect
{
  private Expression<String> HologramName;
  private Expression<Entity> ent;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.HologramName = (Expression<String>) exprs[0];
    this.ent = (Expression<Entity>) exprs[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "hologram follow";
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
