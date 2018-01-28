package uk.co.umbaska.Misc;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprPitchOfPlayer
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
  


  public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = exprs[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "pitch of %player%";
  }
  
  @Nullable
  protected Float[] get(Event e)
  {
    Player player = (Player)this.player.getSingle(e);
    if (player == null)
      return null;
    return new Float[] { Float.valueOf(player.getLocation().getPitch()) };
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player player = (Player)this.player.getSingle(e);
    if (player == null)
      return;
    Float pitch = (Float)delta[0];
    if (mode == Changer.ChangeMode.SET)
    {
      Location loc = player.getLocation();
      loc.setPitch(pitch.floatValue());
      player.teleport(loc);
    }
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Float.class });
  }
}
