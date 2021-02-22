package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;

public class CondCheckEnabled extends Condition{
  private Expression<HackType> hack;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.hack = (Expression<HackType>) expr[0];
    return true;
  }
  
  public boolean check(Event e)
  {
    if (!AACAPIProvider.isAPILoaded()) {
      return false;
    }
    return AACAPIProvider.getAPI().isEnabled((HackType)this.hack.getSingle(e));
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Is AAC Check Enabled";
}
}
