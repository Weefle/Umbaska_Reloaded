package uk.co.umbaska.Bungee;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;
import uk.co.umbaska.GattSk.Extras.Collect;






public class ExprBungeeAllPlayers
  extends SimpleExpression<String>
{
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
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "all Bungee players";
  }
  


  @Nullable
  protected String[] get(Event arg0)
  {
    Main.messenger.getAllPlayers();
    
    return (String[])Collect.asArray(Main.messenger.cache.allPlayersOnline.toArray(new String[Main.messenger.cache.allPlayersOnline.size()]));
  }
}
