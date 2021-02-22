package uk.co.umbaska.GattSk.Effects;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.ScoreboardManagers;



public class EffTeamPlayer
  extends Effect
{
  private Expression<String> boardname;
  private Expression<String> teamName;
  private Expression<OfflinePlayer> player;
  private boolean add;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.boardname = (Expression<String>) args[2];
    this.teamName = (Expression<String>) args[1];
    this.player = (Expression<OfflinePlayer>) args[0];
    this.add = (arg3.mark == 0);
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "create new objective";
  }
  
  protected void execute(Event arg0) {
    String boardname = (String)this.boardname.getSingle(arg0);
    String teamname = (String)this.teamName.getSingle(arg0);
    OfflinePlayer[] player = (OfflinePlayer[])this.player.getAll(arg0);
    if (((teamname == null ? 1 : 0) | (boardname == null ? 1 : 0)) != 0) {
      return;
    }
    if (!this.add) {
      for (OfflinePlayer p : player) {
        ScoreboardManagers.addPlayerToTeam(boardname, teamname, p);
      }
    }
    if (this.add == true) {
      for (OfflinePlayer p : player) {
        ScoreboardManagers.removePlayerFromTeam(boardname, teamname, p);
      }
    }
  }
}
