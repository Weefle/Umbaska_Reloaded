package uk.co.umbaska.AAC;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaEffect;

@Name("Reload Permission Cache of AAC")
@Syntaxes({"AAC reload permission(s|[s] cache)", "reload permission(s|[s] cache) of AAC", "reload AAC['s] permission(s|[s] cache)"})
@Dependency("AAC")
public class EffReloadPermissionCache
  extends UmbaskaEffect
{
  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return true;
  }
  
  protected void execute(Event arg0)
  {
    if (AACAPIProvider.isAPILoaded() == true) {
      AACAPIProvider.getAPI().reloadPermissionCache();
    }
  }
}
