package de.btobastian.javacord.listener.channel;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.listener.Listener;

public abstract interface ChannelChangeNameListener
  extends Listener
{
  public abstract void onChannelChangeName(DiscordAPI paramDiscordAPI, Channel paramChannel, String paramString);
}
