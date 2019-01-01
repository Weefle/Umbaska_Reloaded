package uk.co.umbaska.HologramBased;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

public class Hologram
{
  private PacketContainer destroyPacket1_7;
  private PacketContainer destroyPacket1_8;
  private PacketContainer[] displayPackets1_7;
  private PacketContainer[] displayPackets1_8;
  
  public static enum HologramTarget
  {
    BLACKLIST,  WHITELIST;
    
    private HologramTarget() {}
  }
  
  private static int getId() { try { java.lang.reflect.Field field = Class.forName("net.minecraft.server." + org.bukkit.Bukkit.getServer().getClass().getName().split("\\.")[3] + ".Entity").getDeclaredField("entityCount");
      

      field.setAccessible(true);
      int id = field.getInt(null);
      field.set(null, Integer.valueOf(id + 1));
      return id;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return -1;
  }
  




  private ArrayList<Map.Entry<Integer, Integer>> entityIds = new ArrayList<>();
  protected Location entityLastLocation;
  private ArrayList<String> hologramPlayers = new ArrayList<>();
  private HologramTarget hologramTarget = HologramTarget.BLACKLIST;
  private boolean isUsingWitherSkull = HologramCentral.isUsingWitherSkulls();
  private boolean keepAliveAfterEntityDies;
  private Location lastMovement = new Location(null, 0.0D, 0.0D, 0.0D);
  private String[] lines;
  private double lineSpacing = 1.0D;
  private Location location;
  private org.bukkit.util.Vector moveVector;
  private boolean pitchControlsMoreThanY;
  private Entity relativeEntity;
  private Location relativeToEntity;
  private boolean setRelativePitch;
  private boolean setRelativeYaw;
  private int viewDistance = 100;
  private java.util.List<Entity> holoItems = new ArrayList<>();
  
  public Hologram(Location location, String... lines)
  {
    assert (lines.length != 0) : "You need more lines than nothing!";
    assert (location.getWorld() != null) : "You can't have a null world in the location!";
    this.lines = lines;
    this.location = location.clone().add(0.0D, 54.6D, 0.0D);
  }
  











  public Hologram addPlayer(Player... players)
  {
    for (Player player : players) {
      if (!this.hologramPlayers.contains(player.getName())) {
        this.hologramPlayers.add(player.getName());
        if (isInUse()) {
          HologramCentral.addHologram(player, this);
        }
      }
    }
    return this;
  }
  
  protected PacketContainer getDestroyPacket(Player player) {
    if (HologramCentral.is1_8(player)) {
      return this.destroyPacket1_8;
    }
    return this.destroyPacket1_7;
  }
  
  public Entity getEntityFollowed() {
    return this.relativeEntity;
  }
  
  protected ArrayList<Map.Entry<Integer, Integer>> getEntityIds() {
    return this.entityIds;
  }
  
  public String[] getLines() {
    return this.lines;
  }
  
  public double getLineSpacing() {
    return this.lineSpacing;
  }
  
  public Location getLocation() {
    return this.location.clone().subtract(0.0D, 54.6D, 0.0D);
  }
  
  public org.bukkit.util.Vector getMovement() {
    return this.moveVector;
  }
  
  private ArrayList<Player> getPlayers() {
    ArrayList<Player> players = new ArrayList<>();
    for (Player player : org.bukkit.Bukkit.getServer().getOnlinePlayers()) {
      if (isVisible(player, player.getLocation())) {
        players.add(player);
      }
    }
    return players;
  }
  
  public Location getRelativeToEntity() {
    return this.relativeToEntity;
  }
  
  protected PacketContainer[] getSpawnPackets(Player player) {
    if (HologramCentral.is1_8(player)) {
      return this.displayPackets1_8;
    }
    return this.displayPackets1_7;
  }
  
  public HologramTarget getTarget() {
    return this.hologramTarget;
  }
  
  public boolean hasPlayer(Player player) {
    return this.hologramPlayers.contains(player.getName());
  }
  
  public boolean isInUse() {
    return HologramCentral.isInUse(this);
  }
  
  public boolean isRelativePitch() {
    return this.setRelativePitch;
  }
  
  public boolean isRelativePitchControlMoreThanHeight() {
    return this.pitchControlsMoreThanY;
  }
  
  public boolean isRelativeYaw() {
    return this.setRelativeYaw;
  }
  
  public boolean isRemovedOnEntityDeath() {
    return this.keepAliveAfterEntityDies;
  }
  
  public boolean isUsingArmorStand() {
    return !this.isUsingWitherSkull;
  }
  
  public boolean isUsingWitherSkull() {
    return this.isUsingWitherSkull;
  }
  
  boolean isVisible(Player player, Location loc) {
    return ((getTarget() == HologramTarget.BLACKLIST) != hasPlayer(player)) && (loc.getWorld() == getLocation().getWorld()) && (loc.distance(getLocation()) <= this.viewDistance);
  }
  
  private void makeDestroyPacket()
  {
    int[] ids = new int[this.entityIds.size() * 2];
    int[] ids2 = new int[ids.length / 2];
    int i = 0;
    for (Map.Entry<Integer, Integer> entry : this.entityIds) {
      ids2[(i / 2)] = ((Integer)entry.getKey()).intValue();
      ids[(i++)] = ((Integer)entry.getKey()).intValue();
      ids[(i++)] = ((Integer)entry.getValue()).intValue();
    }
    this.destroyPacket1_7 = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
    this.destroyPacket1_7.getIntegerArrays().write(0, ids);
    this.destroyPacket1_8 = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
    this.destroyPacket1_8.getIntegerArrays().write(0, ids2);
  }
  
  private void makeDisplayPackets() {
    Iterator<Map.Entry<Integer, Integer>> itel = this.entityIds.iterator();
    this.displayPackets1_7 = new PacketContainer[this.lines.length * 3];
    this.displayPackets1_8 = new PacketContainer[this.lines.length * (isUsingWitherSkull() ? 2 : 1)];
    int b = 0;
    while (itel.hasNext()) {
      Map.Entry<Integer, Integer> entry = itel.next();
      PacketContainer[] packets = makeSpawnPacket1_8(b, ((Integer)entry.getKey()).intValue(), this.lines[(this.lines.length - 1 - b)]);
      for (int a = 0; a < (isUsingWitherSkull() ? 2 : 1); a++) {
        this.displayPackets1_8[(b + a)] = packets[a];
      }
      packets = makeSpawnPackets1_7(b, ((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue(), this.lines[(this.lines.length - 1 - b)]);
      for (int a = 0; a < 3; a++) {
        this.displayPackets1_7[(b * 3 + a)] = packets[a];
      }
      b++;
    }
  }
  
  private PacketContainer[] makeSpawnPacket1_8(int height, int witherId, String horseName) {
    if (isUsingWitherSkull()) {
      PacketContainer[] packets = new PacketContainer[2];
      packets[0] = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
      StructureModifier<Integer> ints = packets[0].getIntegers();
      ints.write(0, Integer.valueOf(witherId));
      ints.write(1, Integer.valueOf((int)(getLocation().getX() * 32.0D)));
      ints.write(2, Integer.valueOf((int)((this.location.getY() + -55.15D + height * (getLineSpacing() * 0.285D)) * 32.0D)));
      ints.write(3, Integer.valueOf((int)(getLocation().getZ() * 32.0D)));
      ints.write(9, Integer.valueOf(66));
      
      packets[1] = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
      packets[1].getIntegers().write(0, Integer.valueOf(witherId));
      ArrayList<WrappedWatchableObject> list = new ArrayList<>();
      list.add(new WrappedWatchableObject(0, Byte.valueOf((byte)0)));
      list.add(new WrappedWatchableObject(2, horseName));
      list.add(new WrappedWatchableObject(3, Byte.valueOf((byte)1)));
      packets[1].getWatchableCollectionModifier().write(0, list);
      return packets;
    }
    PacketContainer displayPacket = null;
    displayPacket = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
    StructureModifier<Integer> ints = displayPacket.getIntegers();
    ints.write(0, Integer.valueOf(witherId));
    ints.write(1, Integer.valueOf(30));
    ints.write(2, Integer.valueOf((int)(getLocation().getX() * 32.0D)));
    ints.write(3, Integer.valueOf((int)((this.location.getY() + -56.7D + height * (getLineSpacing() * 0.285D)) * 32.0D)));
    ints.write(4, Integer.valueOf((int)(getLocation().getZ() * 32.0D)));
    
    WrappedDataWatcher watcher = new WrappedDataWatcher();
    watcher.setObject(0, Byte.valueOf((byte)32));
    watcher.setObject(2, horseName);
    watcher.setObject(3, Byte.valueOf((byte)1));
    displayPacket.getDataWatcherModifier().write(0, watcher);
    return new PacketContainer[] { displayPacket };
  }
  
  private PacketContainer[] makeSpawnPackets1_7(int height, int witherId, int horseId, String horseName)
  {
    PacketContainer[] displayPackets = new PacketContainer[3];
    
    displayPackets[0] = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
    StructureModifier<Integer> ints = displayPackets[0].getIntegers();
    ints.write(0, Integer.valueOf(witherId));
    ints.write(1, Integer.valueOf((int)(getLocation().getX() * 32.0D)));
    ints.write(2, Integer.valueOf((int)((this.location.getY() + height * (getLineSpacing() * 0.285D)) * 32.0D)));
    ints.write(3, Integer.valueOf((int)(getLocation().getZ() * 32.0D)));
    ints.write(9, Integer.valueOf(66));
    
    displayPackets[1] = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
    ints = displayPackets[1].getIntegers();
    ints.write(0, Integer.valueOf(horseId));
    ints.write(1, Integer.valueOf(100));
    ints.write(2, Integer.valueOf((int)(getLocation().getX() * 32.0D)));
    ints.write(3, Integer.valueOf((int)((this.location.getY() + height * (getLineSpacing() * 0.285D) + 0.23D) * 32.0D)));
    ints.write(4, Integer.valueOf((int)(getLocation().getZ() * 32.0D)));
    
    WrappedDataWatcher watcher = new WrappedDataWatcher();
    watcher.setObject(0, Byte.valueOf((byte)0));
    watcher.setObject(1, Short.valueOf((short)300));
    watcher.setObject(10, horseName);
    watcher.setObject(11, Byte.valueOf((byte)1));
    watcher.setObject(12, Integer.valueOf(-1700000));
    displayPackets[1].getDataWatcherModifier().write(0, watcher);
    
    displayPackets[2] = new PacketContainer(PacketType.Play.Server.ATTACH_ENTITY);
    ints = displayPackets[2].getIntegers();
    ints.write(1, Integer.valueOf(horseId));
    ints.write(2, Integer.valueOf(witherId));
    return displayPackets;
  }
  
  public Hologram moveHologram(Location location) {
    moveHologram(location, true);
    return this;
  }
  
  public Hologram moveHologram(Location newLocation, boolean setNewRelativeLocation) {
    ArrayList<Player> oldPlayers = getPlayers();
    Location loc = getLocation();
    this.location = newLocation.clone().add(0.0D, 54.6D, 0.0D);
    if ((setNewRelativeLocation) && (getEntityFollowed() != null))
      this.relativeToEntity = getLocation().subtract(getEntityFollowed().getLocation());
    PacketContainer[] packets1_7;
    PacketContainer[] packets1_8;
    if (isInUse())
    {
      makeDisplayPackets();
      ArrayList<Player> newPlayers = getPlayers();
      Iterator<Player> itel = oldPlayers.iterator();
      
      while (itel.hasNext()) {
        Player p = (Player)itel.next();
        if (!newPlayers.contains(p)) {
          itel.remove();
          try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, getDestroyPacket(p), false);
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
      packets1_7 = null;
      packets1_8 = null;
      int x; int z; int i; if (!oldPlayers.isEmpty()) {
        this.lastMovement.add(this.location.getX() - loc.getX(), this.location.getY() - loc.getY(), this.location.getZ() - loc.getZ());
        x = (int)Math.floor(32.0D * this.lastMovement.getX());
        int y = (int)Math.floor(32.0D * this.lastMovement.getY());
        z = (int)Math.floor(32.0D * this.lastMovement.getZ());
        packets1_7 = new PacketContainer[this.lines.length];
        packets1_8 = new PacketContainer[this.lines.length];
        i = 0;
        if ((x >= -128) && (x < 128) && (y >= -128) && (y < 128) && (z >= -128) && (z < 128)) {
          this.lastMovement.subtract(x / 32.0D, y / 32.0D, z / 32.0D);
          for (Map.Entry<Integer, Integer> entityId : this.entityIds) {
            packets1_7[i] = new PacketContainer(PacketType.Play.Server.REL_ENTITY_MOVE);
            packets1_7[i].getIntegers().write(0, entityId.getKey());
            StructureModifier<Byte> bytes = packets1_7[i].getBytes();
            bytes.write(0, Byte.valueOf((byte)x));
            bytes.write(1, Byte.valueOf((byte)y));
            bytes.write(2, Byte.valueOf((byte)z));
            packets1_8[i] = packets1_7[i];
            i++;
          }
        } else {
          x = (int)Math.floor(32.0D * this.location.getX());
          z = (int)Math.floor(32.0D * this.location.getZ());
          this.lastMovement = new Location(null, this.location.getX() - x / 32.0D, 0.0D, this.location.getZ() - z / 32.0D);
          for (Map.Entry<Integer, Integer> entityId : this.entityIds) {
            packets1_7[i] = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
            StructureModifier<Integer> ints = packets1_7[i].getIntegers();
            ints.write(0, entityId.getKey());
            ints.write(1, Integer.valueOf(x));
            ints.write(2, Integer.valueOf((int)Math.floor((this.location.getY() + i * (getLineSpacing() * 0.285D)) * 32.0D)));
            ints.write(3, Integer.valueOf(z));
            packets1_8[i] = packets1_7[i].shallowClone();
            packets1_8[i].getIntegers().write(2, Integer.valueOf((int)Math.floor((this.location.getY() + -55.15D + i * (getLineSpacing() * 0.285D)) * 32.0D)));
            
            i++;
          }
        }
      }
      for (Player p : newPlayers) {
        try {
          for (PacketContainer packet : oldPlayers.contains(p) ? packets1_7 : HologramCentral.is1_8(p) ? packets1_8 : getSpawnPackets(p))
          {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet, false);
          }
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
    return this;
  }
  
  public Hologram remove() {
    HologramCentral.removeHologram(this);
    return this;
  }
  
  public Hologram removePlayer(Player... players) {
    for (Player player : players) {
      if (this.hologramPlayers.contains(player.getName())) {
        this.hologramPlayers.remove(player.getName());
        if (isInUse()) {
          HologramCentral.removeHologram(player, this);
        }
      }
    }
    return this;
  }
  
  public Hologram setFollowEntity(Entity entity) {
    setFollowEntity(entity, true);
    return this;
  }
  
  public Hologram setFollowEntity(Entity entity, boolean isRemoveOnEntityDeath) {
    setFollowEntity(entity, isRemoveOnEntityDeath, false, false, false);
    return this;
  }
  
  public Hologram setFollowEntity(Entity entity, boolean isRemoveOnEntityDeath, boolean setRelativeYaw, boolean setRelativePitch, boolean pitchControlsMoreThanY)
  {
    this.relativeEntity = entity;
    if (entity != null) {
      this.keepAliveAfterEntityDies = isRemoveOnEntityDeath;
      this.relativeToEntity = getLocation().subtract(entity.getLocation());
      this.entityLastLocation = entity.getLocation();
      Location l = entity.getLocation();
      if ((setRelativeYaw) || (setRelativePitch))
      {
        this.relativeToEntity.setPitch(l.getPitch());
        this.relativeToEntity.setYaw(l.getYaw());
        this.setRelativePitch = setRelativePitch;
        this.setRelativeYaw = setRelativeYaw;
        this.pitchControlsMoreThanY = pitchControlsMoreThanY;
      }
    }
    return this;
  }
  
  public Hologram setLines(String... lines)
  {
    if (!this.lines.equals(lines)) {
      String[] oldLines = (String[])this.lines.clone();
      this.lines = lines;
      lines = (String[])lines.clone();
      org.apache.commons.lang.ArrayUtils.reverse(lines);
      org.apache.commons.lang.ArrayUtils.reverse(oldLines);
      if (isInUse()) {
        int i = 0;
        ArrayList<Player> players = getPlayers();
        PacketContainer packet1_7;
        PacketContainer packet1_8; for (; i < Math.min(this.entityIds.size(), lines.length); i++) {
          if ((oldLines.length <= i) || (!oldLines[i].equals(lines[i]))) {
            Map.Entry<Integer, Integer> entry = this.entityIds.get(i);
            packet1_7 = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
            packet1_7.getIntegers().write(0, entry.getValue());
            ArrayList<WrappedWatchableObject> list = new ArrayList<>();
            list.add(new WrappedWatchableObject(0, Byte.valueOf((byte)0)));
            list.add(new WrappedWatchableObject(1, Short.valueOf((short)300)));
            list.add(new WrappedWatchableObject(10, lines[i]));
            list.add(new WrappedWatchableObject(11, Byte.valueOf((byte)1)));
            list.add(new WrappedWatchableObject(12, Integer.valueOf(-1700000)));
            packet1_7.getWatchableCollectionModifier().write(0, list);
            packet1_8 = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
            packet1_8.getIntegers().write(0, entry.getKey());
            list = new ArrayList<>();
            list.add(new WrappedWatchableObject(0, Byte.valueOf((byte)32)));
            list.add(new WrappedWatchableObject(2, lines[i]));
            list.add(new WrappedWatchableObject(3, Byte.valueOf((byte)1)));
            packet1_8.getWatchableCollectionModifier().write(0, list);
            for (Player p : players) {
              try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, HologramCentral.is1_8(p) ? packet1_8 : packet1_7, false);
              }
              catch (InvocationTargetException e) {
                e.printStackTrace();
              }
            }
          }
        }
        if (lines.length != this.entityIds.size()) { PacketContainer destroyPacket;
          if (lines.length < this.entityIds.size())
          {
            int[] destroyIds = new int[(this.entityIds.size() - lines.length) * 2];
            int e = 0;
            while (this.entityIds.size() > lines.length) {
              Map.Entry<Integer, Integer> entry = this.entityIds.remove(this.entityIds.size() - 1);
              destroyIds[(e++)] = ((Integer)entry.getKey()).intValue();
              destroyIds[(e++)] = ((Integer)entry.getValue()).intValue();
            }
            destroyPacket = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            destroyPacket.getIntegerArrays().write(0, destroyIds);
            for (Player p : players) {
              try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, destroyPacket, false);
              }
              catch (InvocationTargetException b) {
                b.printStackTrace();
              }
            }
          } else if (lines.length > this.entityIds.size()) { PacketContainer[] packets1_7;
            PacketContainer[] packet1_81; for (; i < lines.length; i++) {
              Map.Entry<Integer, Integer> entry = new java.util.AbstractMap.SimpleEntry<Integer, Integer>(Integer.valueOf(getId()), Integer.valueOf(getId()));
              this.entityIds.add(entry);
              
              packets1_7 = makeSpawnPackets1_7(i, ((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue(), lines[i]);
              packet1_81 = makeSpawnPacket1_8(i, ((Integer)entry.getKey()).intValue(), lines[i]);
              for (Player p : players) {
                try {
                  if (HologramCentral.is1_8(p)) {
                    for (PacketContainer packet : packet1_81) {
                      ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet, false);
                    }
                  } else {
                    for (PacketContainer packet : packets1_7)
                      ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet, false);
                  }
                } catch (InvocationTargetException e) {
                  e.printStackTrace();
                }
              }
            }
          }
          makeDestroyPacket();
        }
        makeDisplayPackets();
      } else {
        if (lines.length < this.entityIds.size()) {
          while (this.entityIds.size() > lines.length) {
            this.entityIds.remove(this.entityIds.size() - 1);
          }
        }
        for (int i = this.entityIds.size(); i < lines.length; i++) {
          Map.Entry<Integer, Integer> entry = new java.util.AbstractMap.SimpleEntry<Integer, Integer>(Integer.valueOf(getId()), Integer.valueOf(getId()));
          this.entityIds.add(entry);
        }
      }
    }
    
    return this;
  }
  
  public Hologram setLineSpacing(double newLineSpacing) {
    this.lineSpacing = newLineSpacing;
    if (isInUse()) {
      makeDisplayPackets();
      HologramCentral.removeHologram(this);
      HologramCentral.addHologram(this);
    }
    return this;
  }
  
  public Hologram setMovement(org.bukkit.util.Vector vector) {
    this.moveVector = vector;
    return this;
  }
  
  public Hologram setRadius(int viewDistance) {
    assert (viewDistance > 0) : ("Why the hell are you setting the view distance to " + viewDistance + "?!?!");
    this.viewDistance = viewDistance;
    if (isInUse()) {
      HologramCentral.removeHologram(this);
      HologramCentral.addHologram(this);
    }
    return this;
  }
  
  public Hologram setRelativeToEntity(Location location) {
    this.relativeToEntity = location;
    return this;
  }
  
  public Hologram setTarget(HologramTarget target) {
    if (target != getTarget()) {
      this.hologramTarget = target;
      if (isInUse()) {
        HologramCentral.removeHologram(this);
        HologramCentral.addHologram(this);
      }
    }
    return this;
  }
  
  public Hologram setUsingArmorStand() {
    this.isUsingWitherSkull = true;
    makeDisplayPackets();
    
    return this;
  }
  
  public Hologram setUsingWitherSkull() {
    this.isUsingWitherSkull = false;
    makeDisplayPackets();
    
    return this;
  }
  
  public Hologram start()
  {
    if (!isInUse()) {
      for (int i = this.entityIds.size(); i < this.lines.length; i++) {
        int entityId = getId();
        this.entityIds.add(new java.util.AbstractMap.SimpleEntry<Integer, Integer>(Integer.valueOf(getId()), Integer.valueOf(entityId)));
      }
      makeDestroyPacket();
      makeDisplayPackets();
      HologramCentral.addHologram(this);
    }
    return this;
  }
  
  public Hologram stop() {
    return remove();
  }












public java.util.List<Entity> getHoloItems() {
	return holoItems;
}












public void setHoloItems(java.util.List<Entity> holoItems) {
	this.holoItems = holoItems;
}
}
