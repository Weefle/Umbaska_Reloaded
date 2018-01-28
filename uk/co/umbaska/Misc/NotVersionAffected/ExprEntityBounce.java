package uk.co.umbaska.Misc.NotVersionAffected;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;



public class ExprEntityBounce
  extends SimplePropertyExpression<Entity, Boolean>
{
  List<EntityType> projectiles = new ArrayList(Arrays.asList(new EntityType[] { EntityType.THROWN_EXP_BOTTLE, EntityType.ARROW, EntityType.SPLASH_POTION, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.FISHING_HOOK, EntityType.SNOWBALL, EntityType.ENDER_PEARL, EntityType.WITHER_SKULL }));
  
  public Boolean convert(Entity ent)
  {
    if (ent == null)
      return null;
    if (this.projectiles.contains(ent.getType())) {
      return Boolean.valueOf(((Projectile)ent).doesBounce());
    }
    return Boolean.valueOf(false);
  }
  

  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Entity ent = (Entity)getExpr().getSingle(e);
    if (ent == null)
      return;
    Boolean b = (Boolean)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      if (this.projectiles.contains(ent.getType())) {
        ((Projectile)ent).setBounce(b.booleanValue());
      } else {}
    }
  }
  




  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
    return null;
  }
  
  public Class<? extends Boolean> getReturnType()
  {
    return Boolean.class;
  }
  

  protected String getPropertyName()
  {
    return "Does Bounce";
  }
}
