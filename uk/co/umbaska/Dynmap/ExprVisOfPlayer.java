package uk.co.umbaska.Dynmap;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;





public class ExprVisOfPlayer
  extends SimpleExpression<Boolean>
{
  private Expression<Player> player;
  
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
    this.player = (Expression<Player>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return Dynmap vis of player";
  }
  

  @Nullable
  protected Boolean[] get(Event arg0)
  {
    Player p = (Player)this.player.getSingle(arg0);
    
    if (p == null) {
      return null;
    }
    Boolean out = Boolean.valueOf(Main.api.getPlayerVisbility(p));
    return new Boolean[] { out };
  }
}
