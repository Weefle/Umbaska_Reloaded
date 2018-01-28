package uk.co.umbaska.ParticleProjectiles.Effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.HashMap;
import org.bukkit.event.Event;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;
import uk.co.umbaska.ParticleProjectiles.UmbError;


public class EffStartParticleProjectile
  extends Effect
{
  private Expression<String> name;
  
  protected void execute(Event event)
  {
    if (ParticleProjectileHandler.particleProjectiles.containsKey(this.name.getSingle(event))) {
      try {
        ((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(this.name.getSingle(event))).start();
      } catch (UmbError e) {
        e.printStackTrace();
      }
    } else {
      Skript.error(Skript.SKRIPT_PREFIX + "Particle Projectile doesn't exist");
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Start Particle Projectile";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = expressions[0];
    return true;
  }
}
