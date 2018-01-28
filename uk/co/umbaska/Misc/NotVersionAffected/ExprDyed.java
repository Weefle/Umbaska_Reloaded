package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;



public class ExprDyed
  extends SimpleExpression<ItemStack>
{
  private Expression<Color> color;
  private Expression<ItemStack> item;
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.color = args[1];
    this.item = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "dyed item";
  }
  

  @Nullable
  protected ItemStack[] get(Event arg0)
  {
    ItemStack itemStack = (ItemStack)this.item.getSingle(arg0);
    Color c = (Color)this.color.getSingle(arg0);
    
    if (Dyable.getDyable().contains(itemStack.getType())) {
      LeatherArmorMeta lam = (LeatherArmorMeta)itemStack.getItemMeta();
      lam.setColor(c.getBukkitColor());
      itemStack.setItemMeta(lam);
    }
    
    return new ItemStack[] { itemStack };
  }
  
  public Class<? extends ItemStack> getReturnType() {
    return ItemStack.class;
  }
  
  private static enum Dyable {
    LEATHER_HELMET(Material.LEATHER_HELMET),  LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE),  LEATHER_PANTS(Material.LEATHER_LEGGINGS),  LEATHER_BOOTS(Material.LEATHER_BOOTS);
    

    private Dyable(Material type) { this.type = type; }
    
    private Material type;
    public static List<Material> getDyable() { List<Material> types = new ArrayList();
      for (Dyable t : values()) {
        types.add(t.type);
      }
      return types;
    }
  }
}
