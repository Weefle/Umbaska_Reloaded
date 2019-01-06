package de.btobastian.javacord.listener.server;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.listener.Listener;

public abstract interface ServerMemberBanListener
  extends Listener
{
  public abstract void onServerMemberBan(DiscordAPI paramDiscordAPI, User paramUser, Server paramServer);
}
