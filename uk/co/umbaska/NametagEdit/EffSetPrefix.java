package uk.co.umbaska.NametagEdit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ca.wacos.nametagedit.NametagAPI;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffSetPrefix
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
    NametagAPI.setPrefix(p, ct);
  }
  

  public String toString(Event event, boolean b)
  {
    return "set prefix";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    this.changeto = (Expression<String>) expressions[1];
    return true;
  }
}
