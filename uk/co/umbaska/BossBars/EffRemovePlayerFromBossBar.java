package uk.co.umbaska.BossBars;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;






public class EffRemovePlayerFromBossBar
  extends Effect
  implements Listener
{
  private Expression<BossBar> bar;
  private Expression<Player> player;
  
  protected void execute(Event event)
  {
    ((BossBar)this.bar.getSingle(event)).removePlayer((Player)this.player.getSingle(event));
  }
  

  public String toString(Event event, boolean b)
  {
    return "Add Player to Boss Bar";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.bar = expressions[1];
    this.player = expressions[0];
    return true;
  }
}
