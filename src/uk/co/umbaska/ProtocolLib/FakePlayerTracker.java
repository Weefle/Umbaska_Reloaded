package uk.co.umbaska.ProtocolLib;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import uk.co.umbaska.Utils.Disguise.DisguiseUTIL.UUIDRetriever;

public class FakePlayerTracker
{
  public static HashMap<String, EntityPlayer> playertracker = new HashMap<>();
  public static HashMap<String, String> displaynameholder = new HashMap<>();
  public static HashMap<String, String> skinholder = new HashMap<>();
  
  @SuppressWarnings("deprecation")
public static Boolean registerNewPlayer(String id) {
    if (playertracker.containsKey(id)) {
      return Boolean.valueOf(false);
    }
    UUID uid = new UUIDRetriever(id).getUUID();
    GameProfile gp = new GameProfile(uid == null ? UUID.randomUUID() : uid, id);
    UUIDRetriever.setSkin(gp);
    EntityPlayer newPlayer = new EntityPlayer(MinecraftServer.getServer(), (net.minecraft.server.v1_9_R2.WorldServer)MinecraftServer.getServer().getWorld(), gp, new net.minecraft.server.v1_9_R2.PlayerInteractManager(MinecraftServer.getServer().getWorld()));
    playertracker.put(id, newPlayer);
    displaynameholder.put(id, id);
    skinholder.put(id, id);
    start(id);
    return Boolean.valueOf(true);
  }
  
  public static EntityPlayer getPlayer(String id)
  {
    if (playertracker.containsKey(id)) {
      return (EntityPlayer)playertracker.get(id);
    }
    return null;
  }
  
  public static String getDisName(String id)
  {
    if (displaynameholder.containsKey(id)) {
      return (String)displaynameholder.get(id);
    }
    return null;
  }
  
  public static String getSkin(String id)
  {
    if (skinholder.containsKey(id)) {
      return (String)skinholder.get(id);
    }
    return null;
  }
  
  @SuppressWarnings("deprecation")
public static void setDisplayName(String id, String value)
  {
    EntityPlayer p = getPlayer(id);
    if (p != null) {
      UUID uid = new UUIDRetriever(getSkin(id)).getUUID();
      GameProfile gp = new GameProfile(uid == null ? UUID.randomUUID() : uid, value);
      UUIDRetriever.setSkin(gp);
      EntityPlayer newPlayer = new EntityPlayer(MinecraftServer.getServer(), (net.minecraft.server.v1_9_R2.WorldServer)MinecraftServer.getServer().getWorld(), gp, new net.minecraft.server.v1_9_R2.PlayerInteractManager(MinecraftServer.getServer().getWorld()));
      newPlayer.setLocation(p.locX, p.locY, p.locZ, p.pitch, p.yaw);
      newPlayer.world = p.getWorld();
      newPlayer.inventory = p.inventory;
      newPlayer.displayName = p.displayName;
      newPlayer.setHealth(p.getHealth());
      playertracker.remove(id);
      playertracker.put(id, newPlayer);
      displaynameholder.put(id, value);
      refresh(p, newPlayer);
    }
  }
  
  @SuppressWarnings("deprecation")
public static void setSkin(String id, String value) {
    EntityPlayer p = getPlayer(id);
    if (p != null) {
      UUID uid = new UUIDRetriever(value).getUUID();
      GameProfile gp = new GameProfile(uid == null ? UUID.randomUUID() : uid, getDisName(id));
      UUIDRetriever.setSkin(gp);
      EntityPlayer newPlayer = new EntityPlayer(MinecraftServer.getServer(), (net.minecraft.server.v1_9_R2.WorldServer)MinecraftServer.getServer().getWorld(), gp, new net.minecraft.server.v1_9_R2.PlayerInteractManager(MinecraftServer.getServer().getWorld()));
      newPlayer.setLocation(p.locX, p.locY, p.locZ, p.pitch, p.yaw);
      newPlayer.world = p.getWorld();
      newPlayer.inventory = p.inventory;
      newPlayer.displayName = p.displayName;
      newPlayer.setHealth(p.getHealth());
      playertracker.remove(id);
      playertracker.put(id, newPlayer);
      skinholder.put(id, value);
      refresh(p, newPlayer);
    }
  }
  
  public static Boolean isSneaking(String id) {
    EntityPlayer ep = getPlayer(id);
    if (ep != null) {
      return Boolean.valueOf(ep.isSneaking());
    }
    return Boolean.valueOf(false);
  }
  
  public static void sneak(String id, boolean sneak)
  {
    EntityPlayer ep = getPlayer(id);
    net.minecraft.server.v1_9_R2.PacketPlayOutEntityMetadata meta; if (ep != null) {
      ep.setSneaking(sneak);
      meta = new net.minecraft.server.v1_9_R2.PacketPlayOutEntityMetadata(ep.getId(), ep.getDataWatcher(), true);
      
      for (Player p : Bukkit.getServer().getOnlinePlayers()) {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(meta);
      }
    }
  }
  
