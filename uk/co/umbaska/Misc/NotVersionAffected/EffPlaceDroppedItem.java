package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;





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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.l = expressions[1];
    this.item = expressions[0];
    return true;
  }
}
