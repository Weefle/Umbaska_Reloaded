package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;
import uk.co.umbaska.GattSk.Extras.Collect;





public class EffCentredText
  extends SimpleExpression<String>
{
  private Expression<String> msg;
  
  protected String[] get(Event event)
  {
    String msg = (String)this.msg.getSingle(event);
    
    return (String[])Collect.asArray(new String[] { StringUtils.center(msg, 52) });
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
    return true;
  }
}
