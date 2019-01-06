package de.btobastian.javacord.listener.role;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;

public abstract interface RoleDeleteListener
  extends Listener
{
  public abstract void onRoleDelete(DiscordAPI paramDiscordAPI, Role paramRole);
}
