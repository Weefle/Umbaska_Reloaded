package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;

public class EffSetPlayerScoreboard
  extends Effect
{
  private Expression<Player> players;
  private Expression<String> boardname;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = (Expression<Player>) args[0];
    this.boardname = (Expression<String>) args[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set player scoreboard";
  }
  
  protected void execute(Event arg0)
  {
    Player[] players = (Player[])this.players.getAll(arg0);
    String boardname = (String)this.boardname.getSingle(arg0);
    if (((players == null ? 1 : 0) | (boardname == null ? 1 : 0)) != 0) {
      return;
    }
    for (Player p : players) {
      ScoreboardManagers.setPlayerScoreboard(p, boardname);
    }
  }
}
