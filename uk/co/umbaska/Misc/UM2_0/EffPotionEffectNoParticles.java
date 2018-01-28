package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class EffPotionEffectNoParticles
  extends Effect
{
  private Expression<PotionEffectType> type;
  private Expression<Number> tier;
  private Expression<Timespan> time;
  private Expression<Entity> target;
  
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
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.type = expressions[0];
    this.tier = expressions[1];
    this.target = expressions[2];
    this.time = expressions[3];
    return true;
  }
}
