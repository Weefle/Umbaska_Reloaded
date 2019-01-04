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





public class ExprLoopSpecificBlocksCyl
  extends SimpleExpression<Block>
{
  private Expression<Location> loc;
  private Expression<Material> materialsToLoop;
  private Expression<Number> radius;
  private Expression<Number> height;
  
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
    this.materialsToLoop = (Expression<Material>) args[0];
    this.loc = (Expression<Location>) args[1];
    this.radius = (Expression<Number>) args[2];
    this.height = (Expression<Number>) args[3];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return all blocks";
  }
  
  @Nullable
  protected Block[] get(Event arg0)
  {
    Material[] materials = (Material[])this.materialsToLoop.getAll(arg0);
    List<Material> matList = new ArrayList<>();
    for (Material m : materials) {
      matList.add(m);
    }
    Integer height = Integer.valueOf(((Number)this.height.getSingle(arg0)).intValue());
    Integer r = Integer.valueOf(((Number)this.radius.getSingle(arg0)).intValue());
    Location l = (Location)this.loc.getSingle(arg0);
    List<Block> blks = new ArrayList<>();
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
            Block b = l.getWorld().getBlockAt(x, cy, z);
            if (matList.contains(b.getType())) {
              blks.add(b);
            }
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
