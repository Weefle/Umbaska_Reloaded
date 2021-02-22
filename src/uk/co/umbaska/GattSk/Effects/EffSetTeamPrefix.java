package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;



public class EffSetTeamPrefix
  extends Effect
{
  private Expression<String> boardname;
  private Expression<String> teamName;
  private Expression<String> value;
  private boolean prefix;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.boardname = (Expression<String>) args[1];
    this.teamName = (Expression<String>) args[0];
    this.value = (Expression<String>) args[2];
    this.prefix = (arg3.mark == 0);
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "create new objective";
  }
  
  protected void execute(Event arg0) {
    String boardname = (String)this.boardname.getSingle(arg0);
    String teamname = (String)this.teamName.getSingle(arg0);
    String value = (String)this.value.getSingle(arg0);
    if (((teamname == null ? 1 : 0) | (boardname == null ? 1 : 0)) != 0) {
      return;
    }
    
    if (!this.prefix) {
      ScoreboardManagers.setTeamOption(boardname, teamname, "prefix", value, null, null);
    }
    else {
      ScoreboardManagers.setTeamOption(boardname, teamname, "suffix", value, null, null);
    }
  }
}
