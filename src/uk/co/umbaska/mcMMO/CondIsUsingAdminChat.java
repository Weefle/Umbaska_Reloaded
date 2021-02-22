package uk.co.umbaska.mcMMO;

import ch.njol.skript.conditions.base.PropertyCondition;
import com.gmail.nossr50.api.ChatAPI;
import org.bukkit.entity.Player;


public class CondIsUsingAdminChat
  extends PropertyCondition<Player>
{
  public boolean check(Player player)
  {
    return ChatAPI.isUsingAdminChat(player.getName());
  }
  
  protected String getPropertyName()
  {
    return "using partychat";
  }
}
