package uk.co.umbaska.Misc.Looping;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Event;





public class ExprLoopSpecificBlocksSphere
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
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.materialsToLoop = args[0];
    this.loc = args[1];
    this.radius = args[2];
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
    List<Material> matList = new ArrayList();
    for (Material m : materials) {
      matList.add(m);
    }
    Integer r = Integer.valueOf(((Number)this.radius.getSingle(arg0)).intValue());
    Location l = (Location)this.loc.getSingle(arg0);
    List<Block> blks = sphere(l, r.intValue());
    for (Block b : blks) {
      if (!matList.contains(b.getType())) {
        blks.remove(b);
      }
    }
    Block[] barry = (Block[])blks.toArray(new Block[0]);
    return barry;
  }
  
  public ArrayList<Block> sphere(Location center, int radius) {
    ArrayList<Block> sphere = new ArrayList();
    for (int Y = -radius; Y < radius; Y++)
      for (int X = -radius; X < radius; X++)
        for (int Z = -radius; Z < radius; Z++)
          if (Math.sqrt(X * X + Y * Y + Z * Z) <= radius) {
            Block block = center.getWorld().getBlockAt(X + center.getBlockX(), Y + center.getBlockY(), Z + center.getBlockZ());
            sphere.add(block);
          }
    return sphere;
  }
}
