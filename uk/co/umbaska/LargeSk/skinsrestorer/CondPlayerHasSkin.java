package uk.co.umbaska.LargeSk.skinsrestorer;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import skinsrestorer.shared.api.SkinsRestorerAPI;

public class CondPlayerHasSkin
  extends Condition
{
  private Expression<OfflinePlayer> p;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.p = (Expression<OfflinePlayer>) e[0];
    return true;
  }
  
  public boolean check(Event e)
  {
    return SkinsRestorerAPI.hasSkin(((OfflinePlayer)this.p.getSingle(e)).getName()) == true;
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return null;
}
}
