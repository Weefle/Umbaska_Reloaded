package uk.co.umbaska.LargeSk.viaversion;

import java.util.Arrays;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.google.common.collect.Iterables;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Registration.Syntaxes;
import us.myles.ViaVersion.api.ViaVersion;
import us.myles.ViaVersion.api.ViaVersionAPI;

@SuppressWarnings("deprecation")
@Syntaxes({"(protocol|(mc|minecraft)) ver[sion] of %player%", "%player%'s (protocol|(mc|minecraft)) ver[sion]"})
public class ExprMinecraftClientVersion
  extends PropertyExpression<Player, Integer>
{
  Expression<Player> pl;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.pl = (Expression<Player>) expr[0];
    return true;
  }
  
  @Nullable
  protected Integer[] get(Event e, Player[] f)
  {
    Player pl = (Player)Iterables.getFirst(Arrays.asList(f), null);
    if (pl == null) {
      return null;
    }
    ViaVersionAPI instance = ViaVersion.getInstance();
    int version = instance.getPlayerVersion(pl);
    return new Integer[] { Integer.valueOf(version) };
  }

@Override
public String toString(@Nullable Event arg0, boolean arg1) {
	// TODO Auto-generated method stub
	return "Client version of player";
}
}
