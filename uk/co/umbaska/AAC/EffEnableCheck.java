package uk.co.umbaska.AAC;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("Enable hack-check")
@Syntaxes({"enable [hack[ ]]check %hacktype%", "enable %hacktype% [hack[ ]]check"})
@Dependency("AAC")
public class EffEnableCheck
  extends UmbaskaEffect
{
  private Expression<HackType> check;
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.check = expr[0];
    return true;
  }
  
  protected void execute(Event e)
  {
    if (AACAPIProvider.isAPILoaded()) {
      AACAPIProvider.getAPI().enableCheck((HackType)this.check.getSingle(e));
    }
  }
}
