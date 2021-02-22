package uk.co.umbaska.Misc;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

public class ExprResponsePackResponse
  extends SimpleExpression<PlayerResourcePackStatusEvent.Status>
{
  public Class<? extends PlayerResourcePackStatusEvent.Status> getReturnType()
  {
    return PlayerResourcePackStatusEvent.Status.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    if (!ScriptLoader.isCurrentEvent(PlayerResourcePackStatusEvent.class)) {
      Skript.error("Cannot use resource pack status expression outside of a resource pack response event", ErrorQuality.SEMANTIC_ERROR);
      return false;
    }
    return true;
  }
  

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "ParticleProjectile";
  }
  
  @Nullable
  protected PlayerResourcePackStatusEvent.Status[] get(Event arg0) {
    return new PlayerResourcePackStatusEvent.Status[] { ((PlayerResourcePackStatusEvent)arg0).getStatus() };
  }
}
