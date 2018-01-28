package uk.co.umbaska.Misc.UM2_0;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.event.Event;





public class ExprOffsetLocation
  extends SimpleExpression<Location>
{
  private Expression<Location> cmd;
  private Expression<Number> xx;
  private Expression<Number> yy;
  private Expression<Number> zz;
  
  public Class<? extends Location> getReturnType()
  {
    return Location.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.cmd = args[0];
    this.xx = args[1];
    this.yy = args[2];
    this.zz = args[3];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "Offset Location";
  }
  

  @Nullable
  protected Location[] get(Event arg0)
  {
    Location c = (Location)this.cmd.getSingle(arg0);
    Integer x = Integer.valueOf(((Number)this.xx.getSingle(arg0)).intValue());
    Integer y = Integer.valueOf(((Number)this.yy.getSingle(arg0)).intValue());
    Integer z = Integer.valueOf(((Number)this.zz.getSingle(arg0)).intValue());
    return new Location[] { c.add(x.intValue(), y.intValue(), z.intValue()) };
  }
}
