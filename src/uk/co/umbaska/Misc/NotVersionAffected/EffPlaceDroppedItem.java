package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;





public class EffPlaceDroppedItem
  extends Effect
{
  private Expression<Location> l;
  private Expression<ItemStack> item;
  
  protected void execute(Event event)
  {
    ExprLastPlacedItem.lastItem = ((Location)this.l.getSingle(event)).getWorld().dropItemNaturally((Location)this.l.getSingle(event), (ItemStack)this.item.getSingle(event));
    ExprLastPlacedItem.lastItem.setVelocity(new Vector(0, 0, 0));
  }
  

  public String toString(Event event, boolean b)
  {
    return "Place Dropped item";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.l = (Expression<Location>) expressions[1];
    this.item = (Expression<ItemStack>) expressions[0];
    return true;
  }
}
