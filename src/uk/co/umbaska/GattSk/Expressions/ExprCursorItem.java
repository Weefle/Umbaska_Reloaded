package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import uk.co.umbaska.GattSk.Extras.Collect;

public class ExprCursorItem extends ch.njol.skript.lang.util.SimpleExpression<ItemStack>
{
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
    if (!ch.njol.skript.ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
      Skript.error("Cannot use Cursor Item outside of Click Inventory event", ErrorQuality.SEMANTIC_ERROR);
      return false;
    }
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "cursor item";
  }
  
  @Nullable
  protected ItemStack[] get(Event event)
  {
    return (ItemStack[])Collect.asArray(new ItemStack[] { ((InventoryClickEvent)event).getWhoClicked().getOpenInventory().getCursor() });
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ItemStack newItem = (ItemStack)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      ((InventoryClickEvent)e).getWhoClicked().getOpenInventory().setCursor(newItem);
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { ItemStack.class });
  }
}
