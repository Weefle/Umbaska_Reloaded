package uk.co.umbaska.ParticleProjectiles.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.ParticleProjectiles.ParticleProjectileHandler;





public class ExprAllParticleProjectiles
  extends SimpleExpression<String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "ParticleProjectile";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    return (String[])ParticleProjectileHandler.particleProjectiles.keySet().toArray(new String[ParticleProjectileHandler.particleProjectiles.keySet().size()]);
  }
}
