package uk.co.umbaska.BossBars;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;







public class EffRemoveAllPlayersFromBar
  extends Effect
  implements Listener
{
  private Expression<BossBar> bar;
  
  protected void execute(Event event)
  {
    ((BossBar)this.bar.getSingle(event)).removeAll();
  }
  

  public String toString(Event event, boolean b)
  {
    return "Remove all Players from Boss Bar";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = expressions[0];
    return true;
  }
}
