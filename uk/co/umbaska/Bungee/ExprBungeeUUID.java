package uk.co.umbaska.Bungee;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.GattSk.Extras.Collect;






public class ExprBungeeUUID
  extends SimpleExpression<String>
{
  private Expression<Player> player;
  
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
    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "Bungee uuid of player";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    Player[] player = (Player[])this.player.getAll(arg0);
    
    if (player == null) {
      return null;
    }
    List<String> l = new ArrayList();
    for (Player p : player) {
      l.add(p.getUniqueId().toString());
    }
    
    return (String[])Collect.asArray(l.toArray());
  }
}
