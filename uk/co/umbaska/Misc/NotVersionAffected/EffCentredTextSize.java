package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;
import uk.co.umbaska.GattSk.Extras.Collect;





public class EffCentredTextSize
  extends SimpleExpression<String>
{
  private Expression<String> msg;
  private Expression<Integer> size;
  
  protected String[] get(Event event)
  {
    String msg = (String)this.msg.getSingle(event);
    Integer size = (Integer)this.size.getSingle(event);
    if (size.intValue() <= 0) {
      size = Integer.valueOf(52);
    }
    
    return (String[])Collect.asArray(new String[] { StringUtils.center(msg, size.intValue()) });
  }
  
  public boolean isSingle() {
    return true;
  }
  
  public Class<? extends String> getReturnType() {
    return String.class;
  }
  
  public String toString(Event event, boolean b) {
    return ((String)this.msg.getSingle(event)).toString();
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.msg = expressions[0];
    this.size = expressions[1];
    return true;
  }
}
