package uk.co.umbaska.Spawner;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;






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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[0];
    this.delay = expressions[1];
    return true;
  }
}
