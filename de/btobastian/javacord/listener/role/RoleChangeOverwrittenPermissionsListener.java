package de.btobastian.javacord.listener.role;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.VoiceChannel;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;

public abstract interface RoleChangeOverwrittenPermissionsListener
  extends Listener
{
  public abstract void onRoleChangeOverwrittenPermissions(DiscordAPI paramDiscordAPI, Role paramRole, Channel paramChannel, Permissions paramPermissions);
  
  public abstract void onRoleChangeOverwrittenPermissions(DiscordAPI paramDiscordAPI, Role paramRole, VoiceChannel paramVoiceChannel, Permissions paramPermissions);
}
