package uk.co.umbaska.NametagEdit;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.nametagedit.plugin.NametagEdit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;







public class EffSetNametag
  extends Effect
{
  private Expression<Player> player;
  private Expression<String> changeto;
  private Expression<String> changeto2;
  
  protected void execute(Event event)
  {
    String p = ((Player)this.player.getSingle(event)).toString();
    String ct = (String)this.changeto.getSingle(event);
    String ct2 = (String)this.changeto2.getSingle(event);
    if (p == null)
      return;
    if (ct == null) {
      return;
    }
    NametagEdit.getApi().setNametag(p, ct, ct2);
  }
  

  public String toString(Event event, boolean b)
  {
    return "set suffix";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    this.changeto = (Expression<String>) expressions[1];
    this.changeto2 = (Expression<String>) expressions[2];
    return true;
  }
}
