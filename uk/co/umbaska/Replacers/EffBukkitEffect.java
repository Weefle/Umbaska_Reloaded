package uk.co.umbaska.Replacers;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Enums.BukkitEffectEnum;



public class EffBukkitEffect
  extends Effect
{
  private Expression<BukkitEffectEnum> particleName;
  private Expression<Number> data;
  private Expression<Number> secondaryData;
  private Expression<Location> locations;
  private Expression<Player> players;
  
  protected void execute(Event event)
  {
    BukkitEffectEnum particlename = (BukkitEffectEnum)this.particleName.getSingle(event);
    Location[] loc = (Location[])this.locations.getAll(event);
    Player[] players = (Player[])this.players.getAll(event);
    Integer data = Integer.valueOf(((Number)this.data.getSingle(event)).intValue());
    Integer secondaryData = Integer.valueOf(((Number)this.secondaryData.getSingle(event)).intValue());
    if (particlename == null) {
      return;
    }
    if (!ParticleFunction.spawnEffect(particlename, loc, data, secondaryData).booleanValue()) {
      Skript.error("Unknown Effect! " + particlename + " isn't a valid effect! \nSee https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Effect.html for valid particle effects!");
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "Spawn Particle";
  }
  

  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.particleName = expressions[0];
    this.locations = expressions[1];
    this.players = expressions[2];
    this.data = expressions[3];
    this.secondaryData = expressions[4];
    return true;
  }
}
