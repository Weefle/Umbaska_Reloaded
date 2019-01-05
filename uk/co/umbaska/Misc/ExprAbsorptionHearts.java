package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprAbsorptionHearts
  extends SimpleExpression<Number>
{
  private Expression<Player> player;
  
  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends Number> getReturnType() {
    return Number.class;
  }
  
  @Nullable
  protected Number[] get(Event arg0)
  {
    return new Number[] { Float.valueOf(((CraftPlayer)this.player.getSingle(arg0)).getHandle().getAbsorptionHearts()) };
  }
  

  public String toString(Event event, boolean b)
  {
    return "Absorption Hearts";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    return true;
  }
}
