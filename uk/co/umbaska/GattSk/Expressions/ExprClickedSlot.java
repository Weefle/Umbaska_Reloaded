package uk.co.umbaska.GattSk.Expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.co.umbaska.GattSk.Extras.Collect;







public class ExprClickedSlot
  extends SimpleExpression<Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
      Skript.error("You can not use this expression in this event!");
    }
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "clicked slot";
  }
  
  @Nullable
  protected Integer[] get(Event event)
  {
    return (Integer[])Collect.asArray(new Integer[] { Integer.valueOf(((InventoryClickEvent)event).getSlot()) });
  }
}
