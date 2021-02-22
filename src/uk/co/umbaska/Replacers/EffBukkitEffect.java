package uk.co.umbaska.Replacers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
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
    if (!ParticleFunction.spawnEffect(particlename, loc, data, players, secondaryData).booleanValue()) {
      Skript.error("Unknown Effect! " + particlename + " isn't a valid effect! \nSee https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Effect.html for valid particle effects!");
    }
  }
  


  public String toString(Event event, boolean b)
  {
    return "Spawn Particle";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.particleName = (Expression<BukkitEffectEnum>) expressions[0];
    this.locations = (Expression<Location>) expressions[1];
    this.players = (Expression<Player>) expressions[2];
    this.data = (Expression<Number>) expressions[3];
    this.secondaryData = (Expression<Number>) expressions[4];
    return true;
  }
}
