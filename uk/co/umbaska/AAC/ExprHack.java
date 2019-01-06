package uk.co.umbaska.AAC;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import me.konsolas.aac.api.HackType;
import org.bukkit.event.Event;
import uk.co.umbaska.LargeSk.events.EvtPlayerViolation;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Hack inside Violation Event")
@Syntaxes({"hack", "cheat", "violation"})
@Dependency("AAC")
public class ExprHack
  extends SimpleUmbaskaExpression<HackType>
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
}
