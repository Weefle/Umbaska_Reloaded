package uk.co.umbaska.ParticleProjectiles;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import uk.co.umbaska.Enums.ParticleEnum;
import uk.co.umbaska.Main;
import uk.co.umbaska.Replacers.ParticleFunction;









public class ParticleProjectile
{
  Integer count = Integer.valueOf(25); Integer particleData1 = Integer.valueOf(0); Integer particleData2 = Integer.valueOf(0); Integer particleSpeed = Integer.valueOf(0);
  
  Boolean running = Boolean.valueOf(false); Boolean paused = Boolean.valueOf(false); Boolean pausedInPlace = Boolean.valueOf(false);
  



  int ticksPerSecond = 20;
  String name;
  
  public ParticleProjectile(ParticleEnum particleType, String name) { this.particleType = particleType;
    this.running = Boolean.valueOf(false);
    this.name = name; }
  
  ParticleEnum particleType;
  
  public void start() throws UmbError { if (this.startLocation == null) {
      throw new UmbError("Start Location not provided");
    }
    if (!isRunning()) {
      this.running = Boolean.valueOf(true);
      this.boundingBox = new BoundingBox(getStartLocation(), getXoff().doubleValue(), getYoff().doubleValue());
      this.currentLocation = getStartLocation();
      this.projectileTracker = new ProjectileTracker();
      this.projectileTracker.run();
    }
    else {
      throw new UmbError("Already running");
    } }
  
  Vector vector;
  
  public void stop() { this.running = Boolean.valueOf(false);
    this.projectileTracker.task.cancel(); }
  
  Location startLocation;
  
  public void pause() { if (isRunning()) {
      this.paused = Boolean.valueOf(true);
      this.pausedInPlace = Boolean.valueOf(false); } }
  
  Location currentLocation;
  Double xoff;
  
  public void unpause() { if (isRunning()) {
      this.paused = Boolean.valueOf(false);
      this.pausedInPlace = Boolean.valueOf(false);
    } }
  
  Double yoff;
  
  public void pause(Boolean inPlace) { if (isRunning()) {
      this.paused = Boolean.valueOf(true);
      this.pausedInPlace = inPlace; } }
  
  Double zoff;
  Double gravity;
  
  public int getTicksPerSecond() { return this.ticksPerSecond; }
  
  ProjectileType type;
  
  public ProjectileType getType() { return this.type; }
  
  BoundingBox boundingBox;
  ProjectileTracker projectileTracker;
  public void setType(ProjectileType type) { this.type = type; }
  

  public void setTicksPerSecond(int ticksPerSecond) {
    this.ticksPerSecond = ticksPerSecond;
  }
  
  public void setGravity(Double gravity) {
    this.gravity = gravity;
  }
  
  public Double getGravity() {
    return this.gravity;
  }
  
  public void setStartLocation(Location location) {
    this.startLocation = location;
  }
  
  public void setParticleType(ParticleEnum particleType) {
    this.particleType = particleType;
  }
  
  public void setOffset(Double xoff, Double yoff, Double zoff) {
    this.xoff = xoff;
    this.yoff = yoff;
    this.zoff = zoff;
  }
  
  public void setParticleCount(Integer count) {
    this.count = count;
  }
  
  public void setVector(Vector v) {
    this.vector = v;
  }
  
  public boolean isRunning() {
    return this.running.booleanValue();
  }
  
  public Location getStartLocation() {
    return this.startLocation;
  }
  
  public ParticleEnum getParticleType() {
    return this.particleType;
  }
  
  public void setCurrentLocation(Location l) {
    this.currentLocation = l;
  }
  
  public Location getCurrentLocation() {
    return this.currentLocation;
  }
  
  public Vector getVector() {
    return this.vector;
  }
  
  public Integer getCount() {
    return this.count;
  }
  
  public Double getXoff() {
    return this.xoff;
  }
  
  public Double getYoff() { return this.yoff; }
  
  public Double getZoff() {
    return this.zoff;
  }
  
