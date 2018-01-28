package uk.co.umbaska.Misc.Banners;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;




public class ExprNewLayer
  extends SimpleExpression<ItemStack>
{
  private Expression<ItemStack> banner;
  private Expression<Color> color;
  private Expression<PatternType> pattern;
  
  protected ItemStack[] get(Event event)
  {
    ItemStack layer = (ItemStack)this.banner.getSingle(event);
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
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.color = expressions[1];
    this.pattern = expressions[2];
    this.banner = expressions[0];
    return true;
  }
}
