package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprYawOfPlayer
  extends SimpleExpression<Float>
{
  private Expression<Player> player;
  
  public Class<? extends Float> getReturnType()
  {
    return Float.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  


  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = (Expression<Player>) exprs[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "yaw of %player%";
  }
  
  @Nullable
  protected Float[] get(Event e)
  {
    Player player = (Player)this.player.getSingle(e);
    if (player == null)
      return null;
    return new Float[] { Float.valueOf(player.getLocation().getYaw()) };
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player player = (Player)this.player.getSingle(e);
    if (player == null)
      return;
    Float yaw = (Float)delta[0];
    if (mode == Changer.ChangeMode.SET)
    {
      Location loc = player.getLocation();
      loc.setYaw(yaw.floatValue());
      player.teleport(loc);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Float.class });
  }
}
