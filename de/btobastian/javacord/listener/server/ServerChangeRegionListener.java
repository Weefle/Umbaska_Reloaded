package de.btobastian.javacord.listener.server;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Region;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.listener.Listener;

public abstract interface ServerChangeRegionListener
  extends Listener
{
  public abstract void onServerChangeRegion(DiscordAPI paramDiscordAPI, Server paramServer, Region paramRegion);
}
