package uk.co.umbaska.Misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;




public class EffLeashEntityEntity
  extends Effect
{
  private Expression<Entity> entity;
  private Expression<Entity> entity2;
  
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parse)
  {
    this.entity = exprs[0];
    this.entity2 = exprs[1];
    
    return true;
  }
  
  public String toString(@Nullable Event arg0, boolean arg1) {
    return "Leash Entity to Block";
  }
  

  protected void execute(Event event)
  {
    ((LivingEntity)this.entity.getSingle(event)).setLeashHolder((Entity)this.entity2.getSingle(event));
  }
}
