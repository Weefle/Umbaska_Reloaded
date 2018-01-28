package uk.co.umbaska.Replacers;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ExprResultSlot
  extends SimpleExpression<ItemStack>
{
  private Expression<Player> player;
  
  public Class<? extends ItemStack> getReturnType()
  {
    return ItemStack.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get entity type of a spawner";
  }
  
  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    Player p = (Player)this.player.getSingle(arg0);
    Inventory inv = p.getOpenInventory().getTopInventory();
    ItemStack item = new ItemStack(Material.AIR);
    if ((inv.getType() == InventoryType.WORKBENCH) && 
      (((CraftingInventory)inv).getResult() != null)) {
      item = ((CraftingInventory)inv).getResult();
    }
    
    if ((inv.getType() == InventoryType.FURNACE) && 
      (((CraftingInventory)inv).getResult() != null)) {
      item = ((FurnaceInventory)inv).getResult();
    }
    
    return new ItemStack[] { item };
  }
}
