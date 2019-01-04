package uk.co.umbaska.Misc.Banners;

import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;



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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.banner = (Expression<Block>) expressions[1];
    this.layerID = (Expression<Integer>) expressions[0];
    return true;
  }
}
