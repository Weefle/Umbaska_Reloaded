package uk.co.umbaska.NametagEdit;

import ca.wacos.nametagedit.NametagAPI;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;







public class EffSetSuffix
  extends Effect
{
  private Expression<Player> player;
  private Expression<String> changeto;
  
  protected void execute(Event event)
  {
    String p = ((Player)this.player.getSingle(event)).toString();
    String ct = (String)this.changeto.getSingle(event);
    if (p == null)
      return;
    if (ct == null) {
      return;
    }
    NametagAPI.setSuffix(p, ct);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Clear a plot";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = expressions[0];
    this.changeto = expressions[1];
    return true;
  }
}
