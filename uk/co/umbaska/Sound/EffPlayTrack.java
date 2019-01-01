package uk.co.umbaska.Sound;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;








@SuppressWarnings("deprecation")
public class EffPlayTrack
  extends Effect
{
  private Expression<String> trk;
  private Expression<Player> ply;
  
  protected void execute(Event event)
  {
    Player p = (Player)this.ply.getSingle(event);
    String t = (String)this.trk.getSingle(event);
    if (p == null)
      return;
    if (t == null) {
      return;
    }
    Song s = NBSDecoder.parse(new File(t));
    SongPlayer sp = new RadioSongPlayer(s);
    sp.setAutoDestroy(true);
    sp.addPlayer(p);
    sp.setPlaying(true);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Playing song";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.trk = (Expression<String>) expressions[0];
    this.ply = (Expression<Player>) expressions[1];
    return true;
  }
}
