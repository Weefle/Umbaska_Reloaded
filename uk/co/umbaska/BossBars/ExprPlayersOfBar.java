package uk.co.umbaska.BossBars;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;






public class ExprPlayersOfBar
  extends SimpleExpression<Player>
{
  Expression<BossBar> bar;
  
  protected Player[] get(Event event)
  {
    return (Player[])((BossBar)this.bar.getSingle(event)).getPlayers().toArray(new Player[((BossBar)this.bar.getSingle(event)).getPlayers().size()]);
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "Players of boss bar";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) expressions[0];
    return true;
  }
}
