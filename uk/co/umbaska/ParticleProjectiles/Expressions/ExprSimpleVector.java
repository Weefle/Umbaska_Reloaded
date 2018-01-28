package uk.co.umbaska.ParticleProjectiles.Expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;




public class ExprSimpleVector
  extends SimpleExpression<Vector>
{
  private Expression<Number> x;
  private Expression<Number> y;
  private Expression<Number> z;
  
  public Class<? extends Vector> getReturnType()
  {
    return Vector.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.x = args[0];
    this.y = args[1];
    this.z = args[2];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "simpleVector";
  }
  
  @Nullable
  protected Vector[] get(Event arg0)
  {
    return new Vector[] { new Vector(((Number)this.x.getSingle(arg0)).doubleValue(), ((Number)this.y.getSingle(arg0)).doubleValue(), ((Number)this.z.getSingle(arg0)).doubleValue()) };
  }
}
