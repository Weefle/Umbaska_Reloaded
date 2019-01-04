package uk.co.umbaska.Factions;

import org.bukkit.event.Event;

import com.massivecraft.factions.entity.Faction;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class ExprPowerboostOfFaction
  extends SimplePropertyExpression<Faction, Double>
{
  public Class<? extends Double> getReturnType()
  {
    return Double.class;
  }
  
  public Double convert(Faction fac)
  {
    return Double.valueOf(fac.getPowerBoost());
  }
  
  protected String getPropertyName()
  {
    return "faction powerboost";
  }
  
  public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    Faction faction = (Faction)getExpr().getSingle(e);
    if (faction == null)
      return;
    double power = ((Number)delta[0]).doubleValue();
    if (mode == Changer.ChangeMode.SET) {
      faction.setPowerBoost(Double.valueOf(power));
    }
  }
  

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }
}
