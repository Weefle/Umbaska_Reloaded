package uk.co.umbaska.WorldEdit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.event.Event;



public class EffPlaceSchematic
  extends Effect
{
  private Expression<Location> location;
  private Expression<String> schemname;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    String name = (String)this.schemname.getSingle(event);
    Schematic.place(name, l);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Place Schematic AIR";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[1];
    this.schemname = expressions[0];
    return true;
  }
}
