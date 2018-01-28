package uk.co.umbaska.Misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import uk.co.umbaska.Utils.TitleManager.TitleManager;






public class EffActionBar
  extends Effect
  implements Listener
{
  private Expression<String> Actiontitle;
  private Expression<Player> Players;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.Actiontitle = exprs[0];
    this.Players = exprs[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "titles";
  }
  

  protected void execute(Event event)
  {
    String newactiontitle = (String)this.Actiontitle.getSingle(event);
    Player[] playerlist = (Player[])this.Players.getAll(event);
    

    for (Player p : playerlist)
    {
      TitleManager.sendActionTitle(p, newactiontitle);
    }
  }
}
