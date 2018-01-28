package uk.co.umbaska.Misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.ImageManager.ImgInChat;








public class EffShowImage
  extends Effect
{
  private Expression<String> textdata;
  private Expression<String> file;
  private Expression<Player> player;
  private Integer matchType;
  
  protected void execute(Event event)
  {
    Player p = (Player)this.player.getSingle(event);
    String f = (String)this.file.getSingle(event);
    String[] t = (String[])this.textdata.getAll(event);
    if ((p == null) || (f == null) || (t == null)) {
      return;
    }
    if (this.matchType.intValue() == 0) {
      ImgInChat.ShowImg(p, f, t);
    } else {
      ImgInChat.ShowImgFromURL(p, f, t);
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "show %player% image %string% with %strings% [using char %string%] #localshow %player% image from %string% with %strings% [using char %string%] #online";
  }
  


  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.matchType = Integer.valueOf(i);
    this.player = expressions[0];
    this.file = expressions[1];
    this.textdata = expressions[2];
    if (expressions[3] == null) {}
    

    return true;
  }
}
