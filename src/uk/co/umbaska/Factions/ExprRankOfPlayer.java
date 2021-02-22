package uk.co.umbaska.Factions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;



public class ExprRankOfPlayer
  extends SimpleExpression<Rel>
{
  private Expression<Player> player;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) exprs[0];
    return true;
  }
  
  protected Rel[] get(Event e)
  {
    Player player = (Player)this.player.getSingle(e);
    if (player == null)
      return null;
    MPlayer mplayer = (MPlayer)MPlayerColl.get().get(player);
    Rel r = mplayer.getRole();
    return new Rel[] { r };
  }
  
  public Class<? extends Rel> getReturnType()
  {
    return Rel.class;
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Player player = (Player)this.player.getSingle(e);
    
    if (player == null)
      return;
    MPlayer mplayer = (MPlayer)MPlayerColl.get().get(player);
    Rel rel = (Rel)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      mplayer.setRole(rel);
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      mplayer.setRole(Rel.RECRUIT);
    }
  }
  
  public boolean isSingle()
  {
    return true;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "relation between faction";
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Rel.class });
  }
}
