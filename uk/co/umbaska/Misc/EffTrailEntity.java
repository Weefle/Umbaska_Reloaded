package uk.co.umbaska.Misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import uk.co.umbaska.Enums.ParticleEnum;
import uk.co.umbaska.GattSk.Extras.Collect;
import uk.co.umbaska.Main;
import uk.co.umbaska.Replacers.ParticleFunction;


public class EffTrailEntity
  extends Effect
  implements Listener
{
  private Expression<Entity> entity;
  private Expression<ParticleEnum> particle;
  private Expression<Number> d;
  private Expression<Number> d1;
  private Expression<Number> secd;
  private Expression<Number> count;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.count = exprs[1];
    this.entity = exprs[0];
    this.particle = exprs[2];
    this.d = exprs[3];
    this.d1 = exprs[4];
    this.secd = exprs[5];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "trail";
  }
  

  protected void execute(Event event)
  {
    Entity[] entities = (Entity[])this.entity.getAll(event);
    ParticleEnum part = (ParticleEnum)this.particle.getSingle(event);
    for (Entity e : entities)
    {
      new TrailEntity((Number)this.count.getSingle(event), e, part, (Number)this.d.getSingle(event), Integer.valueOf(((Number)this.d1.getSingle(event)).intValue()), Integer.valueOf(((Number)this.secd.getSingle(event)).intValue()), null);
    }
  }
  

  private class TrailEntity
  {
    BukkitTask runnable;
    Entity ent;
    ParticleEnum part;
    Number dRun = Integer.valueOf(0);
    Integer dataRun = Integer.valueOf(0);
    Integer sdataRun = Integer.valueOf(0);
    
    private TrailEntity(final Number count, final Entity ent, final ParticleEnum part, Number speed, Integer data, Integer secData) { this.ent = ent;
      this.part = part;
      this.dRun = speed;
      this.dataRun = data;
      this.sdataRun = secData;
      
      this.runnable = Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable()
      {
        public void run() {
          if ((ent == null) || (!ent.isValid())) {
            EffTrailEntity.TrailEntity.this.runnable.cancel();
          }
          if ((ent.getType() == EntityType.ARROW) && (((Arrow)ent).isOnGround())) {
            EffTrailEntity.TrailEntity.this.runnable.cancel();
          }
          else
            ParticleFunction.spawnParticle(Integer.valueOf(count.intValue()), part, EffTrailEntity.TrailEntity.this.dRun, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), (Location[])Collect.asArray(new Location[] { ent.getLocation() }), EffTrailEntity.TrailEntity.this.dataRun, EffTrailEntity.TrailEntity.this.sdataRun); } }, 1L, 1L);
    }
  }
}
