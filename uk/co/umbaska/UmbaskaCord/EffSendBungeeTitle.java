package uk.co.umbaska.UmbaskaCord;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;

public class EffSendBungeeTitle extends Effect
{
  private Expression<String> player;
  private Expression<String> title;
  private Expression<String> subtitle;
  private Expression<Number> fadeIn;
  private Expression<Number> fadeOut;
  private Expression<Number> stay;
  
  protected void execute(Event event)
  {
    String p = (String)this.player.getSingle(event);
    String t = (String)this.title.getSingle(event);
    String st = (String)this.subtitle.getSingle(event);
    Integer fi = Integer.valueOf(((Number)this.fadeIn.getSingle(event)).intValue());
    Integer fo = Integer.valueOf(((Number)this.fadeOut.getSingle(event)).intValue());
    Integer s = Integer.valueOf(((Number)this.stay.getSingle(event)).intValue());
    Main main = Main.getInstance();
    main.oq.add("sendtitle@@UMB@@" + p + "@@UMB@@" + t + "@@UMB@@" + st + "@@UMB@@" + fi + "@@UMB@@" + fo + "@@UMB@@" + s);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Set Bungee Server Icon\t";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[2];
    this.title = expressions[0];
    this.subtitle = expressions[1];
    this.fadeIn = expressions[3];
    this.fadeOut = expressions[5];
    this.stay = expressions[4];
    
    return true;
  }
}
