package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import uk.co.umbaska.Main;




public class ExprBungeeServerOfPlayer
  extends SimpleExpression<String>
{
  private Expression<String> player;
  
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
    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "return Bungee server of player";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String p = (String)this.player.getSingle(arg0);
    
    if (p == null) {
      return null;
    }
    
    for (Iterator i$ = Main.messenger.cache.allServers.iterator(); i$.hasNext();) { server = (String)i$.next();
      for (String ps : (List)Main.messenger.cache.playersOnlineServer.get(server))
        if (ps == p)
          return new String[] { server };
    }
    String server;
    return null;
  }
}
