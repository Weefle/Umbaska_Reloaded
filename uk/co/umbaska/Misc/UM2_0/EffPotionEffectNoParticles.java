package uk.co.umbaska.Misc.UM2_0;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;



public class EffPotionEffectNoParticles
  extends Effect
{
  private Expression<PotionEffectType> type;
  private Expression<Number> tier;
  private Expression<Timespan> time;
  private Expression<Entity> target;
  
  @SuppressWarnings("deprecation")
protected void execute(Event event)
  {
    Number tier = Integer.valueOf(((Number)this.tier.getSingle(event)).intValue());
    if (tier == null) {
      tier = Integer.valueOf(0);
    } else {
      tier = Integer.valueOf(tier.intValue() - 1);
    }
    for (Entity e : (Entity[])this.target.getAll(event)) {
      ((LivingEntity)e).addPotionEffect(new PotionEffect((PotionEffectType)this.type.getSingle(event), ((Timespan)this.time.getSingle(event)).getTicks(), tier.intValue(), true));
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "Potion Effect No Particles";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.type = (Expression<PotionEffectType>) expressions[0];
    this.tier = (Expression<Number>) expressions[1];
    this.target = (Expression<Entity>) expressions[2];
    this.time = (Expression<Timespan>) expressions[3];
    return true;
  }
}
