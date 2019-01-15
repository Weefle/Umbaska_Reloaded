package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitTask;

import net.minecraft.server.v1_9_R2.EntityBat;
import net.minecraft.server.v1_9_R2.EntityBlaze;
import net.minecraft.server.v1_9_R2.EntityCaveSpider;
import net.minecraft.server.v1_9_R2.EntityChicken;
import net.minecraft.server.v1_9_R2.EntityCow;
import net.minecraft.server.v1_9_R2.EntityCreeper;
import net.minecraft.server.v1_9_R2.EntityEnderDragon;
import net.minecraft.server.v1_9_R2.EntityEnderman;
import net.minecraft.server.v1_9_R2.EntityEndermite;
import net.minecraft.server.v1_9_R2.EntityGhast;
import net.minecraft.server.v1_9_R2.EntityGiantZombie;
import net.minecraft.server.v1_9_R2.EntityGuardian;
import net.minecraft.server.v1_9_R2.EntityHorse;
import net.minecraft.server.v1_9_R2.EntityIronGolem;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.EntityMagmaCube;
import net.minecraft.server.v1_9_R2.EntityMushroomCow;
import net.minecraft.server.v1_9_R2.EntityOcelot;
import net.minecraft.server.v1_9_R2.EntityPig;
import net.minecraft.server.v1_9_R2.EntityPigZombie;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.EntityRabbit;
import net.minecraft.server.v1_9_R2.EntitySheep;
import net.minecraft.server.v1_9_R2.EntitySilverfish;
import net.minecraft.server.v1_9_R2.EntitySkeleton;
import net.minecraft.server.v1_9_R2.EntitySlime;
import net.minecraft.server.v1_9_R2.EntitySnowman;
import net.minecraft.server.v1_9_R2.EntitySpider;
import net.minecraft.server.v1_9_R2.EntitySquid;
import net.minecraft.server.v1_9_R2.EntityVillager;
import net.minecraft.server.v1_9_R2.EntityWitch;
import net.minecraft.server.v1_9_R2.EntityWither;
import net.minecraft.server.v1_9_R2.EntityWolf;
import net.minecraft.server.v1_9_R2.EntityZombie;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntity;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import net.minecraft.server.v1_9_R2.World;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.Disguise.DisguiseAPI;

public class EntityDisguise
  extends Disguise
{
  private EntityLiving living;
  
  public EntityDisguise(org.bukkit.entity.Entity p, EntityType type)
  {
    super(p, type);
    new DisguisedEntityTracker((CraftEntity)getPlayer());
  }
  
  public class DisguisedEntityTracker
  {
    CraftEntity e;
    BukkitTask task;
    
    public DisguisedEntityTracker(CraftEntity ent)
    {
      this.e = ent;
      final DisguiseAPI disAPI = (DisguiseAPI)Main.disguiseAPI;
      
      this.task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
      {
        Location prevLocation = EntityDisguise.DisguisedEntityTracker.this.e.getLocation();
        
        public void run()
        {
          if ((EntityDisguise.DisguisedEntityTracker.this.e.isDead()) || (((EntityLiving)EntityDisguise.DisguisedEntityTracker.this.e.getHandle()).getHealth() <= 0.0D))
          {
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
            EntityDisguise.DisguisedEntityTracker.this.task.cancel();
          }
        }
      }, 1L, 1L);
    }
  }
  
  @SuppressWarnings("deprecation")
