package uk.co.umbaska.WorldGuard;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;



public class ExprOwnersOfRegion
  extends SimpleExpression<OfflinePlayer>
{
  private Expression<ProtectedRegion> region;
  
  public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.region = exprs[0];
    return true;
  }
  
  protected OfflinePlayer[] get(Event e)
  {
    ProtectedRegion region = (ProtectedRegion)this.region.getSingle(e);
    Collection<UUID> uuids = region.getOwners().getUniqueIds();
    Collection<OfflinePlayer> r = new ArrayList(uuids.size());
    
    for (UUID uuid : uuids)
      r.add(Bukkit.getOfflinePlayer(uuid));
    return (OfflinePlayer[])r.toArray(new OfflinePlayer[r.size()]);
  }
  

  public boolean isSingle()
  {
    return true;
  }
  
  public Class<? extends OfflinePlayer> getReturnType()
  {
    return OfflinePlayer.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "owner list";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ProtectedRegion region = (ProtectedRegion)this.region.getSingle(e);
    
    if (region == null) {
      return;
    }
    if (mode == Changer.ChangeMode.ADD) {
      OfflinePlayer player = (OfflinePlayer)delta[0];
      region.getOwners().addPlayer(player.getUniqueId());
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      OfflinePlayer player = (OfflinePlayer)delta[0];
      region.getOwners().removePlayer(player.getUniqueId());
    }
    if (mode == Changer.ChangeMode.RESET) {
      region.getOwners().removeAll();
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { OfflinePlayer.class });
  }
}
