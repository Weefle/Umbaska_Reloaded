package uk.co.umbaska.PlaceHolderAPI;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.clip.placeholderapi.PlaceholderAPI;






public class EffParse
  extends SimpleExpression<String>
{
  private Expression<String> in;
  private Expression<Player> player;
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.in = (Expression<String>) args[0];
    if (arg1 == 0) {
      this.player = (Expression<Player>) args[1];
    }
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "placeholder parse %string% as %player%";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String s = (String)this.in.getSingle(arg0);
    Player p = (Player)this.player.getSingle(arg0);
    
    if (s == null)
      return null;
    if (p == null) {
      p = null;
    }
    

    return new String[] { PlaceholderAPI.setPlaceholders(p, s) };
  }
}
