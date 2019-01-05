package uk.co.umbaska.Misc.UM2_0;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEntitiesWithin
  extends SimpleExpression<Entity>
{
  private Expression<Location> loc1;
  private Expression<Location> loc2;
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.loc1 = (Expression<Location>) args[0];
    this.loc2 = (Expression<Location>) args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "entities within x and x";
  }
  

  @Nullable
  protected Entity[] get(Event arg0)
  {
    Location l1 = (Location)this.loc1.getSingle(arg0);
    Location l2 = (Location)this.loc2.getSingle(arg0);
    Location middle = null;
    if (l1.getBlockY() > l2.getBlockY()) {
      middle = l1;
    } else {
      middle = l2;
    }
    

    Number newx = Double.valueOf((l1.getX() + l2.getBlockX()) / 2.0D);
    Number newy = Double.valueOf((l1.getY() + l2.getBlockY()) / 2.0D);
    Number newz = Double.valueOf((l1.getZ() + l2.getBlockZ()) / 2.0D);
    middle.setX(newx.doubleValue());
    middle.setY(newy.doubleValue());
    middle.setZ(newz.doubleValue());
    
    Number offsetx = Integer.valueOf(l1.getBlockX() - middle.getBlockX());
    Number offsety = Integer.valueOf(l1.getBlockY() - middle.getBlockY());
    Number offsetz = Integer.valueOf(l1.getBlockZ() - middle.getBlockZ());
    
    WitherSkull dummy = (WitherSkull)middle.getWorld().spawn(middle, WitherSkull.class);
    List<Entity> l = dummy.getNearbyEntities(offsetx.doubleValue(), offsety.doubleValue(), offsetz.doubleValue());
    dummy.remove();
    Entity[] ent = (Entity[])l.toArray(new Entity[0]);
    return ent;
  }
  


  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
  }
}
