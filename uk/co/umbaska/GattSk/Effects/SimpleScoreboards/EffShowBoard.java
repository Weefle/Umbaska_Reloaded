package uk.co.umbaska.GattSk.Effects.SimpleScoreboards;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;




public class EffShowBoard
  extends Effect
{
  private Expression<Player> players;
  private Expression<String> scoreboardName;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.scoreboardName = (Expression<String>) args[1];
    this.players = (Expression<Player>) args[0];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "simple show board";
  }
  

  protected void execute(Event arg0)
  {
    String board = (String)this.scoreboardName.getSingle(arg0);
    if (board == null) {
      return;
    }
    for (Player p : (Player[])this.players.getAll(arg0)) {
      SimpleScoreboard.showSimpleBoard(p, board);
    }
  }
}
