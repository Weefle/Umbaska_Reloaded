package uk.co.umbaska.JSON;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.GattSk.Extras.Collect;



public class ExprJsonAppend
  extends SimpleExpression<JSONMessage>
{
  private Expression<JSONMessage> json;
  private Expression<String> append;
  
  protected JSONMessage[] get(Event event)
  {
    JSONMessage j = (JSONMessage)this.json.getSingle(event);
    String a = (String)this.append.getSingle(event);
    if ((j == null) || (a == null)) {
      return null;
    }
    return (JSONMessage[])Collect.asArray(new JSONMessage[] { j.then(a) });
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends JSONMessage> getReturnType()
  {
    return JSONMessage.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return ((JSONMessage)this.json.getSingle(event)).toOldMessageFormat();
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.json = (Expression<JSONMessage>) expressions[0];
    this.append = (Expression<String>) expressions[1];
    return true;
  }
}
