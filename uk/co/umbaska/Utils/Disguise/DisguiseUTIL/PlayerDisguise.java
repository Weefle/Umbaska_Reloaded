package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutAnimation;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntity;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_9_R2.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_9_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;
import net.minecraft.server.v1_9_R2.WorldServer;

public class PlayerDisguise
  extends Disguise
{
  private String name;
  private EntityPlayer ep;
  
  public PlayerDisguise(Entity p, String name)
  {
    super(p, EntityType.PLAYER);
    this.name = ChatColor.translateAlternateColorCodes('&', name);
  }
  
  public void applyDisguise(Collection<Player> players)
  {
    UUID uid = new UUIDRetriever(this.name).getUUID();
    GameProfile gp = new GameProfile(uid == null ? UUID.randomUUID() : uid, this.name);
    
    UUIDRetriever.setSkin(gp);
    EntityPlayer playerEntity = ((CraftPlayer)getPlayer()).getHandle();
    EntityPlayer ep = this.ep == null ? (this.ep = new EntityPlayer(playerEntity.server, (WorldServer)playerEntity.world, gp, new PlayerInteractManager(playerEntity.world))) : this.ep;
    
    ep.inventory = playerEntity.inventory;
    ep.locX = playerEntity.locX;
    ep.locY = playerEntity.locY;
    ep.locZ = playerEntity.locZ;
    ep.yaw = playerEntity.yaw;
    ep.pitch = playerEntity.pitch;
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { playerEntity.getId() });
    
    PacketPlayOutPlayerInfo infoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { playerEntity });
    
    PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { ep });
    
    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(ep);
    for (Player p : players) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        PlayerConnection pc = aep.playerConnection;
        pc.sendPacket(destroy);
        pc.sendPacket(infoRemove);
        pc.sendPacket(infoAdd);
        pc.sendPacket(spawn);
      }
    }
  }
  
  public void revertDisguise(Collection<Player> players)
  {
    EntityPlayer playerEntity = ((CraftPlayer)getPlayer()).getHandle();
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { this.ep.getId() });
    
    PacketPlayOutPlayerInfo infoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { this.ep });
    
    PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { playerEntity });
    
    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(playerEntity);
    for (Player p : players) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        PlayerConnection pc = aep.playerConnection;
        pc.sendPacket(destroy);
        pc.sendPacket(infoRemove);
        pc.sendPacket(infoAdd);
        pc.sendPacket(spawn);
      }
    }
  }
  
  public void sneak(boolean sneak)
  {
    this.ep.setSneaking(sneak);
    PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(this.ep.getId(), this.ep.getDataWatcher(), true);
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(meta);
      }
    }
  }
  
  public void swingArm()
  {
    PacketPlayOutAnimation animation = new PacketPlayOutAnimation(this.ep, 0);
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(animation);
      }
    }
  }
  
  public void setItemInHand(int slot)
  {
    PacketPlayOutHeldItemSlot held = new PacketPlayOutHeldItemSlot(slot);
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(held);
      }
    }
  }
  
  public void move(Location old, Location newloc)
  {
    PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook moveLook = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.ep.getId(), (byte)((newloc.getBlockX() - old.getBlockX()) * 32), (byte)((newloc.getBlockY() - old.getBlockY()) * 32), (byte)((newloc.getBlockZ() - old.getBlockZ()) * 32), (byte)0, (byte)0, this.ep.getBukkitEntity().isOnGround());
    
    PacketPlayOutEntity.PacketPlayOutEntityLook look = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.ep.getId(), (byte)(int)(newloc.getYaw() * 256.0F / 360.0F), (byte)(int)(newloc.getPitch() * 256.0F / 360.0F), this.ep.getBukkitEntity().isOnGround());
    
    PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(this.ep, (byte)(int)(newloc.getYaw() * 256.0F / 360.0F));
    
    this.ep.yaw = newloc.getYaw();
    this.ep.pitch = newloc.getPitch();
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(moveLook);
        aep.playerConnection.sendPacket(look);
        aep.playerConnection.sendPacket(rotation);
      }
    }
  }
  
  public String getName()
  {
    return this.name;
  }
}
