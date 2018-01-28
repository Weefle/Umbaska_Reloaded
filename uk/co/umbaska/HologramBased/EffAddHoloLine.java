package uk.co.umbaska.HologramBased;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;





public class EffAddHoloLine
  extends Effect
{
  private Expression<String> HologramName;
  private Expression<String> Text;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.HologramName = exprs[0];
    this.Text = exprs[1];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "add holo line";
  }
  

  protected void execute(Event event)
  {
    String[] text = (String[])this.Text.getAll(event);
    String holoName = (String)this.HologramName.getSingle(event);
    if (holoName == null) {
      return;
    }
    Hologram h = Holograms.get(holoName);
    


    h.setLines(text);
  }
}
