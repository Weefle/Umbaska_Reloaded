package uk.co.umbaska.ParticleProjectiles.Expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.HashMap;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectile;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;





public class ExprHasStopped
  extends SimpleExpression<Boolean>
{
  private Expression<String> name;
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.name = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "ParticleProjectile";
  }
  
  @Nullable
  protected Boolean[] get(Event arg0)
  {
    if (ParticleProjectileHandler.particleProjectiles.containsKey(this.name.getSingle(arg0))) {
      return new Boolean[] { Boolean.valueOf(((ParticleProjectile)ParticleProjectileHandler.particleProjectiles.get(this.name.getSingle(arg0))).isRunning()) };
    }
    return new Boolean[] { Boolean.valueOf(false) };
  }
}
