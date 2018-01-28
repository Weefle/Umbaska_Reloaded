package uk.co.umbaska.Misc.Books;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import uk.co.umbaska.GattSk.Extras.Collect;


public class ExprPages
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
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.book = args[0];
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
    if (book == null) {
      return null;
    }
    return (String[])Collect.asArray(((BookMeta)book.getItemMeta()).getPages().toArray());
  }
}
