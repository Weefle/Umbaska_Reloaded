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

@Name("Players Who Are Flying")
@Syntaxes({"%players% flying", "flying %players%"})
public class ExprPlayersFlying
  extends SimpleUmbaskaExpression<Player>
{
  private Expression<Player> players;
  
  public Class<? extends Player> getReturnType()
  {
    return Player.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.players = expr[0];
    return true;
  }
  
  @Nullable
  protected Player[] get(Event e)
  {
    ArrayList<Player> r = new ArrayList();
    for (Player l : (Player[])this.players.getArray(e)) {
      if (l.isFlying()) {
        r.add(l);
      }
    }
    return (Player[])r.toArray(new Player[r.size()]);
  }
}
