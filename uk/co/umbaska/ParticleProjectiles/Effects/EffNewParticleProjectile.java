package uk.co.umbaska.ParticleProjectiles.Effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.HashMap;
import org.bukkit.event.Event;
import uk.co.umbaska.Enums.ParticleEnum;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;



public class EffNewParticleProjectile
  extends Effect
{
  private Expression<ParticleEnum> particle;
  private Expression<String> name;
  
  protected void execute(Event event)
  {
    if (!ParticleProjectileHandler.particleProjectiles.containsKey(this.name.getSingle(event))) {
      ParticleProjectile particleProjectile = new ParticleProjectile((ParticleEnum)this.particle.getSingle(event), (String)this.name.getSingle(event));
      ParticleProjectileHandler.particleProjectiles.put(this.name.getSingle(event), particleProjectile);
    } else {
      Skript.error(Skript.SKRIPT_PREFIX + "Particle Projectile already exists with that name!");
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Register New Particle Projectile";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.particle = expressions[0];
    this.name = expressions[1];
    return true;
  }
}
