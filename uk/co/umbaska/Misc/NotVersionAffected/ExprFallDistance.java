package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;




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
  

  public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean kl, SkriptParser.ParseResult pr)
  {
    this.ent = expr[0];
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
