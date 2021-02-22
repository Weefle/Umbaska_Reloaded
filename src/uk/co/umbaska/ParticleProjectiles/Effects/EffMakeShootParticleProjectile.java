package uk.co.umbaska.ParticleProjectiles.Effects;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;
import uk.co.umbaska.ParticleProjectiles.UmbError;

public class EffMakeShootParticleProjectile
  extends Effect
{
  private Expression<Entity> shooter;
  private Expression<String> name;
  private Expression<Number> speed;
  
  protected void execute(Event event)
  {
    if (ParticleProjectileHandler.particleProjectiles.containsKey(this.name.getSingle(event))) {
      ParticleProjectile particleProjectile = (ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(this.name.getSingle(event));
      Vector v = getDirection(((Entity)this.shooter.getSingle(event)).getLocation().getYaw(), ((Entity)this.shooter.getSingle(event)).getLocation().getPitch());
      particleProjectile.setVector(v.multiply(((Number)this.speed.getSingle(event)).doubleValue()));
      try {
        particleProjectile.start();
      } catch (UmbError e) {
        e.printStackTrace();
      }
    } else {
      Skript.error(Skript.SKRIPT_PREFIX + "Particle Projectile doesn't exist");
    }
  }
  
  public static Vector getDirection(float yaw, float pitch) {
    Vector vector = new Vector();
    double rotX = 0.017453292F * yaw;
    double rotY = 0.017453292F * pitch;
    vector.setY(-Math.sin(rotY));
    double h = Math.cos(rotY);
    vector.setX(-h * Math.sin(rotX));
    vector.setZ(h * Math.cos(rotX));
    return vector;
  }
  

  public String toString(Event event, boolean b)
  {
    return "Start Particle Projectile";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.name = (Expression<String>) expressions[1];
    this.shooter = (Expression<Entity>) expressions[0];
    this.speed = (Expression<Number>) expressions[2];
    return true;
  }
}
