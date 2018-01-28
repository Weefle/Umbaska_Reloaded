package uk.co.umbaska.Spawner;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;






public class ExprSpawnedType
  extends SimpleExpression<String>
{
  private Expression<Location> location;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.location = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get entity type of a spawner";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    Location l = (Location)this.location.getSingle(arg0);
    CreatureSpawner cs = (CreatureSpawner)l.getBlock().getState();
    String e = cs.getCreatureTypeName();
    return new String[] { e };
  }
}
