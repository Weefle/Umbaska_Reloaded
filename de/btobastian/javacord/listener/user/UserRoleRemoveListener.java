package de.btobastian.javacord.listener.user;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;

public abstract interface UserRoleRemoveListener
  extends Listener
{
  public abstract void onUserRoleRemove(DiscordAPI paramDiscordAPI, User paramUser, Role paramRole);
}
