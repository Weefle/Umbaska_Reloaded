package uk.co.umbaska.ArmourStands;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.minecraft.server.v1_9_R2.NBTTagCompound;



public class ExprSilent
  extends SimplePropertyExpression<org.bukkit.entity.Entity, Boolean>
{
  public Boolean convert(org.bukkit.entity.Entity ent)
  {
    if (ent == null) {
      return null;
    }
    net.minecraft.server.v1_9_R2.Entity nmsarmorstand = ((CraftEntity)ent).getHandle();
    NBTTagCompound compoundTag = new NBTTagCompound();
    nmsarmorstand.c(compoundTag);
    if (compoundTag.getInt("Silent") == 1) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  

  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    org.bukkit.entity.Entity ent = (org.bukkit.entity.Entity)getExpr().getSingle(e);
    if (ent == null)
      return;
    Boolean b = (Boolean)delta[0];
    net.minecraft.server.v1_9_R2.Entity nmsarmorstand = ((CraftEntity)ent).getHandle();
    NBTTagCompound compoundTag = new NBTTagCompound();
    nmsarmorstand.c(compoundTag);
    int v = 0;
    if (b.booleanValue()) {
      v = 1;
    }
    if (mode == Changer.ChangeMode.SET) {
      compoundTag.setInt("Silent", v);
    }
    nmsarmorstand.f(compoundTag);
  }
  


  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Boolean.class });
    return null;
  }
  
  public Class<? extends Boolean> getReturnType() {
    return Boolean.class;
  }
  

  protected String getPropertyName()
  {
    return "silent state";
  }
}
