package uk.co.umbaska.ParticleProjectiles.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHitEvent;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileLandEvent;







public class ExprCurrentParticleProjectile
  extends SimpleExpression<String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "ParticleProjectile";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    if ((arg0 instanceof ParticleProjectileHitEvent))
      return (String[])Collect.asArray(new String[] { ((ParticleProjectileHitEvent)arg0).getParticleName() });
    if ((arg0 instanceof ParticleProjectileLandEvent)) {
      return (String[])Collect.asArray(new String[] { ((ParticleProjectileLandEvent)arg0).getParticleName() });
    }
    
    return null;
  }
}
