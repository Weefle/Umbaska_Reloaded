package uk.co.umbaska.UmbaskaCord;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;




public class EffSetBungeePlayerList
  extends Effect
{
  private Expression<String> list;
  
  protected void execute(Event event)
  {
    String p = (String)this.list.getSingle(event);
    if (p == null) {
      return;
    }
    p = p.replaceAll("\n", "\\|\\|").replaceAll("\\|\\|", "@nl@");
    Main main = Main.getInstance();
    main.oq.add("setplayerlist@@UMB@@" + p);
  }
  



  public String toString(Event event, boolean b)
  {
    return "Set Bungee Player List";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.list = (Expression<String>) expressions[0];
    return true;
  }
}
