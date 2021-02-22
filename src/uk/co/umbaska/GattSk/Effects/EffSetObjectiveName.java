package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;






public class EffSetObjectiveName
  extends Effect
{
  private Expression<String> boardName;
  private Expression<String> objectiveName;
  private Expression<String> slot;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.objectiveName = (Expression<String>) args[0];
    this.boardName = (Expression<String>) args[1];
    this.slot = (Expression<String>) args[2];
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
    String slot = (String)this.slot.getSingle(arg0);
    
    if (((board == null ? 1 : 0) | (objective == null ? 1 : 0) | (slot == null ? 1 : 0)) != 0) {
      return;
    }
    
    ScoreboardManagers.setObjectiveDisplayName(board, objective.replace("&", "�"), slot);
  }
}
