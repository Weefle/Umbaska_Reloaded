package uk.co.umbaska.ParticleProjectiles;

import org.bukkit.Location;

public class BoundingBox {
  org.bukkit.World world;
  double lowX;
  double lowY;
  double lowZ;
  double highX;
  double highY;
  double highZ;
  
  public BoundingBox(org.bukkit.block.Block center, double radius) { this(center.getLocation().add(0.5D, 0.0D, 0.5D), radius, radius); }
  
  public BoundingBox(Location center, double radius)
  {
    this(center, radius, radius);
  }
  
  public BoundingBox(org.bukkit.block.Block center, double horizRadius, double vertRadius) {
    this(center.getLocation().add(0.5D, 0.0D, 0.5D), horizRadius, vertRadius);
  }
  
  public BoundingBox(Location center, double horizRadius, double vertRadius) {
    this.world = center.getWorld();
    this.lowX = (center.getX() - horizRadius);
    this.lowY = (center.getY() - vertRadius);
    this.lowZ = (center.getZ() - horizRadius);
    this.highX = (center.getX() + horizRadius);
    this.highY = (center.getY() + vertRadius);
    this.highZ = (center.getZ() + horizRadius);
  }
  
  public boolean contains(Location location) {
    if (!location.getWorld().equals(this.world)) {
      return false;
    }
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    return (this.lowX <= x) && (x <= this.highX) && (this.lowY <= y) && (y <= this.highY) && (this.lowZ <= z) && (z <= this.highZ);
  }
  
  public boolean contains(org.bukkit.entity.Entity entity) {
    return contains(entity.getLocation().getBlock().getLocation().add(0.5D, 0.5D, 0.5D));
  }
  
  public boolean contains(org.bukkit.block.Block block) {
    return contains(block.getLocation().add(0.5D, 0.0D, 0.5D));
  }
}
