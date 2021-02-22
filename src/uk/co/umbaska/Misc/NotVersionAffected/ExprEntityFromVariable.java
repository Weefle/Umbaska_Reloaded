package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;






public class ExprEntityFromVariable
  extends SimpleExpression<Entity>
{
  private Expression<Entity> entity;
  
  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
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
    return "return entity from variable";
  }
  

  @Nullable
  protected Entity[] get(Event arg0)
  {
    Entity p = (Entity)this.entity.getSingle(arg0);
    
    return new Entity[] { p };
  }
}
