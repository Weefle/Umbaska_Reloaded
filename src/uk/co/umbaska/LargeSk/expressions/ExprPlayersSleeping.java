package uk.co.umbaska.LargeSk.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprPlayersSleeping
  extends SimpleExpression<Player>
{
  private Expression<Player> players;
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = (Expression<Player>) expr[0];
    return true;
  }
  
  public String toString(@Nullable Event e, boolean arg1)
  {
    return "players flying";
  }
  
  @Nullable
  protected Player[] get(Event e)
  {
    ArrayList<Player> r = new ArrayList<>();
    for (Player l : (Player[])this.players.getArray(e)) {
      if (l.isSleeping()) {
        r.add(l);
      }
    }
    return (Player[])r.toArray(new Player[r.size()]);
  }
  
  public static void register()
  {
    Skript.registerExpression(ExprPlayersSleeping.class, Player.class, ExpressionType.SIMPLE, new String[] { "%players% sleeping", "sleeping %players%" });
  }
}
