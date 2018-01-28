package uk.co.umbaska.Spawner;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;







public class ExprMFG_SpawnedType
  extends SimpleExpression<EntityType>
{
  private Expression<Block> block;
  
  public Class<? extends EntityType> getReturnType()
  {
    return EntityType.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.block = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get entity type of a spawner";
  }
  
  @Nullable
  protected EntityType[] get(Event arg0)
  {
    Block b = (Block)this.block.getSingle(arg0);
    CreatureSpawner cs = (CreatureSpawner)b.getState();
    EntityType e = cs.getSpawnedType();
    return new EntityType[] { e };
  }
}
