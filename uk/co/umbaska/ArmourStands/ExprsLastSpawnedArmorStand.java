package uk.co.umbaska.ArmourStands;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.Main;

public class ExprsLastSpawnedArmorStand
  extends SimpleExpression<Entity>
{
  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "last spawned armor stand";
  }
  
  @Nullable
  public Entity[] get(Event arg0)
  {
    return (Entity[])Collect.asArray(new Entity[] { Main.armorStand });
  }
}
