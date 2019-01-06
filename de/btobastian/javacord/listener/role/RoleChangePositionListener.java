package de.btobastian.javacord.listener.role;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;

public abstract interface RoleChangePositionListener
  extends Listener
{
  public abstract void onRoleChangePosition(DiscordAPI paramDiscordAPI, Role paramRole, int paramInt);
}
