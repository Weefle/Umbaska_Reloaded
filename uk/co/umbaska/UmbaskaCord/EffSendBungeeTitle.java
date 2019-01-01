package uk.co.umbaska.UmbaskaCord;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<String>) expressions[2];
    this.title = (Expression<String>) expressions[0];
    this.subtitle = (Expression<String>) expressions[1];
    this.fadeIn = (Expression<Number>) expressions[3];
    this.fadeOut = (Expression<Number>) expressions[5];
    this.stay = (Expression<Number>) expressions[4];
    
    return true;
  }
}
