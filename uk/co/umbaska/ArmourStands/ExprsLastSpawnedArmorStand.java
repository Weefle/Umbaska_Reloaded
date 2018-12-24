package uk.co.umbaska.ArmourStands;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.GattSk.Extras.Collect;

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
