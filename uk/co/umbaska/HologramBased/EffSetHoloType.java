package uk.co.umbaska.HologramBased;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;


public class EffSetHoloType
  extends Effect
{
  private boolean as;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.as = (parse.mark == 0);
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) { return "titles"; }
  


  protected void execute(Event event)
  {
    HologramCentral.setUsingWitherSkulls(this.as);
  }
}
