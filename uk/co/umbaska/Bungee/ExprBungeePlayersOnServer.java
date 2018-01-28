package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;




public class ExprBungeePlayersOnServer
  extends SimpleExpression<String>
{
  private Expression<String> server;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.server = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return Bungee players on server";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String server = (String)this.server.getSingle(arg0);
    
    if (server == null) {
      return null;
    }
    Main.messenger.getAllPlayersOnServer(server);
    if (Main.messenger.cache.playersOnlineServer.get(server) != null) {
      return (String[])((List)Main.messenger.cache.playersOnlineServer.get(server)).toArray(new String[0]);
    }
    return null;
  }
}
