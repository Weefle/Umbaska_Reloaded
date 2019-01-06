package uk.co.umbaska.AAC;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("TPS of Server by AAC")
@Syntaxes({"[AAC] tps", "tps (of|by) AAC"})
@Dependency("AAC")
public class ExprAacTps
  extends SimpleUmbaskaExpression<Double>
{
  public Class<? extends Double> getReturnType()
  {
    return Double.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  @Nullable
  protected Double[] get(Event e)
  {
    if (AACAPIProvider.isAPILoaded()) {
      return new Double[] { Double.valueOf(AACAPIProvider.getAPI().getTPS()) };
    }
    return null;
  }
}
