package uk.co.umbaska.LargeSk.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Syntaxes;

@Syntaxes({"%players% sneaking", "sneaking %players%"})
public class ExprPlayersSneaking
  extends SimpleExpression<Player>
{
  private Expression<Player> players;
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = (Expression<Player>) expr[0];
    return true;
  }
  
  @Nullable
  protected Player[] get(Event e)
  {
    ArrayList<Player> r = new ArrayList();
    for (Player l : (Player[])this.players.getArray(e)) {
      if (l.isSneaking()) {
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
	return "Players Who Are Sneaking";
}
}
