package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;



public class ExprBlankEnderpearl
  extends SimplePropertyExpression<Entity, Entity>
{
  public String getPropertyName()
  {
    return "blank enderpearl";
  }
  
  public Entity convert(Entity itemStack) {
    Entity ent = ((World)Bukkit.getWorlds().get(0)).spawnEntity(((World)Bukkit.getWorlds().get(0)).getSpawnLocation(), itemStack.getType());
    if (itemStack.getType() != EntityType.ENDER_PEARL) {
      ent.remove();
      return ent;
    }
    ((EnderPearl)ent).setShooter(null);
    ent.remove();
    return ent;
  }
  
  public Class<? extends Entity> getReturnType()
  {
    return Entity.class;
  }
}
