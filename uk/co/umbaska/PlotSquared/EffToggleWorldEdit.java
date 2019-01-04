package uk.co.umbaska.PlotSquared;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.intellectualcrafters.plot.object.PlotPlayer;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;









public class EffToggleWorldEdit
  extends Effect
{
  private Expression<Player> player;
  private Expression<Boolean> setting;
  private Integer matchType;
  
  protected void execute(Event event)
  {
    Player pl = (Player)this.player.getSingle(event);
    if (pl == null) {
      return;
    }
    PlotPlayer pp = PlotPlayer.get(this.player.toString());
    if ((this.matchType.intValue() == 0) || (this.matchType.intValue() == 3) || (this.matchType.intValue() == 4)) {
      Boolean s = null;
      if (this.matchType.intValue() == 0) {
        s = (Boolean)this.setting.getSingle(event);
      } else if (this.matchType.intValue() == 3) {
        s = Boolean.valueOf(true);
      } else {
        s = Boolean.valueOf(false);
      }
      if (s == null) {
        return;
      }
      if (pp == null) {
        return;
      }
      if (s.booleanValue() == true) {
        if (!pp.getAttribute("worldedit")) {
          pp.setAttribute("worldedit");
        }
      }
      else if (pp.getAttribute("worldedit")) {
        pp.removeAttribute("worldedit");
      }
      
    }
    else if (pp.getAttribute("worldedit")) {
      pp.removeAttribute("worldedit");
    } else {
      pp.setAttribute("worldedit");
    }
  }
  



  public String toString(Event event, boolean b)
  {
    return "set worldedit of %player% to true OR toggle worldedit of player";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    if (i == 0) {
      this.setting = (Expression<Boolean>) expressions[1];
    }
    this.matchType = Integer.valueOf(i);
    return true;
  }
}
