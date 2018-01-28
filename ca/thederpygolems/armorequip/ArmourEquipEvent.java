package ca.thederpygolems.armorequip;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;



public final class ArmourEquipEvent
  extends PlayerEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancel = false;
  

  private final EquipMethod equipType;
  

  private final ArmourType type;
  
  private final ItemStack oldArmorPiece;
  
  private ItemStack newArmorPiece;
  

  public ArmourEquipEvent(Player player, EquipMethod equipType, ArmourType type, ItemStack oldArmorPiece, ItemStack newArmorPiece)
  {
    super(player);
    this.equipType = equipType;
    this.type = type;
    this.oldArmorPiece = oldArmorPiece;
    this.newArmorPiece = newArmorPiece;
  }
  




  public static final HandlerList getHandlerList()
  {
    return handlers;
  }
  





  public final HandlerList getHandlers()
  {
    return handlers;
  }
  




  public final void setCancelled(boolean cancel)
  {
    this.cancel = cancel;
  }
  




  public final boolean isCancelled()
  {
    return this.cancel;
  }
  
  public final ArmourType getType() {
    return this.type;
  }
  


  public final ItemStack getOldArmorPiece()
  {
    return this.oldArmorPiece;
  }
  


  public final ItemStack getNewArmorPiece()
  {
    return this.newArmorPiece;
  }
  
  public final void setNewArmorPiece(ItemStack newArmorPiece) {
    this.newArmorPiece = newArmorPiece;
  }
  


  public EquipMethod getMethod()
  {
    return this.equipType;
  }
  
  public static enum EquipMethod
  {
    SHIFT_CLICK,  DRAG,  HOTBAR,  DISPENSER,  BROKE,  DEATH;
    
    private EquipMethod() {}
  }
}
