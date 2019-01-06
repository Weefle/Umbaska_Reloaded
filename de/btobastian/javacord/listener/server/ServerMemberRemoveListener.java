package de.btobastian.javacord.listener.server;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.Listener;

public abstract interface ServerMemberRemoveListener
  extends Listener
{
  public abstract void onServerMemberRemove(DiscordAPI paramDiscordAPI, User paramUser, Server paramServer);
}
