package uk.co.umbaska.Spawner;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;







public class EffMFG_Drop
  extends Effect
{
  private Expression<Location> location;
  private Expression<Block> basedon;
  
  protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    Block bo = (Block)this.basedon.getSingle(event);
    CreatureSpawner cs = (CreatureSpawner)bo.getState();
    String e = cs.getCreatureTypeName();
    ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1);
    ItemMeta spawnerMeta = spawner.getItemMeta();
    spawnerMeta.setDisplayName(e + " Spawner");
    spawner.setItemMeta(spawnerMeta);
    World w = l.getWorld();
    w.dropItemNaturally(l, spawner);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set a spawner";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.location = expressions[0];
    this.basedon = expressions[1];
    return true;
  }
}
