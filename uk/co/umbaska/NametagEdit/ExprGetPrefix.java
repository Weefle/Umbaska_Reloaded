package uk.co.umbaska.NametagEdit;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.nametagedit.plugin.api.NametagAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;






public class ExprGetPrefix
  extends SimpleExpression<String>
{
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
    this.player = (Expression<Player>) args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get prefix";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String p = ((Player)this.player.getSingle(arg0)).toString();
    
    if (p == null) {
      return null;
    }
    

    return new String[] { NametagAPI.getPrefix(p) };
  }
}
