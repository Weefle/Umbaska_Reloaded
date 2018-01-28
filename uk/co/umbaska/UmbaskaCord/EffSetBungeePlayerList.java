package uk.co.umbaska.UmbaskaCord;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import org.bukkit.event.Event;
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
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.list = expressions[0];
    return true;
  }
}
