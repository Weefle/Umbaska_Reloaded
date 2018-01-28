package uk.co.umbaska.Misc.JukeboxAPI;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.oliverdunk.jukeboxapi.api.JukeboxAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;





public class EffStopSong
  extends Effect
{
  private Expression<Player> p;
  
  protected void execute(Event event)
  {
    Player[] players = (Player[])this.p.getAll(event);
    for (Player p : players) {
      JukeboxAPI.stopMusic(p);
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "Jukebox API stop song";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.p = expressions[0];
    
    return true;
  }
}
