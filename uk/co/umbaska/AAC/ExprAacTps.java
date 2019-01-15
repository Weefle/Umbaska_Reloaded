package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class ExprAacTps
  extends SimpleExpression<Double>
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

@Override
public boolean isSingle() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "TPS of Server by AAC";
}
}
