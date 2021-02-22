package uk.co.umbaska.WorldEdit;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;



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
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = (Expression<Location>) expressions[1];
    this.schemname = (Expression<String>) expressions[0];
    return true;
  }
}
