package de.btobastian.javacord.listener.message;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.Listener;

public abstract interface MessageDeleteListener
  extends Listener
{
  public abstract void onMessageDelete(DiscordAPI paramDiscordAPI, Message paramMessage);
}
