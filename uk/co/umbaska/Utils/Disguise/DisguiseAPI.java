package uk.co.umbaska.Utils.Disguise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.umbaska.Main;
import uk.co.umbaska.Utils.Disguise.DisguiseUTIL.Disguise;
import uk.co.umbaska.Utils.Disguise.DisguiseUTIL.PlayerDisguise;

public class DisguiseAPI
{
  private static DisguiseAPI api = new DisguiseAPI();
  
  private Set<Disguise> disguises = new java.util.HashSet();
  private JavaPlugin plugin;
  
  public static DisguiseAPI getAPI()
  {
    return api;
  }
  
  public void initialize(final JavaPlugin plugin)
  {
    this.plugin = plugin;
    
    plugin.getServer().getPluginManager().registerEvents(new org.bukkit.event.Listener()
    {


      @EventHandler
      public void onRespawn(PlayerRespawnEvent e)
      {

        new BukkitRunnable()
        {

          public void run() { ((DisguiseAPI)Main.disguiseAPI).refresh(); } }.runTaskLater(plugin, 1L);
      }
      




      @EventHandler
      public void onDeath(final EntityDeathEvent e)
      {
        new BukkitRunnable()
        {
          public void run() {
            if (((DisguiseAPI)Main.disguiseAPI).getDisguise(e.getEntity()) == null)
              return;
            ((DisguiseAPI)Main.disguiseAPI).unDisguisePlayer(e.getEntity()); } }.runTaskLater(plugin, 1L);
      }
      

      @EventHandler
      public void onMove(PlayerMoveEvent e)
      {
        Player p = e.getPlayer();
        if (DisguiseAPI.this.getDisguise(p) == null)
          return;
        Disguise dis = ((DisguiseAPI)Main.disguiseAPI).getDisguise(p);
        dis.move(e.getFrom(), e.getTo());
      }
      
      @EventHandler
      public void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        for (final Disguise d : DisguiseAPI.this.disguises) {
          new BukkitRunnable()
          {

            public void run() {
              d.revertDisguise(Arrays.asList(new Player[] { p })); } }.runTaskLater(plugin, 1L);
        }
        


        if (((DisguiseAPI)Main.disguiseAPI).getDisguise(p) == null)
          return;
        Disguise d = ((DisguiseAPI)Main.disguiseAPI).getDisguise(p);
        d.revertDisguise(DisguiseAPI.this.online());
        DisguiseAPI.this.disguises.remove(d);
      }
      
      @EventHandler
      public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        for (final Disguise d : DisguiseAPI.this.disguises) {
          new BukkitRunnable()
          {

            public void run() {
              d.applyDisguise(Arrays.asList(new Player[] { p })); } }.runTaskLater(plugin, 1L);
        }
      }
      


      @EventHandler
      public void onHoldItem(PlayerItemHeldEvent e)
      {
        Player p = e.getPlayer();
        if (((DisguiseAPI)Main.disguiseAPI).getDisguise(p) == null)
          return;
        Disguise dis = ((DisguiseAPI)Main.disguiseAPI).getDisguise(p);
        if (!(dis instanceof PlayerDisguise))
          return;
        PlayerDisguise pd = (PlayerDisguise)dis;
        pd.setItemInHand(e.getNewSlot());
      }
      
      @EventHandler
      public void onInteract(PlayerInteractEvent e) {
        if ((e.getAction() != org.bukkit.event.block.Action.LEFT_CLICK_AIR) && (e.getAction() != org.bukkit.event.block.Action.LEFT_CLICK_BLOCK))
        {
          return; }
        Player p = e.getPlayer();
        if (((DisguiseAPI)Main.disguiseAPI).getDisguise(p) == null)
          return;
        Disguise dis = ((DisguiseAPI)Main.disguiseAPI).getDisguise(p);
        if (!(dis instanceof PlayerDisguise))
          return;
        PlayerDisguise pd = (PlayerDisguise)dis;
        pd.swingArm();
      }
      
      @EventHandler
      public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (((DisguiseAPI)Main.disguiseAPI).getDisguise(p) == null)
          return;
        Disguise dis = ((DisguiseAPI)Main.disguiseAPI).getDisguise(p);
        if (!(dis instanceof PlayerDisguise))
          return;
        PlayerDisguise pd = (PlayerDisguise)dis;
        pd.sneak(e.isSneaking()); } }, plugin);
  }
  



  public List<Entity> getDisguised()
  {
    List<Entity> s = new ArrayList();
    for (Disguise d : this.disguises) {
      s.add(d.getPlayer());
    }
    return s;
  }
  
  public boolean isDisguised(Entity p)
  {
    for (Disguise d : this.disguises) {
      if (d.getPlayer().equals(p))
        return true;
    }
    return false;
  }
  
  public Disguise getDisguise(Entity p)
  {
    for (Disguise d : this.disguises)
    {
      if (d.getPlayer().getUniqueId().toString().equals(p.getUniqueId().toString()))
        return d;
    }
    return null;
  }
  
  public void unDisguisePlayer(Entity p)
  {
    if (!isDisguised(p))
      return;
    getDisguise(p).revertDisguise(online());
    this.disguises.remove(getDisguise(p));
  }
  
  public Collection<Player> online() {
    return new ArrayList(org.bukkit.Bukkit.getServer().getOnlinePlayers());
  }
  

  public void disguisePlayer(Entity p, Disguise d, Collection<Player> players)
  {
    if (isDisguised(p))
      return;
    d.applyDisguise(players);
    this.disguises.add(d);
  }
  
  public void disguisePlayer(Entity p, Disguise d) {
    disguisePlayer(p, d, online());
  }
  
  public void refresh()
  {
    for (Disguise d : this.disguises) {
      refresh(d.getPlayer());
    }
  }
  

  private void refresh(Entity p)
  {
    final Disguise d = getDisguise(p);
    new BukkitRunnable()
    {
      public void run()
      {
        d.revertDisguise(DisguiseAPI.this.online());
        d.applyDisguise(DisguiseAPI.this.online()); } }.runTaskLater(this.plugin, 1L);
  }
}
