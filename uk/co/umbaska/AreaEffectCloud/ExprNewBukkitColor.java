package uk.co.umbaska.AreaEffectCloud;

import org.bukkit.Color;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprNewBukkitColor
  extends SimpleExpression<Color>
{
  private Expression<Number> r;
  private Expression<Number> g;
  private Expression<Number> b;
  
  protected Color[] get(Event event)
  {
    Number r = (Number)this.r.getSingle(event);
    Number g = (Number)this.g.getSingle(event);
    Number b = (Number)this.b.getSingle(event);
    return new Color[] { Color.fromRGB(r.intValue(), g.intValue(), b.intValue()) };
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends Color> getReturnType()
  {
    return Color.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "bukkit rgb color";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.r = (Expression<Number>) expressions[0];
    this.g = (Expression<Number>) expressions[1];
    this.b = (Expression<Number>) expressions[2];
    return true;
  }
}
