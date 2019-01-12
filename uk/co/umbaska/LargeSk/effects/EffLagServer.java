package uk.co.umbaska.LargeSk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.Registration.Syntaxes;

@Syntaxes({"lag [the] server for %timespan%", "(make|create) a %timespan% lag[[ ]spike]"})
public class EffLagServer
  extends Effect
{
  private Expression<Timespan> time;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.time = (Expression<Timespan>) expr[0];
    return true;
  }
  
  protected void execute(Event e)
  {
    int something = 2;
    try
    {
      Thread.sleep(((Timespan)this.time.getSingle(e)).getMilliSeconds());
    }
    catch (InterruptedException ex)
    {
      Main.getInstance().getLogger().warning("The Lag Server Effect was interrupted: " + ex.getMessage());
    }
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Lag the server";
}
}
