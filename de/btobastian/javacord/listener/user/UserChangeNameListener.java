package de.btobastian.javacord.listener.user;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.Listener;

public abstract interface UserChangeNameListener
  extends Listener
{
  public abstract void onUserChangeName(DiscordAPI paramDiscordAPI, User paramUser, String paramString);
}
