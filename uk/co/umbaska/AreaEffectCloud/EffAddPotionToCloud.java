package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffAddPotionToCloud
  extends Effect
  implements Listener
{
  private Expression<Entity> cloud;
  private Expression<PotionEffect> potion;
  
  protected void execute(Event event)
  {
    Entity cloud = (Entity)this.cloud.getSingle(event);
    if (cloud.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return;
    }
    ((AreaEffectCloud)cloud).addCustomEffect((PotionEffect)this.potion.getSingle(event), true);
  }
  
  public String toString(Event event, boolean b)
  {
    return "Add Potion to Cloud";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.cloud = (Expression<Entity>) expressions[1];
    this.potion = (Expression<PotionEffect>) expressions[0];
    return true;
  }
}
