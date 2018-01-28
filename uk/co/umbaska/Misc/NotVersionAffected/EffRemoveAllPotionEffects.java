package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;



public class EffRemoveAllPotionEffects
  extends Effect
{
  private Expression<Entity> ent;
  
  public void execute(Event event)
  {
    LivingEntity[] entities = (LivingEntity[])this.ent.getAll(event);
    LivingEntity e; for (e : entities) {
      for (PotionEffect p : e.getActivePotionEffects()) {
        e.removePotionEffect(p.getType());
      }
    }
  }
  
  public String toString(Event event, boolean b) {
    return "Remove All Potion Effects";
  }
  
  public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult)
  {
    this.ent = expressions[0];
    return true;
  }
}
