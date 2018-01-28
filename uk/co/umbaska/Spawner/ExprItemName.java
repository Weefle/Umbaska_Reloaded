package uk.co.umbaska.Spawner;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;







public class ExprItemName
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
  
  public boolean init(Expression<?>[] e, int i, Kleenean kl, SkriptParser.ParseResult pr)
  {
    if (!ScriptLoader.isCurrentEvent(BlockPlaceEvent.class)) {
      Skript.error("Cannot use client in other events other than the transaction event", ErrorQuality.SEMANTIC_ERROR);
      

      return false;
    }
    return true;
  }
  
  public String toString(@Nullable Event e, boolean b)
  {
    return "Returns price";
  }
  
  @Nullable
  protected String[] get(Event e)
  {
    return new String[] { ((BlockPlaceEvent)e).getItemInHand().getItemMeta().getDisplayName() };
  }
}
