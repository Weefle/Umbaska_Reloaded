package uk.co.umbaska.Utils.Disguise.DisguiseUTIL;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutAnimation;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_9_R2.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_9_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;
import net.minecraft.server.v1_9_R2.WorldServer;

public class PlayerDisguise extends Disguise {

	private String name;
	private EntityPlayer ep;

	public PlayerDisguise(Player player, String name) {
		super(player, EntityType.PLAYER);
		this.name = ChatColor.translateAlternateColorCodes('&', name);
	}

	@Override
	public void applyDisguise(Collection<Player> players) {
		UUID uid = new UUIDRetriever(name).getUUID();
		GameProfile gp = new GameProfile(uid == null ? UUID.randomUUID() : uid,
				name);
		UUIDRetriever.setSkin(gp);
		EntityPlayer playerEntity = ((CraftPlayer) getPlayer()).getHandle();
		EntityPlayer ep = this.ep == null
				? this.ep = new EntityPlayer(playerEntity.server,
						(WorldServer) playerEntity.world, gp,
						new PlayerInteractManager(playerEntity.world))
				: this.ep;
		ep.inventory = playerEntity.inventory;
		ep.locX = playerEntity.locX;
		ep.locY = playerEntity.locY;
		ep.locZ = playerEntity.locZ;
		ep.yaw = playerEntity.yaw;
		ep.pitch = playerEntity.pitch;
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(
				playerEntity.getId());
		PacketPlayOutPlayerInfo infoRemove = new PacketPlayOutPlayerInfo(
				EnumPlayerInfoAction.REMOVE_PLAYER, playerEntity);
		PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(
				EnumPlayerInfoAction.ADD_PLAYER, ep);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(
				ep);
		for (Player p : players) {
			if (p == getPlayer())
				continue;
			EntityPlayer aep = ((CraftPlayer) p).getHandle();
			PlayerConnection pc = aep.playerConnection;
			pc.sendPacket(destroy);
			pc.sendPacket(infoRemove);
			pc.sendPacket(infoAdd);
			pc.sendPacket(spawn);
		}
	}

	@Override
	public void revertDisguise(Collection<Player> players) {
		EntityPlayer playerEntity = ((CraftPlayer) getPlayer()).getHandle();
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(
				ep.getId());
		PacketPlayOutPlayerInfo infoRemove = new PacketPlayOutPlayerInfo(
				EnumPlayerInfoAction.REMOVE_PLAYER, ep);
		PacketPlayOutPlayerInfo infoAdd = new PacketPlayOutPlayerInfo(
				EnumPlayerInfoAction.ADD_PLAYER, playerEntity);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(
				playerEntity);
		for (Player p : players) {
			if (p == getPlayer())
				continue;
			EntityPlayer aep = ((CraftPlayer) p).getHandle();
			PlayerConnection pc = aep.playerConnection;
			pc.sendPacket(destroy);
			pc.sendPacket(infoRemove);
			pc.sendPacket(infoAdd);
			pc.sendPacket(spawn);
		}
	}

	public void sneak(boolean sneak) {
		this.ep.setSneaking(sneak);
		PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(
				this.ep.getId(), this.ep.getDataWatcher(), true);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == getPlayer())
				continue;
			EntityPlayer aep = ((CraftPlayer) p).getHandle();
			aep.playerConnection.sendPacket(meta);
		}
	}

	public void swingArm() {
		PacketPlayOutAnimation animation = new PacketPlayOutAnimation(this.ep,
				0);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == getPlayer())
				continue;
			EntityPlayer aep = ((CraftPlayer) p).getHandle();
			aep.playerConnection.sendPacket(animation);
		}
	}

	public void setItemInHand(int slot) {
		PacketPlayOutHeldItemSlot held = new PacketPlayOutHeldItemSlot(slot);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == getPlayer())
				continue;
			EntityPlayer aep = ((CraftPlayer) p).getHandle();
			aep.playerConnection.sendPacket(held);
		}
	}

	@Override
	public void move(Location old, Location newloc) {
		PacketPlayOutRelEntityMoveLook moveLook = new PacketPlayOutRelEntityMoveLook(
				this.ep.getId(),
				(short) ((newloc.getX()*32 - old.getX()*32)*128),
				(short) ((newloc.getY()*32 - old.getY()*32)*128),
				(short) ((newloc.getZ()*32 - old.getZ()*32)*128), (byte) 0,
				(byte) 0, ep.getBukkitEntity().isOnGround());
		PacketPlayOutEntityLook look = new PacketPlayOutEntityLook(
				this.ep.getId(), (byte) (newloc.getYaw() * 256 / 360),
				(byte) (newloc.getPitch() * 256 / 360),
				ep.getBukkitEntity().isOnGround());
		PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(
				ep, (byte) (newloc.getYaw() * 256 / 360));
		ep.yaw = newloc.getYaw();
		ep.pitch = newloc.getPitch();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == getPlayer())
				continue;
			EntityPlayer aep = ((CraftPlayer) p).getHandle();
			aep.playerConnection.sendPacket(moveLook);
			aep.playerConnection.sendPacket(look);
			aep.playerConnection.sendPacket(rotation);
		}
	}

	public String getName() {
		return name;
	}

}