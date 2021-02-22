package uk.co.umbaska.UmbaskaCord;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;




public class EffSetBungeeIcon
  extends Effect
{
  private Expression<String> link;
  
  protected void execute(Event event)
  {
    String p = (String)this.link.getSingle(event);
    if (p == null) {
      return;
    }
    Main main = Main.getInstance();
    main.oq.add("seticonURL@@UMB@@" + p);
  }
  


  public String toString(Event event, boolean b)
  {
    return "Set Bungee Server Icon\t";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.link = (Expression<String>) expressions[0];
    return true;
  }
}
