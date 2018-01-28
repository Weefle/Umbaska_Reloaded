package uk.co.umbaska.Factions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.util.coll.CollectionUtils;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import org.bukkit.event.Event;

public class ExprHomeOfFaction
  extends SimplePropertyExpression<Faction, Location>
{
  public Class<? extends Location> getReturnType()
  {
    return Location.class;
  }
  
  public Location convert(Faction fac)
  {
    return fac.getHome().asBukkitLocation();
  }
  
  protected String getPropertyName()
  {
    return "faction home";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Faction fac = (Faction)getExpr().getSingle(e);
    if (fac == null)
      return;
    Location loc = (Location)delta[0];
    if (mode == Changer.ChangeMode.SET) {
      fac.setHome(PS.valueOf(loc));
    }
    if (mode == Changer.ChangeMode.REMOVE) {
      fac.setHome(null);
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { Location.class });
  }
}
