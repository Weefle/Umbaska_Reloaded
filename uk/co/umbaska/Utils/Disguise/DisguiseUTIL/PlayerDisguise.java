package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.util.Collection;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerDisguise extends Disguise
{
  private String name;
  private EntityPlayer ep;
  
  public PlayerDisguise(org.bukkit.entity.Entity p, String name)
  {
    super(p, org.bukkit.entity.EntityType.PLAYER);
    this.name = org.bukkit.ChatColor.translateAlternateColorCodes('&', name);
  }
  
  public void applyDisguise(Collection<Player> players)
  {
    java.util.UUID uid = new UUIDRetriever(this.name).getUUID();
    com.mojang.authlib.GameProfile gp = new com.mojang.authlib.GameProfile(uid == null ? java.util.UUID.randomUUID() : uid, this.name);
    
    UUIDRetriever.setSkin(gp);
    EntityPlayer playerEntity = ((CraftPlayer)getPlayer()).getHandle();
    EntityPlayer ep = this.ep == null ? (this.ep = new EntityPlayer(playerEntity.server, (net.minecraft.server.v1_9_R1.WorldServer)playerEntity.world, gp, new net.minecraft.server.v1_9_R1.PlayerInteractManager(playerEntity.world))) : this.ep;
    



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
  
  public void revertDisguise(Collection<Player> players) {
    EntityPlayer playerEntity = ((CraftPlayer)getPlayer()).getHandle();
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { this.ep.getId() });
    
    PacketPlayOutPlayerInfo infoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { this.ep });
    
    PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { playerEntity });
    
    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(playerEntity);
    
    for (Player p : players)
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
  
  public void sneak(boolean sneak) {
    this.ep.setSneaking(sneak);
    net.minecraft.server.v1_9_R1.PacketPlayOutEntityMetadata meta = new net.minecraft.server.v1_9_R1.PacketPlayOutEntityMetadata(this.ep.getId(), this.ep.getDataWatcher(), true);
    
    for (Player p : Bukkit.getServer().getOnlinePlayers())
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(meta);
      }
  }
  
  public void swingArm() {
    net.minecraft.server.v1_9_R1.PacketPlayOutAnimation animation = new net.minecraft.server.v1_9_R1.PacketPlayOutAnimation(this.ep, 0);
    
    for (Player p : Bukkit.getServer().getOnlinePlayers())
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(animation);
      }
  }
  
  public void setItemInHand(int slot) {
    net.minecraft.server.v1_9_R1.PacketPlayOutHeldItemSlot held = new net.minecraft.server.v1_9_R1.PacketPlayOutHeldItemSlot(slot);
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(held);
      }
    }
  }
  
  public void move(Location old, Location newloc) {
    net.minecraft.server.v1_9_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook moveLook = new net.minecraft.server.v1_9_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.ep.getId(), (byte)((newloc.getBlockX() - old.getBlockX()) * 32), (byte)((newloc.getBlockY() - old.getBlockY()) * 32), (byte)((newloc.getBlockZ() - old.getBlockZ()) * 32), (byte)0, (byte)0, this.ep.getBukkitEntity().isOnGround());
    




    net.minecraft.server.v1_9_R1.PacketPlayOutEntity.PacketPlayOutEntityLook look = new net.minecraft.server.v1_9_R1.PacketPlayOutEntity.PacketPlayOutEntityLook(this.ep.getId(), (byte)(int)(newloc.getYaw() * 256.0F / 360.0F), (byte)(int)(newloc.getPitch() * 256.0F / 360.0F), this.ep.getBukkitEntity().isOnGround());
    


    net.minecraft.server.v1_9_R1.PacketPlayOutEntityHeadRotation rotation = new net.minecraft.server.v1_9_R1.PacketPlayOutEntityHeadRotation(this.ep, (byte)(int)(newloc.getYaw() * 256.0F / 360.0F));
    
    this.ep.yaw = newloc.getYaw();
    this.ep.pitch = newloc.getPitch();
    for (Player p : Bukkit.getServer().getOnlinePlayers())
      if (p != getPlayer())
      {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(moveLook);
        aep.playerConnection.sendPacket(look);
        aep.playerConnection.sendPacket(rotation);
      }
  }
  
  public String getName() {
    return this.name;
  }
}