  public static void swingArm(String id) {
    EntityPlayer ep = getPlayer(id);
    net.minecraft.server.v1_9_R2.PacketPlayOutAnimation animation; if (ep != null) {
      animation = new net.minecraft.server.v1_9_R2.PacketPlayOutAnimation(ep, 0);
      for (Player p : Bukkit.getServer().getOnlinePlayers()) {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(animation);
      }
    }
  }
  
  public static void setItemInHand(String id, int slot) {
    EntityPlayer ep = getPlayer(id);
    net.minecraft.server.v1_9_R2.PacketPlayOutHeldItemSlot held; if (ep != null) {
      held = new net.minecraft.server.v1_9_R2.PacketPlayOutHeldItemSlot(slot);
      for (Player p : Bukkit.getServer().getOnlinePlayers()) {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(held);
      }
    }
  }
  
  public static void move(String id, Location newloc) {
    EntityPlayer ep = getPlayer(id);
    if (ep != null) {
      Location loc = new Location(ep.getWorld().getWorld(), ep.locX, ep.locY, ep.locX);
      move(id, loc, newloc);
    }
  }
  
  public static void move(String id, Location old, Location newloc) {
    EntityPlayer ep = getPlayer(id);
    net.minecraft.server.v1_9_R2.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook moveLook; net.minecraft.server.v1_9_R2.PacketPlayOutEntity.PacketPlayOutEntityLook look; net.minecraft.server.v1_9_R2.PacketPlayOutEntityHeadRotation rotation; if (ep != null) {
      moveLook = new net.minecraft.server.v1_9_R2.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(ep.getId(), (byte)((newloc.getBlockX() - old.getBlockX()) * 32), (byte)((newloc.getBlockY() - old.getBlockY()) * 32), (byte)((newloc.getBlockZ() - old.getBlockZ()) * 32), (byte)0, (byte)0, ep.getBukkitEntity().isOnGround());
      




      look = new net.minecraft.server.v1_9_R2.PacketPlayOutEntity.PacketPlayOutEntityLook(ep.getId(), (byte)(int)(newloc.getYaw() * 256.0F / 360.0F), (byte)(int)(newloc.getPitch() * 256.0F / 360.0F), ep.getBukkitEntity().isOnGround());
      


      rotation = new net.minecraft.server.v1_9_R2.PacketPlayOutEntityHeadRotation(ep, (byte)(int)(newloc.getYaw() * 256.0F / 360.0F));
      
      ep.yaw = newloc.getYaw();
      ep.pitch = newloc.getPitch();
      for (Player p : Bukkit.getServer().getOnlinePlayers()) {
        EntityPlayer aep = ((CraftPlayer)p).getHandle();
        aep.playerConnection.sendPacket(moveLook);
        aep.playerConnection.sendPacket(look);
        aep.playerConnection.sendPacket(rotation);
      }
    }
  }
  
  @Deprecated
  public static void teleport(String id, Location newloc) { EntityPlayer ep = getPlayer(id);
    if (ep != null) {
      ep.setLocation(newloc.getX(), newloc.getY(), newloc.getZ(), newloc.getPitch(), newloc.getYaw());
      ep.world = ((net.minecraft.server.v1_9_R2.World)newloc.getWorld());
      
      ep.yaw = newloc.getYaw();
      ep.pitch = newloc.getPitch();
      for (Player p : Bukkit.getServer().getOnlinePlayers()) {
    	  EntityPlayer aep = ((CraftPlayer)p).getHandle();
    	  aep.playerConnection.teleport(newloc);
      }
    }
  }
  
  public static void refresh(EntityPlayer old, EntityPlayer newp) {
    net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy destroy = new net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy(new int[] { old.getId() });
    
    PacketPlayOutPlayerInfo infoRemove = new PacketPlayOutPlayerInfo(net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { old });
    
    PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { newp });
    
    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(newp);
    
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      EntityPlayer aep = ((CraftPlayer)p).getHandle();
      PlayerConnection pc = aep.playerConnection;
      pc.sendPacket(destroy);
      pc.sendPacket(infoRemove);
      pc.sendPacket(infoAdd);
      pc.sendPacket(spawn);
    }
  }
  
  public static void start(String id) {
    PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { getPlayer(id) });
    
    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(getPlayer(id));
    
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      EntityPlayer aep = ((CraftPlayer)p).getHandle();
      PlayerConnection pc = aep.playerConnection;
      pc.sendPacket(infoAdd);
      pc.sendPacket(spawn);
    }
  }
}
