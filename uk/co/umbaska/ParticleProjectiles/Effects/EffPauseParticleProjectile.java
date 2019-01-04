package uk.co.umbaska.ParticleProjectiles.Effects;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;


public class EffPauseParticleProjectile
  extends Effect
{
  private Expression<String> name;
  
  protected void execute(Event event)
  {
    if (ParticleProjectileHandler.particleProjectiles.containsKey(this.name.getSingle(event))) {
      ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(this.name.getSingle(event))).pause();
    } else {
      Skript.error(Skript.SKRIPT_PREFIX + "Particle Projectile doesn't exist");
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Start Particle Projectile";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = (Expression<String>) expressions[0];
    return true;
  }
}
