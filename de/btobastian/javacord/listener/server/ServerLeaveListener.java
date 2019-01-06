package de.btobastian.javacord.listener.server;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.listener.Listener;

public abstract interface ServerLeaveListener
  extends Listener
{
  public abstract void onServerLeave(DiscordAPI paramDiscordAPI, Server paramServer);
}
