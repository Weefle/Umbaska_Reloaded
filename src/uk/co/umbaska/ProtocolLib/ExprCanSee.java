package uk.co.umbaska.ProtocolLib;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.GattSk.Extras.Collect;



public class ExprCanSee
  extends SimpleExpression<Boolean>
{
  private Expression<Entity> ent;
  private Expression<Player> play;
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  

  public boolean isSingle()
  {
    return true;
  }
  


  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.ent = (Expression<Entity>) args[0];
    this.play = (Expression<Player>) args[1];
    return true;
  }
  

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get visibility";
  }
  

  @Nullable
  protected Boolean[] get(Event arg0)
  {
    Entity entity = (Entity)this.ent.getSingle(arg0);
    Player player = (Player)this.play.getSingle(arg0);
    if (((entity == null ? 1 : 0) | (player == null ? 1 : 0)) != 0) {
      return null;
    }
    return (Boolean[])Collect.asArray(new Boolean[] { Boolean.valueOf(Main.enthider.canSee(player, entity)) });
  }
}
