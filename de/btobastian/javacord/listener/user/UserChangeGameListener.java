package de.btobastian.javacord.listener.user;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.Listener;

public abstract interface UserChangeGameListener
  extends Listener
{
  public abstract void onUserChangeGame(DiscordAPI paramDiscordAPI, User paramUser, String paramString);
}
