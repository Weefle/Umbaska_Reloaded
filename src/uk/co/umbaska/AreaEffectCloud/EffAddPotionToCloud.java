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
import ch.njol.skript.util.PotionEffectUtils;
import ch.njol.util.Kleenean;

public class EffAddPotionToCloud
  extends Effect
  implements Listener
{
  private Expression<Entity> cloud;
  private Expression<String> potion;
  private Expression<Integer> duration;
  private Expression<Integer> amplifier;
  
  protected void execute(Event event)
  {
    Entity cloud = (Entity)this.cloud.getSingle(event);
    if (cloud.getType() != EntityType.AREA_EFFECT_CLOUD) {
      return;
    }
    PotionEffect potioneffect = new PotionEffect(PotionEffectUtils.parseType(this.potion.getSingle(event)), duration.getSingle(event), amplifier.getSingle(event));
    ((AreaEffectCloud)cloud).addCustomEffect(potioneffect, true);
  }
  
  public String toString(Event event, boolean b)
  {
    return "Add Potion to Cloud";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
	this.potion = (Expression<String>) expressions[0];
	this.duration = (Expression<Integer>) expressions[1];
	this.amplifier = (Expression<Integer>) expressions[2];
    this.cloud = (Expression<Entity>) expressions[3];
    return true;
  }
}
