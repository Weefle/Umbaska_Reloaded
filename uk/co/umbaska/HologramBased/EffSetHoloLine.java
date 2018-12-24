package uk.co.umbaska.HologramBased;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;




public class EffSetHoloLine
  extends Effect
{
  private Expression<Integer> Line;
  private Expression<String> HologramName;
  private Expression<String> Text;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.Line = (Expression<Integer>) exprs[0];
    this.HologramName = (Expression<String>) exprs[1];
    this.Text = (Expression<String>) exprs[2];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "set specific line number";
  }
  
  protected void execute(Event event)
  {
    Integer lineNo = (Integer)this.Line.getSingle(event);
    String text = (String)this.Text.getSingle(event);
    String holoName = (String)this.HologramName.getSingle(event);
    if (holoName == null) {
      return;
    }
    Hologram h = Holograms.get(holoName);
    String[] currentLines = h.getLines();
    currentLines[lineNo.intValue()] = text;
    h.setLines(currentLines);
  }
}
