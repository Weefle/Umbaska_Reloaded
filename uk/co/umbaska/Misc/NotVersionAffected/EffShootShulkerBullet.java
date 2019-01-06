package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.Event;
import org.bukkit.projectiles.ProjectileSource;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffShootShulkerBullet
  extends Effect
{
  private Expression<Entity> shooter;
  private Expression<Entity> target;
  
  protected void execute(Event event)
  {
    Entity shooter = (Entity)this.shooter.getSingle(event);
    Entity target = (Entity)this.target.getSingle(event);
    ShulkerBullet bul = (ShulkerBullet)shooter.getWorld().spawnEntity(shooter.getLocation(), EntityType.SHULKER_BULLET);
    bul.setTarget(target);
    bul.setShooter((ProjectileSource)shooter);
  }
  
  public String toString(Event event, boolean b)
  {
    return null;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.shooter = (Expression<Entity>) expressions[0];
    this.target = (Expression<Entity>) expressions[1];
    return true;
  }
}
