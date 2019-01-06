package de.btobastian.javacord.listener.server;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.listener.Listener;

public abstract interface ServerJoinListener
  extends Listener
{
  public abstract void onServerJoin(DiscordAPI paramDiscordAPI, Server paramServer);
}
