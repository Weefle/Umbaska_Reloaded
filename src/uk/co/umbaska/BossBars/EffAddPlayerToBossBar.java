package uk.co.umbaska.BossBars;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffAddPlayerToBossBar
  extends Effect
  implements Listener
{
  private Expression<BossBar> bar;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    ((BossBar)this.bar.getSingle(event)).addPlayer((Player)this.player.getSingle(event));
  }
  

  public String toString(Event event, boolean b)
  {
    return "Add Player to Boss Bar";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) expressions[1];
    this.player = (Expression<Player>) expressions[0];
    return true;
  }
}
