package uk.co.umbaska.GattSk.Effects.SimpleScoreboards;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;



public class EffSetTitle
  extends Effect
{
  private Expression<String> scoreboardName;
  private Expression<String> value;
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.scoreboardName = args[0];
    this.value = args[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "simple set title";
  }
  

  protected void execute(Event arg0)
  {
    String board = (String)this.scoreboardName.getSingle(arg0);
    if (board == null) {
      return;
    }
    SimpleScoreboard.setScoreboardTitle(board, (String)this.value.getSingle(arg0));
  }
}
