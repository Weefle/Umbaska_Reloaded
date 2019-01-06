package uk.co.umbaska.LargeSk.viaversion;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaPropertyExpression;
import us.myles.ViaVersion.api.ViaVersion;
import us.myles.ViaVersion.api.ViaVersionAPI;

@Dependency("ViaVersion")
@Syntaxes({"(protocol|(mc|minecraft)) ver[sion] of %player%", "%player%'s (protocol|(mc|minecraft)) ver[sion]"})
public class ExprMinecraftClientVersion
  extends UmbaskaPropertyExpression<Player, Integer>
{
  Expression<Player> pl;
  
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.pl = expr[0];
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
}