public String getName()
  {
    String name = "Pig";
    switch (this.living.getBukkitEntity().getType())
    {
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
      name = skeleton.getSkeletonType() == Skeleton.SkeletonType.NORMAL ? "Skeleton" : "Wither Skeleton";
      
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
    net.minecraft.server.v1_9_R2.Entity ep = ((CraftEntity)getPlayer()).getHandle();
    EntityLiving ent = convert(getType());
    ent.setLocation(ep.locX, ep.locY, ep.locZ, ep.yaw, ep.pitch);
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { ep.getId() });
    PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(ent);
    for (Player p : players) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        PlayerConnection pc = aep.playerConnection;
        pc.sendPacket(destroy);
        pc.sendPacket(spawn);
      }
    }
    this.living = ent;
  }
  
  public void revertDisguise(Collection<Player> players)
  {
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { this.living.getId() });
    
    EntityLiving ep = (EntityLiving)getPlayer();
    PacketPlayOutPlayerInfo infoAdd = null;
    if (getPlayer().getType() == EntityType.PLAYER) {
      infoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { ((CraftPlayer)getPlayer()).getHandle() });
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
    PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook moveLook = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.living.getId(), (byte)((newloc.getBlockX() - old.getBlockX()) * 32), (byte)((newloc.getBlockY() - old.getBlockY()) * 32), (byte)((newloc.getBlockZ() - old.getBlockZ()) * 32), (byte)(int)newloc.getYaw(), (byte)(int)newloc.getPitch(), getPlayer().isOnGround());
    
    PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(this.living, (byte)(int)(newloc.getYaw() * 256.0F / 360.0F));
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(moveLook);
        aep.playerConnection.sendPacket(rotation);
      }
    }
  }
  
  private EntityLiving convert(EntityType type)
  {
    EntityLiving living = null;
    net.minecraft.server.v1_9_R2.Entity ep = ((CraftEntity)getPlayer()).getHandle();
    World world = ep.getWorld();
    switch (type)
    {
    case BAT: 
      EntityBat bat = new EntityBat(world);
      living = bat;
      break;
    case BLAZE: 
      living = new EntityBlaze(world);
      break;
    case CAVE_SPIDER: 
      living = new EntityCaveSpider(world);
      break;
    case CHICKEN: 
      living = new EntityChicken(world);
      break;
    case COW: 
      living = new EntityCow(world);
      break;
    case CREEPER: 
      living = new EntityCreeper(world);
      break;
    case ENDERMAN: 
      living = new EntityEnderman(world);
      break;
    case ENDERMITE: 
      living = new EntityEndermite(world);
      break;
    case ENDER_DRAGON: 
      living = new EntityEnderDragon(world);
      break;
    case GHAST: 
      living = new EntityGhast(world);
      break;
    case GIANT: 
      living = new EntityGiantZombie(world);
      break;
    case GUARDIAN: 
      living = new EntityGuardian(world);
      break;
    case HORSE: 
      living = new EntityHorse(world);
      break;
    case IRON_GOLEM: 
      living = new EntityIronGolem(world);
      break;
    case MAGMA_CUBE: 
      living = new EntityMagmaCube(world);
      break;
    case MUSHROOM_COW: 
      living = new EntityMushroomCow(world);
      break;
    case OCELOT: 
      living = new EntityOcelot(world);
      break;
    case PIG: 
      living = new EntityPig(world);
      break;
    case PIG_ZOMBIE: 
      living = new EntityPigZombie(world);
      break;
    case RABBIT: 
      living = new EntityRabbit(world);
      break;
    case SHEEP: 
      living = new EntitySheep(world);
      break;
    case SILVERFISH: 
      living = new EntitySilverfish(world);
      break;
    case SKELETON: 
      living = new EntitySkeleton(world);
      break;
    case SLIME: 
      living = new EntitySlime(world);
      break;
    case SNOWMAN: 
      living = new EntitySnowman(world);
      break;
    case SPIDER: 
      living = new EntitySpider(world);
      break;
    case SQUID: 
      living = new EntitySquid(world);
      break;
    case VILLAGER: 
      living = new EntityVillager(world);
      break;
    case WITCH: 
      living = new EntityWitch(world);
      break;
    case WITHER: 
      living = new EntityWither(world);
      break;
    case WOLF: 
      living = new EntityWolf(world);
      break;
    case ZOMBIE: 
      living = new EntityZombie(world);
      break;
    case MINECART: 
    case MINECART_CHEST: 
    case MINECART_COMMAND: 
    case MINECART_FURNACE: 
    case MINECART_HOPPER: 
    case MINECART_MOB_SPAWNER: 
    case MINECART_TNT: 
    case PAINTING: 
    default: 
      living = new EntityPig(world);
    }
    return living;
  }
  
  public EntityLiving getEntity()
  {
    return this.living;
  }
}
