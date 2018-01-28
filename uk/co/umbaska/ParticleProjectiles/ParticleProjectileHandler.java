package uk.co.umbaska.ParticleProjectiles;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import uk.co.umbaska.ParticleProjectiles.Effects.EffNewParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.Effects.EffStopParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.Effects.EffUnpauseParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.Expressions.ExprCurrentLocation;
import uk.co.umbaska.ParticleProjectiles.Expressions.ExprParticleType;
import uk.co.umbaska.ParticleProjectiles.Expressions.ExprStartLocation;
import uk.co.umbaska.ParticleProjectiles.Expressions.ExprTicksPerSecond;
import uk.co.umbaska.ParticleProjectiles.Expressions.ExprYOffset;
import uk.co.umbaska.Utils.EnumClassInfo;

public class ParticleProjectileHandler
{
  public static HashMap<String, ParticleProjectile> particleProjectiles = new HashMap();
  


  public ParticleProjectileHandler()
  {
    EnumClassInfo.create(ProjectileType.class, "particleprojectiletype").register();
    
    Skript.registerEvent("Particle Hit", SimpleEvent.class, ParticleProjectileHitEvent.class, new String[] { "particle projectile hit [event]" });
    EventValues.registerEventValue(ParticleProjectileHitEvent.class, Entity.class, new Getter()
    {
      public Entity get(ParticleProjectileHitEvent event) { return event.getVictim(); } }, 0);
    


    EventValues.registerEventValue(ParticleProjectileHitEvent.class, String.class, new Getter()
    {
      public String get(ParticleProjectileHitEvent event) { return event.getParticleName(); } }, 0);
    


    Skript.registerEvent("Particle Land", SimpleEvent.class, ParticleProjectileLandEvent.class, new String[] { "particle projectile land [event]" });
    EventValues.registerEventValue(ParticleProjectileLandEvent.class, Location.class, new Getter()
    {
      public Location get(ParticleProjectileLandEvent event) { return event.getLocation(); } }, 0);
    

    EventValues.registerEventValue(ParticleProjectileLandEvent.class, String.class, new Getter()
    {
      public String get(ParticleProjectileLandEvent event) { return event.getParticleName(); } }, 0);
    




    Skript.registerEffect(EffNewParticleProjectile.class, new String[] { "(create|register) [new] particle projectile (using|of) %particleenum%[ and][ id] %string%" });
    Skript.registerEffect(uk.co.umbaska.ParticleProjectiles.Effects.EffRemoveParticleProjectile.class, new String[] { "(delete|un[-]register) particle projectile %string%" });
    Skript.registerEffect(uk.co.umbaska.ParticleProjectiles.Effects.EffPauseInPlaceParticleProjectile.class, new String[] { "pause [in place ]particle projectile %string%[ in place]" });
    Skript.registerEffect(uk.co.umbaska.ParticleProjectiles.Effects.EffPauseParticleProjectile.class, new String[] { "pause particle projectile %string%" });
    Skript.registerEffect(EffUnpauseParticleProjectile.class, new String[] { "unpause particle projectile %string%" });
    Skript.registerEffect(uk.co.umbaska.ParticleProjectiles.Effects.EffStartParticleProjectile.class, new String[] { "(start|run) particle projectile %string%" });
    Skript.registerEffect(EffStopParticleProjectile.class, new String[] { "stop particle projectile %string%" });
    Skript.registerEffect(uk.co.umbaska.ParticleProjectiles.Effects.EffMakeShootParticleProjectile.class, new String[] { "(force|make) %entity% shoot particle projectile %string% with speed %number%" });
    


    SimplePropertyExpression.register(ExprStartLocation.class, Location.class, "[particle projectile] start location", "string");
    SimplePropertyExpression.register(ExprCurrentLocation.class, Location.class, "[particle projectile] current location", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprGravity.class, Number.class, "[particle projectile] gravity", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprParticleCount.class, Number.class, "[particle projectile] particle count", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprParticleData1.class, Number.class, "[particle projectile] particle data 1", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprParticleData2.class, Number.class, "[particle projectile] particle data 2", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprParticleSpeed.class, Number.class, "[particle projectile] particle speed", "string");
    SimplePropertyExpression.register(ExprParticleType.class, uk.co.umbaska.Enums.ParticleEnum.class, "[particle projectile] particle type", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprProjectileVector.class, org.bukkit.util.Vector.class, "[particle projectile] (vector|velocity)", "string");
    SimplePropertyExpression.register(ExprTicksPerSecond.class, Number.class, "[particle projectile] (ticks per second|tps)", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprXOffset.class, Number.class, "[particle projectile] x offset", "string");
    SimplePropertyExpression.register(ExprYOffset.class, Number.class, "[particle projectile] y offset", "string");
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprZOffset.class, Number.class, "[particle projectile] z offset", "string");
    
    SimplePropertyExpression.register(uk.co.umbaska.ParticleProjectiles.Expressions.ExprProjectileType.class, ProjectileType.class, "[particle projectile] projectile type", "string");
    
    Skript.registerExpression(uk.co.umbaska.ParticleProjectiles.Expressions.ExprCurrentParticleProjectile.class, String.class, ExpressionType.SIMPLE, new String[] { "([event-]particle projectile|name of [event-]particle projectile)" });
    Skript.registerExpression(uk.co.umbaska.ParticleProjectiles.Expressions.ExprAllParticleProjectiles.class, String.class, ExpressionType.COMBINED, new String[] { "all particle projectiles" });
    Skript.registerExpression(uk.co.umbaska.ParticleProjectiles.Expressions.ExprHasStopped.class, Boolean.class, ExpressionType.SIMPLE, new String[] { "running state of [particle projectile] %string%" });
  }
}
