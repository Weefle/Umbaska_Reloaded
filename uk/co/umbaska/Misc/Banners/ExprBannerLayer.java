package uk.co.umbaska.Misc.Banners;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.event.Event;



public class ExprBannerLayer
  extends SimpleExpression<Pattern>
{
  private Expression<Block> banner;
  private Expression<Integer> layerID;
  
  protected Pattern[] get(Event event)
  {
    Banner b = (Banner)this.banner.getSingle(event);
    Integer l = (Integer)this.layerID.getSingle(event);
    return new Pattern[] { b.getPattern(l.intValue()) };
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends Pattern> getReturnType() {
    return Pattern.class;
  }
  
  public String toString(Event event, boolean b) {
    return "Get Banner Block Layer";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.banner = expressions[1];
    this.layerID = expressions[0];
    return true;
  }
}
