package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.FireworkMeta;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Main;

public class EffRav3
  extends Effect
{
  protected void execute(Event event)
  {
    Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable()
    {
      public void run() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
          Firework firework = (Firework)p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
          FireworkMeta fm = firework.getFireworkMeta();
          fm.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).withFade(Color.GREEN).build());
          fm.setPower(2);
          firework.setFireworkMeta(fm);
          firework.detonate(); } } }, 20L, 20L);
  }
  




  public String toString(Event event, boolean b)
  {
    return "rav3";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    return true;
  }
}
