package uk.co.umbaska.Spawner;

import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;





public class EffMFG_SetSpawner
  extends Effect
{
  private Expression<Location> location;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    String name = ((BlockPlaceEvent)event).getItemInHand().getItemMeta().getDisplayName().toString();
    if (name == null) {
      return;
    }
    String[] nameSplit = name.split(" Spawner");
    name = nameSplit[0];
    CreatureSpawner cs = (CreatureSpawner)l.getBlock().getState();
    cs.setCreatureTypeByName(name);
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
    return true;
  }
}
