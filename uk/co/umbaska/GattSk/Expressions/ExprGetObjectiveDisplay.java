package uk.co.umbaska.GattSk.Expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;







public class ExprGetObjectiveDisplay
  extends SimpleExpression<Objective>
{
  private Expression<String> slot;
  private Expression<String> board;
  
  public Class<? extends Objective> getReturnType()
  {
    return Objective.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.slot = args[0];
    this.board = args[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "entity spawn reason";
  }
  
  @Nullable
  protected Objective[] get(Event arg0)
  {
    String slot = (String)this.slot.getSingle(arg0);
    String board = (String)this.board.getSingle(arg0);
    if (((slot == null ? 1 : 0) | (board == null ? 1 : 0)) != 0) {
      return null;
    }
    return (Objective[])Collect.asArray(new Objective[] { ScoreboardManagers.getObjectiveDisplay(board, slot) });
  }
}
