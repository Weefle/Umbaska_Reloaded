package uk.co.umbaska.LargeSk.effects;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;
import uk.co.umbaska.Umbaska;

@Name("Lag the server")
@Syntaxes({"lag [the] server for %timespan%", "(make|create) a %timespan% lag[[ ]spike]"})
public class EffLagServer
  extends UmbaskaEffect
{
  private Expression<Timespan> time;
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.time = expr[0];
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
      Umbaska.logWarning(new Object[] { "The Lag Server Effect was interrupted: " + ex.getMessage() });
    }
  }
}
