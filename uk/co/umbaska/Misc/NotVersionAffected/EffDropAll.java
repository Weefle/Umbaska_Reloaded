package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;






public class EffDropAll
  extends Effect
{
  private Expression<Player> player;
  private Expression<Location> location;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    Player p = (Player)this.player.getSingle(event);
    if (l == null)
      return;
    if (p == null) {
      return;
    }
    Location loc = p.getLocation().clone();
    Inventory inv = p.getInventory();
    for (ItemStack item : inv.getContents()) {
      if (item != null) {
        loc.getWorld().dropItem(l, item.clone());
      }
    }
    inv.clear();
  }
  

  public String toString(Event event, boolean b)
  {
    return "Drop all of a players inventory at a location";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    this.location = expressions[1];
    return true;
  }
}
