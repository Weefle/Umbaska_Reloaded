package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class EffReloadAAC
  extends Effect
{
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  protected void execute(Event arg0)
  {
    if (AACAPIProvider.isAPILoaded() == true) {
      AACAPIProvider.getAPI().reloadAAC();
    }
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Reload AAC";
}
}
