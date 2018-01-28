package ca.thederpygolems.armourequip;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;

public class ArmourListener implements org.bukkit.event.Listener
{
  private final HashMap<String, HashMap<ArmourType, Long>> lastEquip;
  private final java.util.List<String> blockedMaterials;
  
  public ArmourListener(java.util.List<String> blockedMaterials)
  {
    this.lastEquip = new HashMap();
    this.blockedMaterials = blockedMaterials;
  }
  
  @EventHandler
  public final void onInventoryClick(InventoryClickEvent e) {
    boolean shift = false;
    if (e.isCancelled()) return;
    if ((e.getClick() == org.bukkit.event.inventory.ClickType.SHIFT_LEFT) || (e.getClick() == org.bukkit.event.inventory.ClickType.SHIFT_RIGHT)) {
      shift = true;
    }
    if ((e.getSlotType() != org.bukkit.event.inventory.InventoryType.SlotType.ARMOR) && (e.getSlotType() != org.bukkit.event.inventory.InventoryType.SlotType.QUICKBAR) && (!e.getInventory().getName().equalsIgnoreCase("container.crafting"))) {
      return;
    }
    if (!(e.getWhoClicked() instanceof Player)) return;
    if (e.getCurrentItem() == null) return;
    ArmourType newArmorType = ArmourType.matchType(shift ? e.getCurrentItem() : e.getCursor());
    if ((!shift) && (newArmorType != null) && (e.getRawSlot() != newArmorType.getSlot()))
    {
      return;
    }
    if (shift) {
      newArmorType = ArmourType.matchType(e.getCurrentItem());
      if (newArmorType != null) {
        boolean equipping = true;
        if (e.getRawSlot() == newArmorType.getSlot()) {
          equipping = false;
        }
        if ((!newArmorType.equals(ArmourType.HELMET)) || (equipping ? e.getWhoClicked().getInventory().getHelmet() != null : e.getWhoClicked().getInventory().getHelmet() == null)) if ((!newArmorType.equals(ArmourType.CHESTPLATE)) || (equipping ? e.getWhoClicked().getInventory().getChestplate() != null : e.getWhoClicked().getInventory().getChestplate() == null)) if ((!newArmorType.equals(ArmourType.LEGGINGS)) || (equipping ? e.getWhoClicked().getInventory().getLeggings() != null : e.getWhoClicked().getInventory().getLeggings() == null)) if ((!newArmorType.equals(ArmourType.BOOTS)) || (equipping ? e.getWhoClicked().getInventory().getBoots() != null : e.getWhoClicked().getInventory().getBoots() == null)) return;
        ArmourEquipEvent armorEquipEvent = new ArmourEquipEvent((Player)e.getWhoClicked(), ArmourEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
        if (canEquip(e.getWhoClicked().getUniqueId().toString(), newArmorType)) {
          Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
          setLastEquip(e.getWhoClicked().getUniqueId().toString(), newArmorType);
          if (armorEquipEvent.isCancelled()) {
            e.setCancelled(true);
          }
          
        }
      }
    }
    else
    {
      newArmorType = ArmourType.matchType((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) ? e.getCurrentItem() : e.getCursor());
      if ((newArmorType != null) && (e.getRawSlot() == newArmorType.getSlot())) {
        ArmourEquipEvent armorEquipEvent = new ArmourEquipEvent((Player)e.getWhoClicked(), ArmourEquipEvent.EquipMethod.DRAG, newArmorType, e.getCurrentItem(), e.getCursor());
        if (canEquip(e.getWhoClicked().getUniqueId().toString(), newArmorType)) {
          Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
          setLastEquip(e.getWhoClicked().getUniqueId().toString(), newArmorType);
          if (armorEquipEvent.isCancelled()) {
            e.setCancelled(true);
          }
        }
      }
    }
  }
  
  @EventHandler
  public void playerInteractEvent(PlayerInteractEvent e)
  {
    if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) { Material mat;
      if ((e.getClickedBlock() != null) && (e.getAction() == Action.RIGHT_CLICK_BLOCK))
      {
        mat = e.getClickedBlock().getType();
        for (String s : this.blockedMaterials) {
          if (mat.name().equalsIgnoreCase(s)) return;
        }
      }
      ArmourType newArmorType = ArmourType.matchType(e.getItem());
      if ((newArmorType != null) && (
        ((newArmorType.equals(ArmourType.HELMET)) && (e.getPlayer().getInventory().getHelmet() == null)) || ((newArmorType.equals(ArmourType.CHESTPLATE)) && (e.getPlayer().getInventory().getChestplate() == null)) || ((newArmorType.equals(ArmourType.LEGGINGS)) && (e.getPlayer().getInventory().getLeggings() == null)) || ((newArmorType.equals(ArmourType.BOOTS)) && (e.getPlayer().getInventory().getBoots() == null)))) {
        ArmourEquipEvent armorEquipEvent = new ArmourEquipEvent(e.getPlayer(), ArmourEquipEvent.EquipMethod.HOTBAR, ArmourType.matchType(e.getItem()), null, e.getItem());
        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
        if (armorEquipEvent.isCancelled()) {
          e.setCancelled(true);
        }
      }
    }
  }
  
