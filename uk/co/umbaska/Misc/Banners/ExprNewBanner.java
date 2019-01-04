package uk.co.umbaska.Misc.Banners;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;


public class ExprNewBanner
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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.color = (Expression<Color>) expressions[0];
    return true;
  }
}
