package uk.co.umbaska.Replacers;

import ch.njol.skript.lang.Expression;
import org.bukkit.Location;
import org.bukkit.event.Event;
import uk.co.umbaska.Enums.ParticleEnum;

public class EffParticleAll extends ch.njol.skript.lang.Effect
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
  
  protected void execute(Event event)
  {
    ParticleEnum particlename = (ParticleEnum)this.particleName.getSingle(event);
    Number offx = (Number)this.offx.getSingle(event);
    Number offy = (Number)this.offy.getSingle(event);
    Number offz = (Number)this.offz.getSingle(event);
    Number speed = (Number)this.speed.getSingle(event);
    Location[] loc = (Location[])this.locations.getAll(event);
    Integer count = Integer.valueOf(((Number)this.count.getSingle(event)).intValue());
    Integer data = Integer.valueOf(((Number)this.data.getSingle(event)).intValue());
    Integer secondaryData = Integer.valueOf(((Number)this.secondaryData.getSingle(event)).intValue());
    if (particlename == null) {
      return;
    }
    ParticleFunction.spawnParticle(count, particlename, speed, offx, offy, offz, loc, data, secondaryData);
  }
  

  public String toString(Event event, boolean b)
  {
    return "Spawn Particle";
  }
  

  public boolean init(Expression<?>[] expressions, int i, ch.njol.util.Kleenean kleenean, ch.njol.skript.lang.SkriptParser.ParseResult parseResult)
  {
    this.count = expressions[0];
    this.particleName = expressions[1];
    this.speed = expressions[2];
    this.offx = expressions[3];
    this.offy = expressions[4];
    this.offz = expressions[5];
    this.locations = expressions[6];
    this.data = expressions[7];
    this.secondaryData = expressions[8];
    return true;
  }
}
