package uk.co.umbaska.AAC;

import javax.annotation.Nullable;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import org.bukkit.entity.Player;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.SimpleUmbaskaPropertyExpression;
import uk.co.umbaska.Registration.Syntaxes;

@Name("Ping of Player by AAC")
@Syntaxes({"AAC (ping of %player%|%player%'s ping)", "[AAC] (ping of %player%|%player%'s ping) by AAC"})
@Dependency("AAC")
public class ExprAacPing
  extends SimpleUmbaskaPropertyExpression<Player, Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  @Nullable
  public Integer convert(Player pl)
  {
    if (AACAPIProvider.isAPILoaded()) {
      return Integer.valueOf(AACAPIProvider.getAPI().getPing(pl));
    }
    return null;
  }
}
