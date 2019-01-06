package uk.co.umbaska.LargeSk.skinsrestorer;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import skinsrestorer.shared.api.SkinsRestorerAPI;
import uk.co.umbaska.Registration.Dependency;
import uk.co.umbaska.Registration.Name;
import uk.co.umbaska.Registration.Syntaxes;
import uk.co.umbaska.Registration.UmbaskaCondition;

@Name("If Player has a Custom Skin")
@Syntaxes({"%offlineplayer% (has|have) [a] skin"})
@Dependency("SkinsRestorer")
public class CondPlayerHasSkin
  extends UmbaskaCondition
{
  private Expression<OfflinePlayer> p;
  
  public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.p = e[0];
    return true;
  }
  
  public boolean check(Event e)
  {
    return SkinsRestorerAPI.hasSkin(((OfflinePlayer)this.p.getSingle(e)).getName()) == true;
  }
}
