package uk.co.umbaska.Misc.Books;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;


public class ExprAuthor
  extends SimpleExpression<String>
{
  private Expression<ItemStack> book;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.book = (Expression<ItemStack>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return book";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    ItemStack book = (ItemStack)this.book.getSingle(arg0);
    return (String[])Collect.asArray(new String[] { ((BookMeta)book.getItemMeta()).getAuthor() });
  }
}
