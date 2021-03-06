package uk.co.umbaska.GattSk.Effects.SimpleScoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;



public class EffSetSlot
  extends Effect
{
  private Expression<Number> slot;
  private Expression<String> scoreboardName;
  private Expression<String> value;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.scoreboardName = (Expression<String>) args[1];
    this.slot = (Expression<Number>) args[0];
    this.value = (Expression<String>) args[2];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "simple set slot";
  }
  

  protected void execute(Event arg0)
  {
    String board = (String)this.scoreboardName.getSingle(arg0);
    String txt = (String)this.value.getSingle(arg0);
    if ((board == null) || (txt == null)) {
      return;
    }
    SimpleScoreboard.setScore(board, Integer.valueOf(((Number)this.slot.getSingle(arg0)).intValue()), txt);
  }
}
