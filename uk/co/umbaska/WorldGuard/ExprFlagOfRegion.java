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
import ch.njol.util.coll.CollectionUtils;





public class ExprFlagOfRegion
  extends SimpleExpression<String>
{
  private Expression<Flag<?>> flag;
  private Expression<ProtectedRegion> region;
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.flag = (Expression<Flag<?>>) exprs[0];
    this.region = (Expression<ProtectedRegion>) exprs[1];
    return true;
  }
  
  protected String[] get(Event e)
  {
    ProtectedRegion region = (ProtectedRegion)this.region.getSingle(e);
    Flag<?> flag = this.flag.getSingle(e);
    try {
      if (flag != null) {
        return new String[] { region.getFlag(flag).toString() };
      }
    }
    catch (Exception exception) {}
    

    return null;
  }
  

  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends String> getReturnType()
  {
    return String.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "flag list";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ProtectedRegion region = (ProtectedRegion)this.region.getSingle(e);
    Flag<?> flag = this.flag.getSingle(e);
    if (region == null) {
      return;
    }
    if (mode == Changer.ChangeMode.SET) {
      String val = (String)delta[0];
      region.getFlags().put(flag, val);
    }
    
    if (mode == Changer.ChangeMode.RESET) {
      region.setFlag(flag, null);
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { String.class });
    if (mode == Changer.ChangeMode.RESET)
      return new Class[0];
    return null;
  }
}
