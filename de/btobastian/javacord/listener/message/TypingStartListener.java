package de.btobastian.javacord.listener.message;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.Listener;

public abstract interface TypingStartListener
  extends Listener
{
  public abstract void onTypingStart(DiscordAPI paramDiscordAPI, User paramUser, Channel paramChannel);
}
