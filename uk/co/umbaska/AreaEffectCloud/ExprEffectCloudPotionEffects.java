package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEffectCloudPotionEffects
  extends SimpleExpression<PotionEffect>
{
  Expression<Entity> entity;
  
  protected PotionEffect[] get(Event event)
  {
    Entity cloud = (Entity)this.entity.getSingle(event);
    if (cloud.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return null;
    }
    return (PotionEffect[])((AreaEffectCloud)cloud).getEffects().toArray(new PotionEffect[((AreaEffectCloud)cloud).getEffects().size()]);
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
    return "Potion Effects of Area Effect Cloud";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.entity = (Expression<Entity>) expressions[0];
    return true;
  }
}
