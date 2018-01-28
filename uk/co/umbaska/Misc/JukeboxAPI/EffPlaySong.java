package uk.co.umbaska.Misc.JukeboxAPI;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.oliverdunk.jukeboxapi.api.JukeboxAPI;
import com.oliverdunk.jukeboxapi.api.ResourceType;
import com.oliverdunk.jukeboxapi.api.models.Media;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;



public class EffPlaySong
  extends Effect
{
  private Expression<Player> p;
  private Expression<String> url;
  
  protected void execute(Event event)
  {
    Player[] players = (Player[])this.p.getAll(event);
    String url = (String)this.url.getSingle(event);
    Media media = new Media(ResourceType.MUSIC, url);
    for (Player p : players) {
      JukeboxAPI.play(p, media);
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "Jukebox API set song";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.p = expressions[1];
    this.url = expressions[0];
    
    return true;
  }
}
