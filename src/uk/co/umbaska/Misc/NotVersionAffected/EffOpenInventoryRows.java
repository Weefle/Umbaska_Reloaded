package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;



public class EffOpenInventoryRows
  extends Effect
{
  private Expression<InventoryType> types;
  private Expression<String> name;
  private Expression<Player> player;
  private Expression<Integer> rows;
  
  protected void execute(Event event)
  {
    Player[] p = (Player[])this.player.getAll(event);
    String n = (String)this.name.getSingle(event);
    Integer i = (Integer)this.rows.getSingle(event);
    InventoryType t = (InventoryType)this.types.getSingle(event);
    if (p == null) {
      return;
    }
    if (n == null) {
      n = t.getDefaultTitle();
    }
    if (i != null) {
      t = InventoryType.CHEST;
    }
    if (t == InventoryType.CHEST) {
      if (i == null) {
        i = Integer.valueOf(1);
      }
      i = Integer.valueOf(i.intValue() * 9);
    }
    for (Player pl : p) {
      Inventory inv = Bukkit.createInventory(null, t, n);
      if (t == InventoryType.CHEST) {
        inv = Bukkit.createInventory(null, i.intValue(), n);
      }
      
      pl.openInventory(inv);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Open Inventory";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.types = (Expression<InventoryType>) expressions[0];
    this.name = (Expression<String>) expressions[1];
    this.rows = (Expression<Integer>) expressions[2];
    this.player = (Expression<Player>) expressions[3];
    return true;
  }
}
