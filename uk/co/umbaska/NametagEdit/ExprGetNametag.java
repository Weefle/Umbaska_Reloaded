package uk.co.umbaska.NametagEdit;

import ca.wacos.nametagedit.NametagAPI;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;






public class ExprGetNametag
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
  

  public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.player = args[0];
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "get nametag";
  }
  

  @Nullable
  protected String[] get(Event arg0)
  {
    String p = ((Player)this.player.getSingle(arg0)).toString();
    
    if (p == null) {
      return null;
    }
    

    return new String[] { NametagAPI.getNametag(p) };
  }
}
