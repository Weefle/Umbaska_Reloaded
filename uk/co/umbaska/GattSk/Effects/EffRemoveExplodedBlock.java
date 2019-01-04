package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;





public class EffRemoveExplodedBlock
  extends Effect
{
  private Expression<Block> block;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.block = (Expression<Block>) exprs[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "remove exploded block";
  }
  
  protected void execute(Event event) {
    Block[] blocks = (Block[])this.block.getAll(event);
    

    for (Block bl : blocks)
    {
      ((EntityExplodeEvent)event).blockList().remove(bl);
    }
  }
}
