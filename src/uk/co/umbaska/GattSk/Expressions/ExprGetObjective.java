package uk.co.umbaska.GattSk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;







public class ExprGetObjective
  extends SimpleExpression<String>
{
  private Expression<String> objective;
  private Expression<String> board;
  
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
    this.objective = (Expression<String>) args[0];
    this.board = (Expression<String>) args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "entity spawn reason";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    String obj = (String)this.objective.getSingle(arg0);
    String board = (String)this.board.getSingle(arg0);
    if (((obj == null ? 1 : 0) | (board == null ? 1 : 0)) != 0) {
      return null;
    }
    return (String[])Collect.asArray(new String[] { ScoreboardManagers.getObjective(board, obj) });
  }
}
