package uk.co.umbaska.Enums;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EntityPlayer;

public class AnvilGUI
{
  private Player player;
  private AnvilClickEventHandler handler;
  
  private class AnvilContainer extends net.minecraft.server.v1_9_R1.ContainerAnvil
  {
    public AnvilContainer(EntityHuman entity)
    {
      super(entity.world, new net.minecraft.server.v1_9_R1.BlockPosition(0, 0, 0), entity);
    }
    
    public boolean a(EntityHuman entityhuman)
    {
      return true;
    }
  }
  
  public static enum AnvilSlot {
    INPUT_LEFT(0), 
    INPUT_RIGHT(1), 
    OUTPUT(2);
    
    private int slot;
    
    private AnvilSlot(int slot) {
      this.slot = slot;
    }
    
    public int getSlot() {
      return this.slot;
    }
    
    public static AnvilSlot bySlot(int slot) {
      for (AnvilSlot anvilSlot : ) {
        if (anvilSlot.getSlot() == slot) {
          return anvilSlot;
        }
      }
      
      return null;
    }
  }
  

  public class AnvilClickEvent
  {
    private AnvilGUI.AnvilSlot slot;
    private String name;
    private boolean close = true;
    private boolean destroy = true;
    
    public AnvilClickEvent(AnvilGUI.AnvilSlot slot, String name) {
      this.slot = slot;
      this.name = name;
    }
    
    public AnvilGUI.AnvilSlot getSlot() {
      return this.slot;
    }
    
    public String getName() {
      return this.name;
    }
    
    public boolean getWillClose() {
      return this.close;
    }
    
    public void setWillClose(boolean close) {
      this.close = close;
    }
    
    public boolean getWillDestroy() {
      return this.destroy;
    }
    
    public void setWillDestroy(boolean destroy) {
      this.destroy = destroy;
    }
  }
  








  private HashMap<AnvilSlot, ItemStack> items = new HashMap();
  
  private Inventory inv;
  private org.bukkit.event.Listener listener;
  public String anvTitle;
  
  public AnvilGUI(Player player, final String title, final AnvilClickEventHandler handler)
  {
    this.player = player;
    this.handler = handler;
    this.anvTitle = title;
    
    this.listener = new org.bukkit.event.Listener() {
      @EventHandler
      public void onInventoryClick(InventoryClickEvent event) {
        if ((event.getWhoClicked() instanceof Player)) {
          Player clicker = (Player)event.getWhoClicked();
          
          if (event.getInventory().equals(AnvilGUI.this.inv))
          {
            ItemStack item = event.getCurrentItem();
            int slot = event.getRawSlot();
            
            String name = title;
            if ((item != null) && 
              (item.hasItemMeta())) {
              ItemMeta meta = item.getItemMeta();
              
              if (meta.hasDisplayName()) {
                name = meta.getDisplayName();
              }
            }
            

            AnvilGUI.AnvilClickEvent clickEvent = new AnvilGUI.AnvilClickEvent(AnvilGUI.this, AnvilGUI.AnvilSlot.bySlot(slot), name);
            
            handler.onAnvilClick(clickEvent);
            
            if (clickEvent.getWillClose()) {
              event.getWhoClicked().closeInventory();
            }
            
            if (clickEvent.getWillDestroy()) {
              AnvilGUI.this.destroy();
            }
          }
        }
      }
      
      @EventHandler
      public void onInventoryClose(InventoryCloseEvent event) {
        if ((event.getPlayer() instanceof Player)) {
          Player player = (Player)event.getPlayer();
          Inventory inv = event.getInventory();
          
          if (inv.equals(AnvilGUI.this.inv)) {
            inv.clear();
            AnvilGUI.this.destroy();
          }
        }
      }
      
      @EventHandler
      public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().equals(AnvilGUI.this.getPlayer())) {
          AnvilGUI.this.destroy();
        }
        
      }
    };
    org.bukkit.Bukkit.getPluginManager().registerEvents(this.listener, uk.co.umbaska.Main.getInstance());
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public void setSlot(AnvilSlot slot, ItemStack item) {
    this.items.put(slot, item);
  }
  
  public void open() {
    EntityPlayer p = ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer)this.player).getHandle();
    AnvilContainer container = new AnvilContainer(p);
    

    this.inv = org.bukkit.Bukkit.createInventory(p.getBukkitEntity(), container.getBukkitView().getTopInventory().getType(), this.anvTitle);
    
    for (AnvilSlot slot : this.items.keySet()) {
      this.inv.setItem(slot.getSlot(), (ItemStack)this.items.get(slot));
    }
    

    int c = p.nextContainerCounter();
    

    net.minecraft.server.v1_9_R1.IChatBaseComponent iChatBaseComponent = net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer.a(this.anvTitle);
    p.playerConnection.sendPacket(new net.minecraft.server.v1_9_R1.PacketPlayOutOpenWindow(c, "minecraft:anvil", iChatBaseComponent));
    

    p.activeContainer = container;
    

    p.activeContainer.windowId = c;
    

    p.activeContainer.addSlotListener(p);
  }
  
  public void destroy() {
    this.player = null;
    this.handler = null;
    this.items = null;
    
    org.bukkit.event.HandlerList.unregisterAll(this.listener);
    
    this.listener = null;
  }
  
  public static abstract interface AnvilClickEventHandler
  {
    public abstract void onAnvilClick(AnvilGUI.AnvilClickEvent paramAnvilClickEvent);
  }
}
