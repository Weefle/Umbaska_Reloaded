package uk.co.umbaska.Spawner;

import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;






public class EffSetSpawner
  extends Effect
{
  private Expression<Location> location;
  private Expression<String> entity;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    String e = (String)this.entity.getSingle(event);
    if (l == null)
      return;
    if (e == null) {
      return;
    }
    CreatureSpawner cs = (CreatureSpawner)l.getBlock().getState();
    cs.setCreatureTypeByName(e);
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
    this.entity = (Expression<String>) expressions[1];
    return true;
  }
}
