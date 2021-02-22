package uk.co.umbaska.Replacers;

import ch.njol.skript.lang.Expression;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.Enums.ParticleEnum;

public class EffParticle extends ch.njol.skript.lang.Effect
{
  private Expression<ParticleEnum> particleName;
  private Expression<Number> offx;
  private Expression<Number> offy;
  private Expression<Number> offz;
  private Expression<Number> speed;
  private Expression<Number> data;
  private Expression<Number> secondaryData;
  private Expression<Number> count;
  private Expression<Location> locations;
  private Expression<Player> players;
  
  protected void execute(Event event)
  {
    ParticleEnum particlename = (ParticleEnum)this.particleName.getSingle(event);
    Number offx = (Number)this.offx.getSingle(event);
    Number offy = (Number)this.offy.getSingle(event);
    Number offz = (Number)this.offz.getSingle(event);
    Number speed = (Number)this.speed.getSingle(event);
    Location[] loc = (Location[])this.locations.getAll(event);
    Integer count = Integer.valueOf(((Number)this.count.getSingle(event)).intValue());
    Player[] players = (Player[])this.players.getAll(event);
    Integer data = Integer.valueOf(((Number)this.data.getSingle(event)).intValue());
    Integer secondaryData = Integer.valueOf(((Number)this.secondaryData.getSingle(event)).intValue());
    if (particlename == null) {
      return;
    }
    ParticleFunction.spawnParticle(count, particlename, speed, offx, offy, offz, loc, data, players, secondaryData);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Spawn Particle";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, ch.njol.util.Kleenean kleenean, ch.njol.skript.lang.SkriptParser.ParseResult parseResult)
  {
    this.count = (Expression<Number>) expressions[0];
    this.particleName = (Expression<ParticleEnum>) expressions[1];
    this.speed = (Expression<Number>) expressions[2];
    this.offx = (Expression<Number>) expressions[3];
    this.offy = (Expression<Number>) expressions[4];
    this.offz = (Expression<Number>) expressions[5];
    this.locations = (Expression<Location>) expressions[6];
    this.players = (Expression<Player>) expressions[7];
    this.data = (Expression<Number>) expressions[8];
    this.secondaryData = (Expression<Number>) expressions[9];
    return true;
  }
}
