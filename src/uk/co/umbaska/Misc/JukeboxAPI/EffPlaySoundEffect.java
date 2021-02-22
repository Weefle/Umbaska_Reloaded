package uk.co.umbaska.Misc.JukeboxAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;



public class EffPlaySoundEffect
  extends Effect
{
  private Expression<Player> p;
  private Expression<String> url;
  
  protected void execute(Event event)
  {
    Player[] players = (Player[])this.p.getAll(event);
    String url = (String)this.url.getSingle(event);
    Media media = new Media(ResourceType.SOUND_EFFECT, url);
    for (Player p : players) {
      JukeboxAPI.play(p, media);
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "Jukebox API play sound effect";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.p = (Expression<Player>) expressions[1];
    this.url = (Expression<String>) expressions[0];
    
    return true;
  }
}
