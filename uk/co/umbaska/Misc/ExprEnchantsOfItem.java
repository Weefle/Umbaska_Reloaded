package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;




public class ExprEnchantsOfItem
  extends SimpleExpression<Enchantment>
{
  private Expression<ItemStack> item;
  
  public Class<? extends Enchantment> getReturnType()
  {
    return Enchantment.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.item = (Expression<ItemStack>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "enchants of item";
  }
  

  @Nullable
  protected Enchantment[] get(Event arg0)
  {
    ItemStack i = (ItemStack)this.item.getSingle(arg0);
    
    if (i == null) {
      return null;
    }
    










    return (Enchantment[])i.getEnchantments().keySet().toArray(new Enchantment[i.getEnchantments().keySet().size()]);
  }
}
