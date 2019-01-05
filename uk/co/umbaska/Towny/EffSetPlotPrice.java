package uk.co.umbaska.Towny;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.TownyUniverse;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;






public class EffSetPlotPrice
  extends Effect
{
  private Expression<Location> location;
  private Expression<Double> price;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    Double i = (Double)this.price.getSingle(event);
    if (l == null)
      return;
    if (i == null) {
      return;
    }
    TownyUniverse.getTownBlock(l).setPlotPrice(i.doubleValue());
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set a plot price";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = (Expression<Location>) expressions[0];
    this.price = (Expression<Double>) expressions[1];
    return true;
  }
}
