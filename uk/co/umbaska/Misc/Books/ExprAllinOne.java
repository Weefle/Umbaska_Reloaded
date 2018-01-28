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


public class ExprAllinOne
  extends SimpleExpression<ItemStack>
{
  private Expression<String> title;
  private Expression<String> pages;
  private Expression<String> author;
  
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
    this.title = args[0];
    this.author = args[1];
    this.pages = args[2];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return book";
  }
  
  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    String title = (String)this.title.getSingle(arg0);
    String author = (String)this.author.getSingle(arg0);
    String[] pages = (String[])this.pages.getAll(arg0);
    if ((author == null) || (title == null) || (pages == null)) {
      return null;
    }
    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta bm = (BookMeta)book.getItemMeta();
    bm.setTitle(title);
    bm.setPages(pages);
    bm.setAuthor(author);
    bm.setDisplayName(title);
    book.setItemMeta(bm);
    return (ItemStack[])Collect.asArray(new ItemStack[] { book });
  }
}
