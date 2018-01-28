package uk.co.umbaska.GattSk.Effects.InventoryClick;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;









public class EffSetCursorItem
  extends Effect
{
  Expression<ItemStack> item;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.item = exprs[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set cursor item";
  }
  
  protected void execute(Event event) {
    ItemStack itemTobe = (ItemStack)this.item.getSingle(event);
    ((InventoryClickEvent)event).getCursor().setType(itemTobe.getType());
    ((InventoryClickEvent)event).getCursor().setItemMeta(itemTobe.getItemMeta());
    ((InventoryClickEvent)event).getCursor().addEnchantments(itemTobe.getEnchantments());
    ((InventoryClickEvent)event).getCursor().setAmount(itemTobe.getAmount());
    ((InventoryClickEvent)event).getCursor().setData(itemTobe.getData());
    ((InventoryClickEvent)event).getCursor().setDurability(itemTobe.getDurability());
  }
}
