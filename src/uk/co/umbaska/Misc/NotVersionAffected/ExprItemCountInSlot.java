package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;







public class ExprItemCountInSlot
  extends SimpleExpression<Integer>
{
  private Expression<ItemStack> itemstack;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.itemstack = (Expression<ItemStack>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return owner of plot";
  }
  
  @Nullable
  protected Integer[] get(Event arg0)
  {
    Integer amt = Integer.valueOf(((ItemStack)this.itemstack.getSingle(arg0)).getAmount());
    if (((ItemStack)this.itemstack.getSingle(arg0)).getType() == Material.AIR) {
      amt = Integer.valueOf(0);
    }
    return new Integer[] { amt };
  }
}
