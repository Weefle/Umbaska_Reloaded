package uk.co.umbaska.Misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Enums.AnvilGUI;
import uk.co.umbaska.Enums.InventoryTypes;

public class EffOpenInventory extends Effect
{
  private Expression<InventoryTypes> types;
  private Expression<String> name;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    Player[] p = (Player[])this.player.getAll(event);
    String n = (String)this.name.getSingle(event);
    InventoryTypes t2 = (InventoryTypes)this.types.getSingle(event);
    InventoryType t = t2.getType();
    if (p == null) {
      return;
    }
    for (Player pl : p) {
      if (t != InventoryType.ANVIL) {
        Inventory inv = Bukkit.createInventory(null, t, n);
        
        pl.openInventory(inv);
      } else {
        AnvilGUI anv = new AnvilGUI(pl, n, new AnvilGUI.AnvilClickEventHandler()
        {
          public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
            if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
              event.setWillClose(false);
              event.setWillDestroy(false);
            }
            else {
              event.setWillClose(false);
              event.setWillDestroy(false);
            }
          }
        });
        anv.open();
      }
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Open Inventory";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.types = (Expression<InventoryTypes>) expressions[0];
    this.name = (Expression<String>) expressions[1];
    this.player = (Expression<Player>) expressions[2];
    return true;
  }
}
