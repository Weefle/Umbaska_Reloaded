package uk.co.umbaska.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;


public class Scatter
{
  World world;
  final Plugin p;
  int radius;
  int x;
  int z;
  int delay = 20;
  Entity[] players;
  ItemStack[] badBlocks;
  
  public Scatter(Plugin p, World world, int radius, int x, int z, ItemStack[] badBlocks, int delay, Entity[] players) {
    this.world = world;
    this.p = p;
    this.radius = radius;
    this.x = x;
    this.z = z;
    this.badBlocks = badBlocks;
    this.delay = delay;
    this.players = players;
    scatter();
  }
  
  public Scatter(Plugin p, int radius, Location loc, ItemStack[] badBlocks, int delay, Entity[] players) {
    this.world = loc.getWorld();
    this.p = p;
    this.radius = radius;
    this.x = loc.getBlockX();
    this.z = loc.getBlockZ();
    this.badBlocks = badBlocks;
    this.delay = delay;
    this.players = players;
    scatter();
  }
  
  private void scatter() {
    Random random = new Random();
    final double randomAngle = random.nextDouble() * 3.141592653589793D * 2.0D;
    double newradius = this.radius * random.nextDouble();
    final int[] coords = convertFromRadiansToBlock(newradius, randomAngle);
    Bukkit.getScheduler().runTaskTimer(this.p, new Runnable() {
      int count = -1;
      
      public void run() {
        this.count += 1;
        Entity e = Scatter.this.players[this.count];
        Scatter.this.scatterPlayerRandom(e, randomAngle, coords, this.val$coords); } }, this.delay, this.delay);
  }
  



  public void scatterPlayerRandom(final Entity p, final double angle, double newrad, final int[] coords)
  {
    final Location finalTeleport = new Location(this.world, 0.0D, 0.0D, 0.0D);
    
    finalTeleport.setX(coords[0]);
    finalTeleport.setZ(coords[1]);
    
    finalTeleport.setX(finalTeleport.getX() + this.x);
    finalTeleport.setZ(finalTeleport.getZ() + this.z);
    
    finalTeleport.setX(Math.round(finalTeleport.getX()) + 0.5D);
    finalTeleport.setZ(Math.round(finalTeleport.getZ()) + 0.5D);
    if (!this.world.getChunkAt(finalTeleport).isLoaded()) {
      this.world.getChunkAt(finalTeleport).load(true);
    }
    final Location loc = finalTeleport;
    Plugin plug = this.p;
    final World worlds = this.world;
    Bukkit.getScheduler().runTaskLater(this.p, new Runnable() {
      Location finalTeleport2 = loc;
      
      public void run() {
        worlds.getChunkAt(this.finalTeleport2).load(true);
        this.finalTeleport2.setY(Scatter.this.getSafeY(this.finalTeleport2));
        Location blockUnder = this.finalTeleport2;
        blockUnder.setY(blockUnder.getBlockY() - 1);
        List<Material> banned = new ArrayList();
        if (Scatter.this.badBlocks.length > 0) {
          for (ItemStack b : Scatter.this.badBlocks) {
            banned.add(b.getType());
          }
        }
        if (this.finalTeleport2.getBlock().getType() != Material.AIR)
        {
          this.finalTeleport2.setY(Scatter.this.getSafeY(this.finalTeleport2));
        }
        for (Material ban : banned) {
          if ((blockUnder.getBlock().getType() == ban) || (this.finalTeleport2.getBlock().getType() == ban)) {
            Scatter.this.scatterPlayerRandom(p, angle, coords, finalTeleport);
            return;
          }
        }
        Bukkit.getScheduler().runTaskLater(this.val$plug, new Runnable()
        {

          public void run() { Scatter.2.this.val$p.teleport(Scatter.2.this.val$finalTeleport); } }, 20L); } }, 40L);
  }
  







  private int getSafeY(Location loc)
  {
    return loc.getWorld().getHighestBlockYAt(loc);
  }
  
  private int[] convertFromRadiansToBlock(double radius, double angle)
  {
    return new int[] { (int)Math.round(radius * Math.cos(angle)), (int)Math.round(radius * Math.sin(angle)) };
  }
}
