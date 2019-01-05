package uk.co.umbaska.Misc.NotVersionAffected;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;



public class EffRemoveAllPotionEffects
  extends Effect
{
  private Expression<Entity> ent;
  
  public void execute(Event event)
  {
    LivingEntity[] entities = (LivingEntity[])this.ent.getAll(event);
    for (LivingEntity e : entities) {
      for (PotionEffect p : e.getActivePotionEffects()) {
        e.removePotionEffect(p.getType());
      }
    }
  }
  
  public String toString(Event event, boolean b) {
    return "Remove All Potion Effects";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ent = (Expression<Entity>) expressions[0];
    return true;
  }
}
