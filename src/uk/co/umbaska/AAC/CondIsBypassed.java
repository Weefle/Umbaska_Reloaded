package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;

public class CondIsBypassed
  extends Condition
{
  private Expression<Player> p;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.p = (Expression<Player>) e[0];
    return true;
  }
  
  public boolean check(Event e)
  {
    return (AACAPIProvider.isAPILoaded()) && (AACAPIProvider.getAPI().isBypassed((Player)this.p.getSingle(e)));
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Player is Bypassed";
}
}
