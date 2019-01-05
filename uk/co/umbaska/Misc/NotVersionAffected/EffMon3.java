package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.ChatColor;
import uk.co.umbaska.Main;






public class EffMon3
  extends Effect
{
  protected void execute(Event event)
  {
    Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable()
    {
      public void run() {
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you...");
        Bukkit.broadcastMessage("" + ChatColor.GREEN + "You get Mon3, you get Mon3, you ALL get Mon3! Except you... I don't like you..."); } }, 20L, 20L);
  }
  



  public String toString(Event event, boolean b)
  {
    return "mon3";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
}
