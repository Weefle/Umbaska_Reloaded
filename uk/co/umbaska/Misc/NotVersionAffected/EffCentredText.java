package uk.co.umbaska.Misc.NotVersionAffected;

import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
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
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.msg = (Expression<String>) expressions[0];
    return true;
  }
}
