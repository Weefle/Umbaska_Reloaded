package uk.co.umbaska.Replacers;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;


public class EffSetResultSlot
  extends Effect
{
  private Expression<Player> player;
  private Expression<ItemStack> itemstack;
  
  protected void execute(Event event)
  {
    Player p = (Player)this.player.getSingle(event);
    ItemStack item = (ItemStack)this.itemstack.getSingle(event);
    Inventory inv = p.getOpenInventory().getTopInventory();
    if (inv.getType() == InventoryType.WORKBENCH) {
      ((CraftingInventory)inv).setResult(item);
    }
    if (inv.getType() == InventoryType.FURNACE) {
      ((FurnaceInventory)inv).setResult(item);
    }
    p.updateInventory();
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set Result Slot";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.itemstack = expressions[1];
    this.player = expressions[0];
    return true;
  }
}
