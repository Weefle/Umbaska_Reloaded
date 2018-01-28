package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.net.InetAddress;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;
import uk.co.umbaska.GattSk.Extras.Collect;








public class ExprServerPingIP
  extends SimpleExpression<String>
{
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    return ScriptLoader.isCurrentEvent(ServerListPingEvent.class);
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "server ping IP";
  }
  
  @Nullable
  protected String[] get(Event arg0)
  {
    return (String[])Collect.asArray(new String[] { ((ServerListPingEvent)arg0).getAddress().getHostAddress() });
  }
}
