package uk.co.umbaska.Spawner;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;






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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[0];
    this.entity = expressions[1];
    return true;
  }
}
