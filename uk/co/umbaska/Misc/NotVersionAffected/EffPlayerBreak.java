package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import uk.co.umbaska.Enums.BukkitEffectEnum;
import uk.co.umbaska.Replacers.ParticleFunction;







public class EffPlayerBreak
  extends Effect
{
  private Expression<Location> location;
  private Expression<Player> player;
  
  @SuppressWarnings("deprecation")
protected void execute(Event event)
  {
    Location l = (Location)this.location.getSingle(event);
    Player p = (Player)this.player.getSingle(event);
    if (l.getBlock().getType() != Material.AIR) {
      ParticleFunction.spawnEffect(BukkitEffectEnum.STEP_SOUND, new Location[] { l }, Integer.valueOf(l.getBlock().getType().getId()), Integer.valueOf(0));
      BlockBreakEvent e = new BlockBreakEvent(l.getBlock(), p);
      Bukkit.getPluginManager().callEvent(e);
      if (!e.isCancelled()) {
        l.getBlock().breakNaturally();
      }
    }
  }
  

  public String toString(Event event, boolean b)
  {
    return "Force Break Blocck - Player";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.player = (Expression<Player>) expressions[0];
    this.location = (Expression<Location>) expressions[1];
    return true;
  }
}
