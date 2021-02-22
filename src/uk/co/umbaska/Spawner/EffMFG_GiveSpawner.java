package uk.co.umbaska.Spawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;






public class EffMFG_GiveSpawner
  extends Effect
{
  private Expression<Player> player;
  private Expression<Block> basedon;
  
  protected void execute(Event event)
  {
    Player p = (Player)this.player.getSingle(event);
    Block bo = (Block)this.basedon.getSingle(event);
    CreatureSpawner cs = (CreatureSpawner)bo.getState();
    String e = cs.getCreatureTypeName();
    ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1);
    ItemMeta spawnerMeta = spawner.getItemMeta();
    spawnerMeta.setDisplayName(e + " Spawner");
    spawner.setItemMeta(spawnerMeta);
    p.getInventory().addItem(new ItemStack[] { spawner });
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set a spawner";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    this.basedon = (Expression<Block>) expressions[1];
    return true;
  }
}
