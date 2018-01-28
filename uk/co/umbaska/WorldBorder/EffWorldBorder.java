package uk.co.umbaska.WorldBorder;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

















public class EffWorldBorder
  extends Effect
{
  private Expression<World> world;
  private Expression<Location> location;
  private Expression<Double> c1;
  private Expression<Double> c2;
  private Expression<Long> s1;
  private Expression<Integer> w1;
  private Integer matchType;
  
  protected void execute(Event event)
  {
    World w = (World)this.world.getSingle(event);
    WorldBorder wb = w.getWorldBorder();
    if (this.matchType.intValue() == 0) {
      Location l = (Location)this.location.getSingle(event);
      wb.setCenter(l);
    } else if (this.matchType.intValue() == 1) {
      Double cc1 = (Double)this.c1.getSingle(event);
      Double cc2 = (Double)this.c2.getSingle(event);
      wb.setCenter(cc1.doubleValue(), cc2.doubleValue());
    } else if (this.matchType.intValue() == 1) {
      Double cc1 = (Double)this.c1.getSingle(event);
      wb.setDamageAmount(cc1.doubleValue());
    } else if (this.matchType.intValue() == 1) {
      Double cc1 = (Double)this.c1.getSingle(event);
      wb.setDamageBuffer(cc1.doubleValue());
    } else if (this.matchType.intValue() == 1) {
      Double cc1 = (Double)this.c1.getSingle(event);
      wb.setSize(cc1.doubleValue());
    } else if (this.matchType.intValue() == 1) {
      Double cc1 = (Double)this.c1.getSingle(event);
      Long ss1 = (Long)this.s1.getSingle(event);
      wb.setSize(cc1.doubleValue(), ss1.longValue());
    } else if (this.matchType.intValue() == 1) {
      Integer ww1 = (Integer)this.w1.getSingle(event);
      wb.setWarningDistance(ww1.intValue());
    } else if (this.matchType.intValue() == 1) {
      Integer ww1 = (Integer)this.w1.getSingle(event);
      wb.setWarningTime(ww1.intValue());
    }
    else {}
  }
  


  public String toString(Event event, boolean b)
  {
    return "set WHATEVER of worldborder in %world% to %type%";
  }
  



  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.world = expressions[0];
    this.matchType = Integer.valueOf(i);
    if (i == 0) {
      this.location = expressions[1];
    } else if (i == 1) {
      this.c1 = expressions[1];
      this.c2 = expressions[2];
    } else if ((i == 2) || (i == 3) || (i == 4) || (i == 5)) {
      this.c1 = expressions[1];
      if (i == 5) {
        this.s1 = expressions[2];
      }
    } else if ((i == 6) || (i == 7)) {
      this.s1 = expressions[1];
    }
    return true;
  }
}
