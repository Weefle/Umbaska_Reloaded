package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Utils.ItemManager;


public class ExprBetterGlow
  extends SimpleExpression<ItemStack>
{
  private Expression<ItemStack> item;
  
  public boolean isSingle()
  {
    return true;
  }
  


  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.item = (Expression<ItemStack>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "glow";
  }
  

  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    ItemStack itemStack = (ItemStack)this.item.getSingle(arg0);
    return new ItemStack[] { ItemManager.addGlow(itemStack) };
  }
  
  public Class<? extends ItemStack> getReturnType()
  {
    return ItemStack.class;
  }
}
