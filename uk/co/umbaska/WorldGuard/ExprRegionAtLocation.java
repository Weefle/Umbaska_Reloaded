package uk.co.umbaska.WorldGuard;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;



public class ExprRegionAtLocation
  extends SimpleExpression<ProtectedRegion>
{
  private Expression<Location> location;
  
  public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult)
  {
    this.location = exprs[0];
    return true;
  }
  
  protected ProtectedRegion[] get(Event e)
  {
    Location loc = (Location)this.location.getSingle(e);
    ApplicableRegionSet applicableregionset = WGBukkit.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
    
    LinkedList<ProtectedRegion> parents = new LinkedList();
    LinkedList<ProtectedRegion> regions = new LinkedList();
    Iterator<ProtectedRegion> localIterator = applicableregionset.iterator();
    while (localIterator.hasNext()) {
      ProtectedRegion region = (ProtectedRegion)localIterator.next();
      regions.add(region);
      parents.add(region.getParent());
    }
    for (ProtectedRegion reg : parents) {
      regions.remove(reg);
    }
    return (ProtectedRegion[])regions.toArray(new ProtectedRegion[regions.size()]);
  }
  

  public boolean isSingle()
  {
    return false;
  }
  
  public Class<? extends ProtectedRegion> getReturnType()
  {
    return ProtectedRegion.class;
  }
  
  public String toString(@Nullable Event e, boolean debug)
  {
    return "region list";
  }
}
