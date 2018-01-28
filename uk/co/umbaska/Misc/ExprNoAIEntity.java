package uk.co.umbaska.Misc;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;


public class ExprNoAIEntity
  extends SimplePropertyExpression<org.bukkit.entity.Entity, org.bukkit.entity.Entity>
{
  public String getPropertyName()
  {
    return "no ai entity";
  }
  
  public org.bukkit.entity.Entity convert(org.bukkit.entity.Entity ent)
  {
    net.minecraft.server.v1_9_R1.Entity nmsent = ((CraftEntity)ent).getHandle();
    NBTTagCompound compoundTag = new NBTTagCompound();
    nmsent.c(compoundTag);
    compoundTag.setInt("NoAI", 1);
    nmsent.f(compoundTag);
    
    return nmsent.getBukkitEntity();
  }
  
  public Class<? extends org.bukkit.entity.Entity> getReturnType() {
    return org.bukkit.entity.Entity.class;
  }
}
