package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;







public class ExprClickedInventory
  extends SimpleExpression<String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
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
    return "invo clicked";
  }
  
  @Nullable
  protected String[] get(Event event)
  {
    return (String[])Collect.asArray(new String[] { ((InventoryClickEvent)event).getClickedInventory().toString() });
  }
}
