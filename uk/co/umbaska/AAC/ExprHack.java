package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.HackType;
import uk.co.umbaska.LargeSk.events.EvtPlayerViolation;

public class ExprHack
  extends SimpleExpression<HackType>
{
  HackType hack;
  
  public Class<? extends HackType> getReturnType()
  {
    return HackType.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    if (!ScriptLoader.isCurrentEvent(EvtPlayerViolation.class))
    {
      Skript.error("The hack expression can only be used inside Player Violation Event. Else you may want to use %hacktype%");
      
      return false;
    }
    return true;
  }
  
  @Nullable
  protected HackType[] get(Event e)
  {
    EvtPlayerViolation event = (EvtPlayerViolation)e;
    this.hack = event.getHack();
    return new HackType[] { this.hack };
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Hack inside Violation Event";
}
}
