package de.btobastian.javacord.listener.user;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;

public abstract interface UserRoleAddListener
  extends Listener
{
  public abstract void onUserRoleAdd(DiscordAPI paramDiscordAPI, User paramUser, Role paramRole);
}
