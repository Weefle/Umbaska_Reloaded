package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;







public class EffSetItemInEntity
  extends Effect
{
  private Expression<Entity> ent;
  private Expression<ItemStack> item;
  
  protected void execute(Event event)
  {
    if (((Entity)this.ent.getSingle(event)).getType() == EntityType.DROPPED_ITEM) {
      Item ent = (Item)this.ent.getSingle(event);
      ent.setItemStack((ItemStack)this.item.getSingle(event));
    }
    else {}
  }
  


  public String toString(Event event, boolean b)
  {
    return "Set Item Within Entity";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ent = expressions[0];
    this.item = expressions[1];
    return true;
  }
}
