package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;







public class ExprGetScore
  extends SimpleExpression<Integer>
{
  private Expression<String> boardname;
  private Expression<String> objective;
  private Expression<String> score;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.boardname = (Expression<String>) args[0];
    this.objective = (Expression<String>) args[1];
    this.score = (Expression<String>) args[2];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "entity spawn reason";
  }
  

  @Nullable
  protected Integer[] get(Event arg0)
  {
    String board = (String)this.boardname.getSingle(arg0);
    String obj = (String)this.objective.getSingle(arg0);
    String scoretofind = (String)this.score.getSingle(arg0);
    
    if (((board == null ? 1 : 0) | (obj == null ? 1 : 0) | (scoretofind == null ? 1 : 0)) != 0) {
      return null;
    }
    
    Integer score = Integer.valueOf(ScoreboardManagers.getScore(board, obj, scoretofind));
    
    return (Integer[])Collect.asArray(new Integer[] { score });
  }
}
