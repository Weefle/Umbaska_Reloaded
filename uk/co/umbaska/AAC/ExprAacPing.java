package uk.co.umbaska.AAC;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.konsolas.aac.api.AACAPIProvider;

public class ExprAacPing
  extends SimplePropertyExpression<Player, Integer>
{
  public Class<? extends Integer> getReturnType()
  {
    return Integer.class;
  }
  
  @Nullable
  public Integer convert(Player pl)
  {
    if (AACAPIProvider.isAPILoaded()) {
      return Integer.valueOf(AACAPIProvider.getAPI().getPing(pl));
    }
    return null;
  }

@Override
protected String getPropertyName() {
	// TODO Auto-generated method stub
	return "Ping of Player by AAC";
}
}
