package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;








public class ExprSpawnReasonOfEntity
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
    return "entity spawn reason";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    Entity e = (Entity)this.entity.getSingle(arg0);
    String out = null;
    if (e.hasMetadata("spawnreason")) {
      out = e.getMetadata("spawnreason").toString();
    } else {
      return null;
    }
    return new String[] { out };
  }
}
