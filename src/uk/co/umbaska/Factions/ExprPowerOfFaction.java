package uk.co.umbaska.Factions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.massivecraft.factions.entity.Faction;



public class ExprPowerOfFaction
  extends SimplePropertyExpression<Faction, Double>
{
  public Class<? extends Double> getReturnType()
  {
    return Double.class;
  }
  
  public Double convert(Faction faction)
  {
    return Double.valueOf(faction.getPower());
  }
  
  protected String getPropertyName()
  {
    return "faction power";
  }
}
