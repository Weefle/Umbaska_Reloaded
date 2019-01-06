package uk.co.umbaska.LargeSk.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Players With Permission")
@Syntaxes({"%players% with perm[ission[[ ]node]] %string%"})
public class ExprPlayersWithPermission
  extends SimpleUmbaskaExpression<Player>
{
  private Expression<Player> players;
  private Expression<String> permission;
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = expr[0];
    this.permission = expr[1];
    return true;
  }
  
  @Nullable
  protected Player[] get(Event e)
  {
    ArrayList<Player> r = new ArrayList();
    for (Player l : (Player[])this.players.getArray(e)) {
      if (l.hasPermission((String)this.permission.getSingle(e))) {
        r.add(l);
      }
    }
    return (Player[])r.toArray(new Player[r.size()]);
  }
}
