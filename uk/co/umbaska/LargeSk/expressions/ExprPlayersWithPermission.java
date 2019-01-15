package uk.co.umbaska.LargeSk.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprPlayersWithPermission
  extends SimpleExpression<Player>
{
  private Expression<Player> players;
  private Expression<String> permission;
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = (Expression<Player>) expr[0];
    this.permission = (Expression<String>) expr[1];
    return true;
  }
  
  @Nullable
  protected Player[] get(Event e)
  {
    ArrayList<Player> r = new ArrayList<>();
    for (Player l : (Player[])this.players.getArray(e)) {
      if (l.hasPermission((String)this.permission.getSingle(e))) {
        r.add(l);
      }
    }
    return (Player[])r.toArray(new Player[r.size()]);
  }

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Players With Permission";
}
}
