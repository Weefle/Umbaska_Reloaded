package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.util.Collection;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_9_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitTask;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.Disguise.DisguiseAPI;

public class EntityDisguise extends Disguise
{
  private EntityLiving living;
  
  public EntityDisguise(org.bukkit.entity.Entity p, EntityType type)
  {
    super(p, type);
    new DisguisedEntityTracker((CraftEntity)getPlayer());
  }
  
  public class DisguisedEntityTracker {
    CraftEntity e;
    BukkitTask task;
    
    public DisguisedEntityTracker(CraftEntity ent) {
      this.e = ent;
      final DisguiseAPI disAPI = (DisguiseAPI)Main.disguiseAPI;
      

      this.task = org.bukkit.Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
        Location prevLocation = EntityDisguise.DisguisedEntityTracker.this.e.getLocation();
        
        public void run()
        {
          if ((EntityDisguise.DisguisedEntityTracker.this.e.isDead()) || (((EntityLiving)EntityDisguise.DisguisedEntityTracker.this.e.getHandle()).getHealth() <= 0.0D)) {
            disAPI.unDisguisePlayer(EntityDisguise.DisguisedEntityTracker.this.e);
            disAPI.getDisguise(EntityDisguise.DisguisedEntityTracker.this.e).revertDisguise(disAPI.online());
            disAPI.refresh();
            
            EntityDisguise.DisguisedEntityTracker.this.task.cancel();
          }
          if (disAPI.getDisguise(EntityDisguise.DisguisedEntityTracker.this.e) != null)
          {
            Disguise dis = disAPI.getDisguise(EntityDisguise.DisguisedEntityTracker.this.e);
            
            dis.move(this.prevLocation, EntityDisguise.DisguisedEntityTracker.this.e.getLocation());
            
            this.prevLocation = EntityDisguise.DisguisedEntityTracker.this.e.getLocation();

          }
          else
          {
            EntityDisguise.DisguisedEntityTracker.this.task.cancel(); } } }, 1L, 1L);
    }
  }
  



  public String getName()
  {
    String name = "Pig";
    switch (this.living.getBukkitEntity().getType()) {
    case CAVE_SPIDER: 
      name = "Cave Spider";
      break;
    case HORSE: 
      name = "Horse";
      break;
    case IRON_GOLEM: 
      name = "Iron Golem";
      break;
    case MAGMA_CUBE: 
      name = "Magma Cube";
      break;
    case MINECART: 
      name = "Minecart";
      break;
    case MINECART_CHEST: 
      name = "Minecart w/ Chest";
      break;
    case MINECART_COMMAND: 
      name = "Minecart w/ Command Block";
      break;
    case MINECART_FURNACE: 
      name = "Minecart w/ Furnace";
      break;
    case MINECART_HOPPER: 
      name = "Minecart w/ Hopper";
      break;
    case MINECART_MOB_SPAWNER: 
      name = "Minecart w/ Spawner";
      break;
    case MINECART_TNT: 
      name = "Minecart w/ TNT";
      break;
    case MUSHROOM_COW: 
      name = "Mooshroom";
      break;
    case OCELOT: 
      name = "Ocelot";
      break;
    case PAINTING: 
      name = "Painting";
      break;
    case PIG_ZOMBIE: 
      name = "Zombie Pigman";
      break;
    case SKELETON: 
      Skeleton skeleton = (Skeleton)this.living.getBukkitEntity();
      name = skeleton.getSkeletonType() == org.bukkit.entity.Skeleton.SkeletonType.NORMAL ? "Skeleton" : "Wither Skeleton";
      
      break;
    case SNOWMAN: 
      name = "Snow Golem";
      break;
    case WITHER: 
      name = "Wither Boss";
      break;
    default: 
      name = this.living.getBukkitEntity().getType().getName();
    }
    
    return name;
  }
  

  public void applyDisguise(Collection<Player> players)
  {
    net.minecraft.server.v1_9_R1.Entity ep = ((CraftEntity)getPlayer()).getHandle();
    EntityLiving ent = convert(getType());
    ent.setLocation(ep.locX, ep.locY, ep.locZ, ep.yaw, ep.pitch);
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { ep.getId() });
    PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(ent);
    for (Player p : players)
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        PlayerConnection pc = aep.playerConnection;
        pc.sendPacket(destroy);
        pc.sendPacket(spawn);
      }
    this.living = ent;
  }
  
  public void revertDisguise(Collection<Player> players)
  {
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { this.living.getId() });
    
    EntityLiving ep = (EntityLiving)getPlayer();
    net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo infoAdd = null;
    if (getPlayer().getType() == EntityType.PLAYER) {
      infoAdd = new net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo(net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { ((CraftPlayer)getPlayer()).getHandle() });
    }
    PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(ep);
    for (Player p : players) {
      if ((getPlayer().getType() != EntityType.PLAYER) || 
        (p != (Player)getPlayer()))
      {

        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        PlayerConnection pc = aep.playerConnection;
        pc.sendPacket(destroy);
        if (infoAdd != null) {
          pc.sendPacket(infoAdd);
        }
        pc.sendPacket(spawn);
      }
    }
  }
  
  public void move(Location old, Location newloc)
  {
    net.minecraft.server.v1_9_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook moveLook = new net.minecraft.server.v1_9_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.living.getId(), (byte)((newloc.getBlockX() - old.getBlockX()) * 32), (byte)((newloc.getBlockY() - old.getBlockY()) * 32), (byte)((newloc.getBlockZ() - old.getBlockZ()) * 32), (byte)(int)newloc.getYaw(), (byte)(int)newloc.getPitch(), getPlayer().isOnGround());
    





    PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(this.living, (byte)(int)(newloc.getYaw() * 256.0F / 360.0F));
    
    for (Player p : org.bukkit.Bukkit.getServer().getOnlinePlayers())
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(moveLook);
        aep.playerConnection.sendPacket(rotation);
      }
  }
  
  private EntityLiving convert(EntityType type) {
    EntityLiving living = null;
    net.minecraft.server.v1_9_R1.Entity ep = ((CraftEntity)getPlayer()).getHandle();
    net.minecraft.server.v1_9_R1.World world = ep.getWorld();
    switch (type) {
    case BAT: 
      net.minecraft.server.v1_9_R1.EntityBat bat = new net.minecraft.server.v1_9_R1.EntityBat(world);
      living = bat;
      break;
    case BLAZE: 
      living = new net.minecraft.server.v1_9_R1.EntityBlaze(world);
      break;
    case CAVE_SPIDER: 
      living = new net.minecraft.server.v1_9_R1.EntityCaveSpider(world);
      break;
    case CHICKEN: 
      living = new net.minecraft.server.v1_9_R1.EntityChicken(world);
      break;
    case COW: 
      living = new net.minecraft.server.v1_9_R1.EntityCow(world);
      break;
    case CREEPER: 
      living = new net.minecraft.server.v1_9_R1.EntityCreeper(world);
      break;
    case ENDERMAN: 
      living = new net.minecraft.server.v1_9_R1.EntityEnderman(world);
      break;
    case ENDERMITE: 
      living = new net.minecraft.server.v1_9_R1.EntityEndermite(world);
      break;
    


    case ENDER_DRAGON: 
      living = new net.minecraft.server.v1_9_R1.EntityEnderDragon(world);
      break;
    case GHAST: 
      living = new net.minecraft.server.v1_9_R1.EntityGhast(world);
      break;
    case GIANT: 
      living = new net.minecraft.server.v1_9_R1.EntityGiantZombie(world);
      break;
    case GUARDIAN: 
      living = new net.minecraft.server.v1_9_R1.EntityGuardian(world);
      break;
    case HORSE: 
      living = new net.minecraft.server.v1_9_R1.EntityHorse(world);
      break;
    case IRON_GOLEM: 
      living = new net.minecraft.server.v1_9_R1.EntityIronGolem(world);
      break;
    case MAGMA_CUBE: 
      living = new net.minecraft.server.v1_9_R1.EntityMagmaCube(world);
      break;
    


    case MUSHROOM_COW: 
      living = new net.minecraft.server.v1_9_R1.EntityMushroomCow(world);
      break;
    case OCELOT: 
      living = new net.minecraft.server.v1_9_R1.EntityOcelot(world);
      break;
    case PIG: 
      living = new net.minecraft.server.v1_9_R1.EntityPig(world);
      break;
    case PIG_ZOMBIE: 
      living = new net.minecraft.server.v1_9_R1.EntityPigZombie(world);
      break;
    case RABBIT: 
      living = new net.minecraft.server.v1_9_R1.EntityRabbit(world);
      break;
    case SHEEP: 
      living = new net.minecraft.server.v1_9_R1.EntitySheep(world);
      break;
    case SILVERFISH: 
      living = new net.minecraft.server.v1_9_R1.EntitySilverfish(world);
      break;
    case SKELETON: 
      living = new net.minecraft.server.v1_9_R1.EntitySkeleton(world);
      break;
    case SLIME: 
      living = new net.minecraft.server.v1_9_R1.EntitySlime(world);
      break;
    case SNOWMAN: 
      living = new net.minecraft.server.v1_9_R1.EntitySnowman(world);
      break;
    case SPIDER: 
      living = new net.minecraft.server.v1_9_R1.EntitySpider(world);
      break;
    case SQUID: 
      living = new net.minecraft.server.v1_9_R1.EntitySquid(world);
      break;
    case VILLAGER: 
      living = new net.minecraft.server.v1_9_R1.EntityVillager(world);
      break;
    case WITCH: 
      living = new net.minecraft.server.v1_9_R1.EntityWitch(world);
      break;
    case WITHER: 
      living = new net.minecraft.server.v1_9_R1.EntityWither(world);
      break;
    case WOLF: 
      living = new net.minecraft.server.v1_9_R1.EntityWolf(world);
      break;
    case ZOMBIE: 
      living = new net.minecraft.server.v1_9_R1.EntityZombie(world);
      break;
    case MINECART: case MINECART_CHEST: case MINECART_COMMAND: case MINECART_FURNACE: case MINECART_HOPPER: case MINECART_MOB_SPAWNER: case MINECART_TNT: case PAINTING: default: 
      living = new net.minecraft.server.v1_9_R1.EntityPig(world);
    }
    
    return living;
  }
  
  public EntityLiving getEntity() {
    return this.living;
  }
}
