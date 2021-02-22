package uk.co.umbaska.Misc.Banners;

import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;




public class ExprFirstLayer
  extends SimpleExpression<ItemStack>
{
  private Expression<Color> color;
  private Expression<PatternType> pattern;
  
  protected ItemStack[] get(Event event)
  {
    ItemStack layer = new ItemStack(Material.BANNER);
    BannerMeta bm = (BannerMeta)layer.getItemMeta();
    bm.addPattern(new Pattern(((Color)this.color.getSingle(event)).getWoolColor(), (PatternType)this.pattern.getSingle(event)));
    layer.setItemMeta(bm);
    return new ItemStack[] { layer };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends ItemStack> getReturnType() {
    return ItemStack.class;
  }
  
  public String toString(Event event, boolean b) {
    return "New Banner Layer";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.color = (Expression<Color>) expressions[0];
    this.pattern = (Expression<PatternType>) expressions[1];
    return true;
  }
}
