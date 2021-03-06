package uk.co.umbaska.Spawner;

import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;






public class EffSetDelay
  extends Effect
{
  private Expression<Location> location;
  private Expression<Integer> delay;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    Integer i = (Integer)this.delay.getSingle(event);
    if (l == null)
      return;
    if (i == null) {
      return;
    }
    CreatureSpawner cs = (CreatureSpawner)l.getBlock().getState();
    cs.setDelay(i.intValue());
    cs.update();
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set a spawner";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = (Expression<Location>) expressions[0];
    this.delay = (Expression<Integer>) expressions[1];
    return true;
  }
}