  public Integer getParticleData1() {
    return this.particleData1;
  }
  
  public void setParticleData1(Integer particleData1) {
    this.particleData1 = particleData1;
  }
  
  public Integer getParticleData2() {
    return this.particleData2;
  }
  
  public void setParticleData2(Integer particleData2) {
    this.particleData2 = particleData2;
  }
  
  public Integer getParticleSpeed() {
    return this.particleSpeed;
  }
  
  public void setParticleSpeed(Integer particleSpeed) {
    this.particleSpeed = particleSpeed;
  }
  
  private class ProjectileTracker implements Runnable
  {
    List<LivingEntity> entities;
    BukkitTask task;
    
    public ProjectileTracker() {
      this.task = Bukkit.getScheduler().runTaskTimer(Main.plugin, this, ParticleProjectile.this.getTicksPerSecond(), ParticleProjectile.this.getTicksPerSecond());
    }
    
    public void run()
    {
      if (ParticleProjectile.this.isRunning()) {
        if (!ParticleProjectile.this.paused.booleanValue()) {
          spawnParticles();
          ParticleProjectile.this.boundingBox = new BoundingBox(ParticleProjectile.this.getStartLocation(), ParticleProjectile.this.getXoff().doubleValue(), ParticleProjectile.this.getYoff().doubleValue());
          ParticleProjectile.this.currentLocation.add(ParticleProjectile.this.getVector());
          if (ParticleProjectile.this.getGravity().doubleValue() > 0.0D) {
            ParticleProjectile.this.setVector(ParticleProjectile.this.getVector().setY(ParticleProjectile.this.getVector().getY() - ParticleProjectile.this.getGravity().doubleValue() / ParticleProjectile.this.ticksPerSecond));
          }
          if ((ParticleProjectile.this.currentLocation.getBlock().getType() != Material.AIR) && ((ParticleProjectile.this.type == ProjectileType.STOP_ON_GROUND) || (ParticleProjectile.this.type == ProjectileType.STOP_ON_BOTH))) {
            ParticleProjectileLandEvent land = new ParticleProjectileLandEvent(ParticleProjectile.this.currentLocation, ParticleProjectile.this.name);
            Bukkit.getServer().getPluginManager().callEvent(land);
            if (!land.isCancelled()) {
              ParticleProjectile.this.running = Boolean.valueOf(false);
              ParticleProjectile.this.stop();
            }
          }
          this.entities = ParticleProjectile.this.currentLocation.getWorld().getLivingEntities();
          boolean stop = false;
          for (LivingEntity le : this.entities) {
            if ((le.getLocation().distanceSquared(ParticleProjectile.this.currentLocation) < 2.2D) || (ParticleProjectile.this.boundingBox.contains(le))) {
              ParticleProjectileHitEvent land = new ParticleProjectileHitEvent(le, ParticleProjectile.this.name);
              Bukkit.getServer().getPluginManager().callEvent(land);
              if (!land.isCancelled()) {
                stop = true;
              } else {
                stop = false;
              }
            }
          }
          if (((ParticleProjectile.this.type == ProjectileType.STOP_ON_HIT) || (ParticleProjectile.this.type == ProjectileType.STOP_ON_BOTH)) && 
            (stop)) {
            ParticleProjectile.this.running = Boolean.valueOf(false);
            ParticleProjectile.this.stop();
          }
          
          spawnParticles();
        }
        else if (ParticleProjectile.this.pausedInPlace.booleanValue()) {
          spawnParticles();
        }
      }
    }
    
    public void spawnParticles()
    {
      ParticleFunction.spawnParticle(ParticleProjectile.this.getCount(), ParticleProjectile.this.getParticleType(), ParticleProjectile.this.getParticleSpeed(), ParticleProjectile.this.getXoff(), ParticleProjectile.this.getYoff(), ParticleProjectile.this.getZoff(), new Location[] { ParticleProjectile.this.getCurrentLocation() }, ParticleProjectile.this.getParticleData1(), ParticleProjectile.this.getParticleData2());
    }
  }
}
