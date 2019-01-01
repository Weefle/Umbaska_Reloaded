package uk.co.umbaska.WorldGuard;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;



@SuppressWarnings("rawtypes")
public class ExprFlagsOfRegion
  extends SimpleExpression<Flag>
{
  private Expression<ProtectedRegion> region;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.region = (Expression<ProtectedRegion>) exprs[0];
    return true;
  }
  
  protected Flag[] get(Event e)
  {
    ProtectedRegion region = (ProtectedRegion)this.region.getSingle(e);
    try {
      if (region != null) {
        return (Flag[])region.getFlags().keySet().toArray(new Flag[region.getFlags().keySet().size()]);
      }
    }
    catch (Exception exception) {}
    


    return null;
  }
  
  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends Flag> getReturnType()
  {
    return Flag.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "flag list";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ProtectedRegion region = (ProtectedRegion)this.region.getSingle(e);
    
    if (region == null) {
      return;
    }
    if (mode == Changer.ChangeMode.RESET) {
      region.getFlags().clear();
    }
  }
  
  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.RESET)
      return new Class[0];
    return null;
  }
}
