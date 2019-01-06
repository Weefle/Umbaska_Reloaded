package uk.co.umbaska.AAC;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaCondition;

@Name("Is AAC Check Enabled")
@Syntaxes({"[AAC] (check %-hacktype%|%-hacktype% check) is (enabled|on)"})
@Dependency("AAC")
public class CondCheckEnabled
  extends UmbaskaCondition
{
  private Expression<HackType> hack;
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.hack = expr[0];
    return true;
  }
  
  public boolean check(Event e)
  {
    if (!AACAPIProvider.isAPILoaded()) {
      return false;
    }
    return AACAPIProvider.getAPI().isEnabled((HackType)this.hack.getSingle(e));
  }
}
