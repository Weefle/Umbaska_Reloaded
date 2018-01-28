package uk.co.umbaska.GattSk.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;

public class EffNewObjective
  extends Effect
{
  private Expression<String> scoreboardType;
  private Expression<String> boardname;
  private Expression<String> objectivename;
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.scoreboardType = args[0];
    this.boardname = args[1];
    this.objectivename = args[2];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "create new objective";
  }
  
  protected void execute(Event arg0)
  {
    String scoreboardtype = (String)this.scoreboardType.getSingle(arg0);
    String boardname = (String)this.boardname.getSingle(arg0);
    String objectivename = (String)this.objectivename.getSingle(arg0);
    if (((scoreboardtype == null ? 1 : 0) | (boardname == null ? 1 : 0) | (objectivename == null ? 1 : 0)) != 0) {
      return;
    }
    ScoreboardManagers.createObjective(boardname, objectivename, scoreboardtype);
  }
}
