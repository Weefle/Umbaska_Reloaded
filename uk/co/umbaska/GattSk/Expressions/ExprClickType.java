package uk.co.umbaska.GattSk.Expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.co.umbaska.GattSk.Extras.Collect;

public class ExprClickType extends ch.njol.skript.lang.util.SimpleExpression<ClickType>
{
  public Class<? extends ClickType> getReturnType()
  {
    return ClickType.class;
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
    return "click type";
  }
  
  @Nullable
  protected ClickType[] get(Event event)
  {
    return (ClickType[])Collect.asArray(new ClickType[] { ((InventoryClickEvent)event).getClick() });
  }
}
