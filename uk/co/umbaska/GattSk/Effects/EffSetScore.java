package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;



public class EffSetScore
  extends Effect
{
  private Expression<String> boardName;
  private Expression<String> objectiveName;
  private Expression<String> scoreName;
  private Expression<Number> score;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.boardName = (Expression<String>) args[1];
    this.objectiveName = (Expression<String>) args[2];
    this.scoreName = (Expression<String>) args[0];
    this.score = (Expression<Number>) args[3];
    return true;
  }
  

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set scoreboard score";
  }
  
  protected void execute(Event arg0)
  {
    String board = (String)this.boardName.getSingle(arg0);
    String objective = (String)this.objectiveName.getSingle(arg0);
    String namescore = (String)this.scoreName.getSingle(arg0);
    Number score = (Number)this.score.getSingle(arg0);
    
    if (((board == null ? 1 : 0) | (objective == null ? 1 : 0) | (namescore == null ? 1 : 0) | (score == null ? 1 : 0)) != 0) {
      return;
    }
    
    ScoreboardManagers.setScore(board, objective, namescore, Integer.valueOf(score.intValue()));
  }
}
