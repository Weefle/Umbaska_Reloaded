package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Event;



public class ExprBlocksInCylinder
  extends SimpleExpression<Block>
{
  private Expression<Location> l;
  private Expression<Number> rad;
  private Expression<Number> height;
  
  public Class<? extends Block> getReturnType()
  {
    return Block.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean kl, SkriptParser.ParseResult pr)
  {
    this.rad = expr[0];
    this.height = expr[1];
    this.l = expr[2];
    return true;
  }
  
  public String toString(@Nullable Event event, boolean b)
  {
    return "Blocks in cylinder";
  }
  

  @Nullable
  protected Block[] get(Event event)
  {
    Integer height = Integer.valueOf(((Number)this.height.getSingle(event)).intValue());
    Integer r = Integer.valueOf(((Number)this.rad.getSingle(event)).intValue());
    Location l = (Location)this.l.getSingle(event);
    Block center = l.getBlock();
    List<Block> blks = new ArrayList();
    int cx = l.getBlockX();
    int cy = l.getBlockY();
    int cz = l.getBlockZ();
    int rSquared = r.intValue() * r.intValue();
    Boolean goDown = Boolean.valueOf(false);
    if (height.intValue() == 0) {
      return null;
    }
    if (height.intValue() < 0) {
      goDown = Boolean.valueOf(true);
      height = Integer.valueOf(height.intValue() / -1);
    }
    for (int currentheight = 0; currentheight < height.intValue(); currentheight++) {
      for (int x = cx - r.intValue(); x <= cx + r.intValue(); x++) {
        for (int z = cz - r.intValue(); z <= cz + r.intValue(); z++) {
          if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
            blks.add(l.getWorld().getBlockAt(x, cy, z));
          }
        }
      }
      if (!goDown.booleanValue()) {
        cy++;
      } else {
        cy--;
      }
    }
    Block[] barry = (Block[])blks.toArray(new Block[0]);
    return barry;
  }
}
