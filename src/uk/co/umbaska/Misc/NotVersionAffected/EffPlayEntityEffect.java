package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;










public class EffPlayEntityEffect
  extends Effect
{
  private Expression<Entity> ent;
  private Expression<EntityEffect> effect;
  
  protected void execute(Event event)
  {
    ((Entity)this.ent.getSingle(event)).playEffect((EntityEffect)this.effect.getSingle(event));
  }
  

  public String toString(Event event, boolean b)
  {
    return "Play Entity Effect";
  }
  

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ent = (Expression<Entity>) expressions[1];
    this.effect = (Expression<EntityEffect>) expressions[0];
    return true;
  }
}
