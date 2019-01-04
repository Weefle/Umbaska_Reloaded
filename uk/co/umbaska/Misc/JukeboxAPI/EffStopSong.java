package uk.co.umbaska.Misc.JukeboxAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;





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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.p = (Expression<Player>) expressions[0];
    
    return true;
  }
}
