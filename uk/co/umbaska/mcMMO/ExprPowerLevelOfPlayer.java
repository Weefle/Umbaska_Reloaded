package uk.co.umbaska.mcMMO;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.gmail.nossr50.api.ExperienceAPI;
import org.bukkit.entity.Player;



public class ExprPowerLevelOfPlayer
  extends SimplePropertyExpression<Player, Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  

  public Integer convert(Player player)
  {
    return Integer.valueOf(ExperienceAPI.getPowerLevel(player));
  }
  

  protected String getPropertyName()
  {
    return "power level";
  }
}
