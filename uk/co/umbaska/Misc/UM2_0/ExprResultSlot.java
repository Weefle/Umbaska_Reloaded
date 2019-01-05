package uk.co.umbaska.Misc.UM2_0;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprResultSlot
  extends SimpleExpression<ItemStack>
{
  private Expression<Player> player;
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = (Expression<Player>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "result slot";
  }
  

  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    ItemStack itm = new ItemStack(Material.AIR);
    Player p = (Player)this.player.getSingle(arg0);
    InventoryType t = p.getOpenInventory().getTopInventory().getType();
    Inventory i = p.getOpenInventory().getTopInventory();
    if (t == InventoryType.ANVIL) {
      itm = i.getItem(2);
    }
    else if (t == InventoryType.FURNACE) {
      itm = ((FurnaceInventory)i).getResult();
    } else if (t == InventoryType.WORKBENCH) {
      itm = ((CraftingInventory)i).getResult();
    }
    return new ItemStack[] { itm };
  }
  
  public Class<? extends ItemStack> getReturnType()
  {
    return ItemStack.class;
  }
}
