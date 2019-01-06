package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNewPotionEffect
  extends SimpleExpression<PotionEffect>
{
  private Expression<PotionEffectType> type;
  private Expression<Number> tier;
  private Expression<Number> time;
  
  protected PotionEffect[] get(Event event)
  {
    PotionEffect po = new PotionEffect((PotionEffectType)this.type.getSingle(event), ((Number)this.time.getSingle(event)).intValue(), ((Number)this.tier.getSingle(event)).intValue());
    return new PotionEffect[] { po };
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends PotionEffect> getReturnType()
  {
    return PotionEffect.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "new potion effect";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.type = (Expression<PotionEffectType>) expressions[0];
    this.tier = (Expression<Number>) expressions[1];
    this.time = (Expression<Number>) expressions[2];
    return true;
  }
}
