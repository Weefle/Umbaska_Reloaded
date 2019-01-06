package de.btobastian.javacord.listener.user;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.listener.Listener;

public abstract interface UserChangeOverwrittenPermissionsListener
  extends Listener
{
  public abstract void onUserChangeOverwrittenPermissions(DiscordAPI paramDiscordAPI, User paramUser, Channel paramChannel, Permissions paramPermissions);
  
  public abstract void onUserChangeOverwrittenPermissions(DiscordAPI paramDiscordAPI, User paramUser, VoiceChannel paramVoiceChannel, Permissions paramPermissions);
}
