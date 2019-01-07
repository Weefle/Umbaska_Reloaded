package uk.co.umbaska.Discord;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Discord;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("Discord - Connect to account")
@Syntaxes({"d[is[cord]]][ ]connect to [d[is[cord]]][ ]%dconnection%"})
@Discord
public class EffDiscordConnectAccount
  extends Effect
{
  public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
  
  protected void execute(Event e) {}

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return null;
}
}
