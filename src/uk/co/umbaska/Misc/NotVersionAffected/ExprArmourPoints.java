package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;






public class ExprArmourPoints
  extends SimpleExpression<Double>
{
  private Expression<Player> player;
  
  public Class<? extends Double> getReturnType()
  {
    return Double.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = (Expression<Player>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return plot id at location";
  }
  

  @Nullable
  protected Double[] get(Event arg0)
  {
    Player p = (Player)this.player.getSingle(arg0);
    
    if (p == null) {
      return null;
    }
    PlayerInventory inv = p.getInventory();
    ItemStack boots = inv.getBoots();
    ItemStack helmet = inv.getHelmet();
    ItemStack chest = inv.getChestplate();
    ItemStack pants = inv.getLeggings();
    double red = 0.0D;
    if (boots != null) {
      if (helmet.getType() == Material.LEATHER_HELMET) { red += 0.04D;
      } else if (helmet.getType() == Material.GOLD_HELMET) { red += 0.08D;
      } else if (helmet.getType() == Material.CHAINMAIL_HELMET) { red += 0.08D;
      } else if (helmet.getType() == Material.IRON_HELMET) { red += 0.08D;
      } else if (helmet.getType() == Material.DIAMOND_HELMET) red += 0.12D;
    }
    if (chest != null) {
      if (boots.getType() == Material.LEATHER_BOOTS) { red += 0.04D;
      } else if (boots.getType() == Material.GOLD_BOOTS) { red += 0.04D;
      } else if (boots.getType() == Material.CHAINMAIL_BOOTS) { red += 0.04D;
      } else if (boots.getType() == Material.IRON_BOOTS) { red += 0.08D;
      } else if (boots.getType() == Material.DIAMOND_BOOTS) red += 0.12D;
    }
    if (helmet != null) {
      if (pants.getType() == Material.LEATHER_LEGGINGS) { red += 0.08D;
      } else if (pants.getType() == Material.GOLD_LEGGINGS) { red += 0.12D;
      } else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) { red += 0.16D;
      } else if (pants.getType() == Material.IRON_LEGGINGS) { red += 0.2D;
      } else if (pants.getType() == Material.DIAMOND_LEGGINGS) red += 0.24D;
    }
    if (pants != null) {
      if (chest.getType() == Material.LEATHER_CHESTPLATE) { red += 0.12D;
      } else if (chest.getType() == Material.GOLD_CHESTPLATE) { red += 0.2D;
      } else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) { red += 0.2D;
      } else if (chest.getType() == Material.IRON_CHESTPLATE) { red += 0.24D;
      } else if (chest.getType() == Material.DIAMOND_CHESTPLATE) red += 0.32D;
    }
    return new Double[] { Double.valueOf(red) };
  }
}
