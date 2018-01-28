package uk.co.umbaska.Misc.NotVersionAffected;

import ca.thederpygolems.armorequip.ArmourEquipEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;








public class ExprOldArmour
  extends SimpleExpression<ItemStack>
{
  public Class<? extends ItemStack> getReturnType()
  {
    return ItemStack.class;
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
  protected ItemStack[] get(Event arg0)
  {
    return new ItemStack[] { ((ArmourEquipEvent)arg0).getOldArmorPiece() };
  }
}
