package uk.co.umbaska.UUID;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;


public class ExprUUIDOfEntity
  extends SimpleExpression<String>
{
  private Expression<Entity> entity;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.entity = (Expression<Entity>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return uuids of entity";
  }
  
  @Nullable
  protected String[] get(Event arg0) {
    Entity entities = (Entity)this.entity.getSingle(arg0);
    if (entities.getType() == EntityType.PLAYER) {
      return new String[] { ((Player)entities).getUniqueId().toString() };
    }
    return new String[] { entities.getUniqueId().toString() };
  }
}
