package uk.co.umbaska.Misc.Looping;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;





public class ExprLoopTransparentBlocks
  extends SimpleExpression<Block>
{
  private Expression<Location> locc1;
  private Expression<Location> locc2;
  
  public Class<? extends Block> getReturnType()
  {
    return Block.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.locc1 = (Expression<Location>) args[0];
    this.locc2 = (Expression<Location>) args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return all blocks";
  }
  
  @Nullable
  protected Block[] get(Event arg0)
  {
    Location loc1 = (Location)this.locc1.getSingle(arg0);
    Location loc2 = (Location)this.locc2.getSingle(arg0);
    
    if (loc1 == null)
      return null;
    if (loc2 == null) {
      return null;
    }
    
    List<Block> blocks = new ArrayList<>();
    
    int topBlockX = loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
    int bottomBlockX = loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
    
    int topBlockY = loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY();
    int bottomBlockY = loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY();
    
    int topBlockZ = loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ();
    int bottomBlockZ = loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ();
    
    for (int x = bottomBlockX; x <= topBlockX; x++)
    {
      for (int z = bottomBlockZ; z <= topBlockZ; z++)
      {
        for (int y = bottomBlockY; y <= topBlockY; y++)
        {
          Block block = loc1.getWorld().getBlockAt(x, y, z);
          Material mBlock = block.getType();
          if (mBlock.isTransparent()) {
            blocks.add(block);
          }
        }
      }
    }
    Block[] out = new Block[blocks.size()];
    out = (Block[])blocks.toArray(out);
    return out;
  }
}
