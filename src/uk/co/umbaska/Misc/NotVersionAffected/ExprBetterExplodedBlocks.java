package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;






public class ExprBetterExplodedBlocks
  extends SimpleExpression<Block>
{
  public Class<? extends Block> getReturnType()
  {
    return Block.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "better exploded blocks";
  }
  
  @Nullable
  protected Block[] get(Event arg0)
  {
    return (Block[])Collect.asArray(((EntityExplodeEvent)arg0).blockList().toArray());
  }
}
