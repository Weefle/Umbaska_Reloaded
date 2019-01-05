package uk.co.umbaska.Misc.NotVersionAffected;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;




public class ExprFallDistance
  extends SimpleExpression<Number>
{
  private Expression<Entity> ent;
  
  public Class<? extends Number> getReturnType()
  {
    return Number.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean kl, SkriptParser.ParseResult pr)
  {
    this.ent = (Expression<Entity>) expr[0];
    return true;
  }
  
  public String toString(@Nullable Event event, boolean b)
  {
    return "Fall Distance";
  }
  
  @Nullable
  protected Number[] get(Event event)
  {
    return new Number[] { Float.valueOf(((Entity)this.ent.getSingle(event)).getFallDistance()) };
  }
}
