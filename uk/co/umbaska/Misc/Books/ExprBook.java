package uk.co.umbaska.Misc.Books;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import uk.co.umbaska.GattSk.Extras.Collect;



public class ExprBook
  extends SimpleExpression<ItemStack>
{
  private Expression<String> name;
  
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
    this.name = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return book";
  }
  
  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    String title = (String)this.name.getSingle(arg0);
    if (title == null) {
      return null;
    }
    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta bm = (BookMeta)book.getItemMeta();
    bm.setTitle(title);
    bm.setDisplayName(title);
    book.setItemMeta(bm);
    return (ItemStack[])Collect.asArray(new ItemStack[] { book });
  }
}
