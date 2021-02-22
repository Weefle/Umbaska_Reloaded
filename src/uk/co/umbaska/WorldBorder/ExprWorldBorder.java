package uk.co.umbaska.WorldBorder;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;









public class ExprWorldBorder
  extends SimpleExpression<Object>
{
  private Expression<World> world;
  private Integer matchType;
  
  public Class<? extends Object> getReturnType()
  {
    return Object.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.world = (Expression<World>) args[0];
    this.matchType = Integer.valueOf(arg1);
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    if (this.matchType.intValue() == 0)
      return "get size of world border";
    if (this.matchType.intValue() == 1)
      return "get damage amount of world border";
    if (this.matchType.intValue() == 2)
      return "get damage buffer of world border";
    if (this.matchType.intValue() == 3)
      return "get warning distance of world border";
    if (this.matchType.intValue() == 4) {
      return "get warning time of world border";
    }
    return "well something gone did gone fuck up aye it fam (world border)";
  }
  

  @Nullable
  protected Object[] get(Event arg0)
  {
    World w = (World)this.world.getSingle(arg0);
    WorldBorder wb = w.getWorldBorder();
    if (this.matchType.intValue() == 0)
      return new Double[] { Double.valueOf(wb.getSize()) };
    if (this.matchType.intValue() == 1)
      return new Double[] { Double.valueOf(wb.getDamageAmount()) };
    if (this.matchType.intValue() == 2)
      return new Double[] { Double.valueOf(wb.getDamageBuffer()) };
    if (this.matchType.intValue() == 3)
      return new Integer[] { Integer.valueOf(wb.getWarningDistance()) };
    if (this.matchType.intValue() == 4) {
      return new Integer[] { Integer.valueOf(wb.getWarningTime()) };
    }
    return new Object[] { null };
  }
}
