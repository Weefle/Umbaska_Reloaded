package uk.co.umbaska.Misc;

import java.net.InetAddress;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.util.CachedServerIcon;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;







public class ExprNetworking
  extends SimpleExpression<Object>
  implements Listener
{
  private Integer matchType;
  private Expression<Player> player;
  
  public Class<? extends Object> getReturnType()
  {
    return Object.class;
  }
  
  public boolean isSingle()
  {
    return true;
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.matchType = Integer.valueOf(arg1);
    if ((arg1 > 9) && (arg1 < 16)) {
      this.player = (Expression<Player>) args[0];
    }
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "well something gone did gone fuck up aye it fam (networking)";
  }
  
  @Nullable
  protected Object[] get(Event arg0)
  {
    if (this.matchType.intValue() == 0)
      return new String[] { Bukkit.getIp() };
    if (this.matchType.intValue() == 1)
      return new Integer[] { Integer.valueOf(Bukkit.getPort()) };
    if (this.matchType.intValue() == 2)
      return new Long[] { Long.valueOf(Bukkit.getConnectionThrottle()) };
    if (this.matchType.intValue() == 3)
      return new Boolean[] { Boolean.valueOf(Bukkit.getOnlineMode()) };
    if (this.matchType.intValue() == 4)
      return new String[] { Bukkit.getVersion() };
    if (this.matchType.intValue() == 5)
      return new String[] { Bukkit.getServer().getMotd() };
    if (this.matchType.intValue() == 6)
      return new String[] { Bukkit.getServer().getName() };
    if (this.matchType.intValue() == 7)
      return new Integer[] { Integer.valueOf(Bukkit.getServer().getIdleTimeout()) };
    if (this.matchType.intValue() == 8)
      return new CachedServerIcon[] { Bukkit.getServerIcon() };
    if (this.matchType.intValue() == 9)
      return new Integer[] { Integer.valueOf(Bukkit.getMaxPlayers()) };
    if (this.matchType.intValue() == 10)
      return new String[] { ((Player)this.player.getSingle(arg0)).getAddress().getHostString() };
    if (this.matchType.intValue() == 11)
      return new Integer[] { Integer.valueOf(((Player)this.player.getSingle(arg0)).getAddress().getPort()) };
    if (this.matchType.intValue() == 12)
      return new String[] { ((Player)this.player.getSingle(arg0)).getAddress().getHostString() + ":" + ((Player)this.player.getSingle(arg0)).getAddress().getPort() };
    if (this.matchType.intValue() == 13)
      return new String[] { ((Player)this.player.getSingle(arg0)).getAddress().getHostName() };
    if (this.matchType.intValue() == 14)
      return new String[] { ((Player)this.player.getSingle(arg0)).getAddress().toString() };
    if (this.matchType.intValue() == 15) {
      InetAddress iad = null;
      try {
        iad = (InetAddress)Main.addressMap.get(((Player)this.player.getSingle(arg0)).getUniqueId());
      } catch (NullPointerException e) {
        try {
          System.out.println("[Umbaska] Couldn't get Address of: " + ((Player)this.player.getSingle(arg0)).getName() + " (" + this.player.getSingle(arg0) + ")");
        } catch (NullPointerException ee) {
          System.out.println("[Umbaska] Oh how... double try failed.");
        }
      }
      if (iad == null) {
        Main.addressMap.put(((Player)this.player.getSingle(arg0)).getUniqueId(), null);
        


        return null;
      }
      return new InetAddress[] { iad }; }
    if (this.matchType.intValue() == 16) {
      return new String[] { Bukkit.getIp() + ":" + Bukkit.getPort() };
    }
    
    return new Object[] { null };
  }
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
  public final void onPlayerLogin(PlayerLoginEvent ev) {
    if (Main.addressMap.containsKey(ev.getPlayer().getUniqueId())) {
      Main.addressMap.remove(ev.getPlayer().getUniqueId());
    }
    InetAddress addr = ev.getAddress();
    System.out.println("[Umbaska] Adding: " + ev.getPlayer().getName() + " to the address map (" + addr.toString() + ")");
    Main.addressMap.put(ev.getPlayer().getUniqueId(), addr);
  }
}
