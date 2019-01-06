package de.btobastian.javacord.listener.role;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;

public abstract interface RoleChangePermissionsListener
  extends Listener
{
  public abstract void onRoleChangePermissions(DiscordAPI paramDiscordAPI, Role paramRole, Permissions paramPermissions);
}
