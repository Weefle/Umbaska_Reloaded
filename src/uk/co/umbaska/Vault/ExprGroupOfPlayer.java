package uk.co.umbaska.Vault;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;








public class ExprGroupOfPlayer
  extends SimpleExpression<String>
{
  private Expression<Player> player;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
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
    return "return plot owner";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    Player p = (Player)this.player.getSingle(arg0);
    if (p == null) {
      return null;
    }
    if (Main.perms == null) {
      Skript.warning("[Umbaska] Right oh', who tried to use permissions without Vault.");
      return null;
    }
    return new String[] { Main.perms.getPrimaryGroup(p) };
  }
}
