package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ca.thederpygolems.armorequip.ArmourEquipEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;








public class ExprArmourSlot
  extends SimpleExpression<Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "permission of cmd";
  }
  
  @Nullable
  protected Integer[] get(Event arg0)
  {
    ItemStack item = ((ArmourEquipEvent)arg0).getNewArmorPiece();
    Integer slot = Integer.valueOf(0);
    if (item.getType() == Material.LEATHER_HELMET) {
      slot = Integer.valueOf(5);
    } else if (item.getType() == Material.GOLD_HELMET) {
      slot = Integer.valueOf(5);
    } else if (item.getType() == Material.CHAINMAIL_HELMET) {
      slot = Integer.valueOf(5);
    } else if (item.getType() == Material.IRON_HELMET) {
      slot = Integer.valueOf(5);
    } else if (item.getType() == Material.DIAMOND_HELMET) {
      slot = Integer.valueOf(5);
    }
    
    if (item.getType() == Material.LEATHER_BOOTS) {
      slot = Integer.valueOf(8);
    } else if (item.getType() == Material.GOLD_BOOTS) {
      slot = Integer.valueOf(8);
    } else if (item.getType() == Material.CHAINMAIL_BOOTS) {
      slot = Integer.valueOf(8);
    } else if (item.getType() == Material.IRON_BOOTS) {
      slot = Integer.valueOf(8);
    } else if (item.getType() == Material.DIAMOND_BOOTS) {
      slot = Integer.valueOf(8);
    }
    
    if (item.getType() == Material.LEATHER_LEGGINGS) {
      slot = Integer.valueOf(7);
    } else if (item.getType() == Material.GOLD_LEGGINGS) {
      slot = Integer.valueOf(7);
    } else if (item.getType() == Material.CHAINMAIL_LEGGINGS) {
      slot = Integer.valueOf(7);
    } else if (item.getType() == Material.IRON_LEGGINGS) {
      slot = Integer.valueOf(7);
    } else if (item.getType() == Material.DIAMOND_LEGGINGS) {
      slot = Integer.valueOf(7);
    }
    if (item.getType() == Material.LEATHER_CHESTPLATE) {
      slot = Integer.valueOf(6);
    } else if (item.getType() == Material.GOLD_CHESTPLATE) {
      slot = Integer.valueOf(6);
    } else if (item.getType() == Material.CHAINMAIL_CHESTPLATE) {
      slot = Integer.valueOf(6);
    } else if (item.getType() == Material.IRON_CHESTPLATE) {
      slot = Integer.valueOf(6);
    } else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
      slot = Integer.valueOf(6);
    }
    return new Integer[] { slot };
  }
}
