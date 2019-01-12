package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;
import uk.co.umbaska.Registration.Syntaxes;

@Syntaxes({"enable [hack[ ]]check %hacktype%", "enable %hacktype% [hack[ ]]check"})
public class EffEnableCheck
  extends Effect
{
  private Expression<HackType> check;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.check = (Expression<HackType>) expr[0];
    return true;
  }
  
  protected void execute(Event e)
  {
    if (AACAPIProvider.isAPILoaded()) {
      AACAPIProvider.getAPI().enableCheck((HackType)this.check.getSingle(e));
    }
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Enable hack-check";
}
}
