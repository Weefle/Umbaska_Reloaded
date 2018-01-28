package uk.co.umbaska.Misc.Banners;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;


public class ExprNewBannerFrom
  extends SimpleExpression<ItemStack>
{
  private Expression<Color> color;
  
  protected ItemStack[] get(Event event)
  {
    ItemStack item = new ItemStack(Material.BANNER);
    BannerMeta bm = (BannerMeta)item.getItemMeta();
    bm.setBaseColor(((Color)this.color.getSingle(event)).getWoolColor());
    item.setItemMeta(bm);
    return new ItemStack[] { item };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends ItemStack> getReturnType() {
    return ItemStack.class;
  }
  
  public String toString(Event event, boolean b) {
    return "New Banner";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.color = expressions[0];
    return true;
  }
}