  @EventHandler
  public void dispenserFireEvent(BlockDispenseEvent e)
  {
    ArmourType type = ArmourType.matchType(e.getItem());
    Location loc; if (ArmourType.matchType(e.getItem()) != null) {
      loc = e.getBlock().getLocation();
      for (Player p : loc.getWorld().getPlayers()) {
        if ((loc.getBlockY() - p.getLocation().getBlockY() >= -1) && (loc.getBlockY() - p.getLocation().getBlockY() <= 1) && (
          ((p.getInventory().getHelmet() == null) && (type.equals(ArmourType.HELMET))) || ((p.getInventory().getChestplate() == null) && (type.equals(ArmourType.CHESTPLATE))) || ((p.getInventory().getLeggings() == null) && (type.equals(ArmourType.LEGGINGS))) || ((p.getInventory().getBoots() == null) && (type.equals(ArmourType.BOOTS))))) {
          org.bukkit.block.Dispenser dispenser = (org.bukkit.block.Dispenser)e.getBlock().getState();
          org.bukkit.material.Dispenser dis = (org.bukkit.material.Dispenser)dispenser.getData();
          BlockFace directionFacing = dis.getFacing();
          
          if (((directionFacing == BlockFace.EAST) && (p.getLocation().getBlockX() != loc.getBlockX()) && (p.getLocation().getX() <= loc.getX() + 2.3D) && (p.getLocation().getX() >= loc.getX())) || ((directionFacing == BlockFace.WEST) && (p.getLocation().getX() >= loc.getX() - 1.3D) && (p.getLocation().getX() <= loc.getX())) || ((directionFacing == BlockFace.SOUTH) && (p.getLocation().getBlockZ() != loc.getBlockZ()) && (p.getLocation().getZ() <= loc.getZ() + 2.3D) && (p.getLocation().getZ() >= loc.getZ())) || ((directionFacing == BlockFace.NORTH) && (p.getLocation().getZ() >= loc.getZ() - 1.3D) && (p.getLocation().getZ() <= loc.getZ()))) {
            ArmourEquipEvent armorEquipEvent = new ArmourEquipEvent(p, ArmourEquipEvent.EquipMethod.DISPENSER, ArmourType.matchType(e.getItem()), null, e.getItem());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
              e.setCancelled(true);
            }
            return;
          }
        }
      }
    }
  }
  
  @EventHandler
  public void itemBreakEvent(PlayerItemBreakEvent e)
  {
    ArmourType type = ArmourType.matchType(e.getBrokenItem());
    if (type != null) {
      Player p = e.getPlayer();
      ArmourEquipEvent armorEquipEvent = new ArmourEquipEvent(p, ArmourEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
      Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
      if (armorEquipEvent.isCancelled()) {
        ItemStack i = e.getBrokenItem().clone();
        i.setAmount(1);
        i.setDurability((short)(i.getDurability() - 1));
        if (type.equals(ArmourType.HELMET)) {
          p.getInventory().setHelmet(i);
        } else if (type.equals(ArmourType.CHESTPLATE)) {
          p.getInventory().setChestplate(i);
        } else if (type.equals(ArmourType.LEGGINGS)) {
          p.getInventory().setLeggings(i);
        } else if (type.equals(ArmourType.BOOTS)) {
          p.getInventory().setBoots(i);
        }
      }
    }
  }
  
  @EventHandler
  public void playerDeathEvent(PlayerDeathEvent e) {
    Player p = e.getEntity();
    for (ItemStack i : p.getInventory().getArmorContents()) {
      if ((i != null) && (!i.getType().equals(Material.AIR))) {
        Bukkit.getServer().getPluginManager().callEvent(new ArmourEquipEvent(p, ArmourEquipEvent.EquipMethod.DEATH, ArmourType.matchType(i), i, null));
      }
    }
  }
  
  public boolean canEquip(String id, ArmourType type)
  {
    if (type == null) return true;
    if ((this.lastEquip.containsKey(id)) && 
      (((HashMap)this.lastEquip.get(id)).containsKey(type)) && 
      (System.currentTimeMillis() - ((Long)((HashMap)this.lastEquip.get(id)).get(type)).longValue() < 500L)) { return false;
    }
    
    return true;
  }
  
  public void setLastEquip(String id, ArmourType armorType) {
    if (armorType != null) {
      if (!this.lastEquip.containsKey(id)) {
        this.lastEquip.put(id, new HashMap());
      }
      HashMap<ArmourType, Long> data = (HashMap)this.lastEquip.get(id);
      data.put(armorType, Long.valueOf(System.currentTimeMillis()));
      this.lastEquip.put(id, data);
    }
  }
}
