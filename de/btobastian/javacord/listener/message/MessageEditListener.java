package de.btobastian.javacord.listener.message;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.Listener;

public abstract interface MessageEditListener
  extends Listener
{
  public abstract void onMessageEdit(DiscordAPI paramDiscordAPI, Message paramMessage, String paramString);
}
