package uk.co.umbaska.NametagEdit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.nametagedit.plugin.NametagEdit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







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
    NametagEdit.getApi().setSuffix(p, ct);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Clear a plot";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    this.changeto = (Expression<String>) expressions[1];
    return true;
  }
}
