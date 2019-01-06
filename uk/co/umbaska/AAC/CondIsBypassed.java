package uk.co.umbaska.AAC;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaCondition;

@Name("Player is Bypassed")
@Syntaxes({"[AAC] %player%('s| is) bypass(ed by|ing) AAC"})
@Dependency("AAC")
public class CondIsBypassed
  extends UmbaskaCondition
{
  private Expression<Player> p;
  
  public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.p = e[0];
    return true;
  }
  
  public boolean check(Event e)
  {
    return (AACAPIProvider.isAPILoaded()) && (AACAPIProvider.getAPI().isBypassed((Player)this.p.getSingle(e)));
  }
}
