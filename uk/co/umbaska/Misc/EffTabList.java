package uk.co.umbaska.Misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;




@Deprecated
public class EffTabList
  extends Effect
  implements Listener
{
  private Expression<Player> Players;
  private Expression<String> format;
  private boolean footer;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.footer = (parse.mark == 0);
    this.format = exprs[0];
    this.Players = exprs[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "tablist header 1.8";
  }
  


  protected void execute(Event event)
  {
    Player[] playerlist = (Player[])this.Players.getAll(event);
    String format = this.format.toString(event, true);
  }
}
