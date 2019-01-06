package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffSpawnAreaEffectCloud
  extends Effect
{
  private Expression<Location> loc;
  
  protected void execute(Event event)
  {
    Location loc = (Location)this.loc.getSingle(event);
    Entity e = loc.getWorld().spawn(loc, AreaEffectCloud.class);
    ch.njol.skript.effects.EffSpawn.lastSpawned = e;
  }
  
  public String toString(Event event, boolean b)
  {
    return "spawn area effect cloud";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.loc = (Expression<Location>) expressions[0];
    return true;
  }
}
