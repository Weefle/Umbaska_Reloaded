package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.FireworkMeta;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.ChatColor;
import uk.co.umbaska.Main;

public class ExprBlitzkrieg
  extends SimpleExpression<String>
{
  protected String[] get(Event event)
  {
    Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
    {
      public void run()
      {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
          Firework firework = (Firework)p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
          FireworkMeta fm = firework.getFireworkMeta();
          fm.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).withFade(Color.GREEN).build());
          fm.setPower(3);
          firework.setFireworkMeta(fm);
          firework.detonate();
        }
      }
    }, 20L, 20L);
    
    Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
    {
      public void run()
      {
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "HEIL GATT~");
      }
    }, 20L, 20L);
    
    return new String[] { "lewl scrub" };
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public String toString(Event event, boolean b)
  {
    return "Potion Effects of Area Effect Cloud";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
}
