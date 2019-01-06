package de.btobastian.javacord.listener.role;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.Listener;
import java.awt.Color;

public abstract interface RoleChangeColorListener
  extends Listener
{
  public abstract void onRoleChangeColor(DiscordAPI paramDiscordAPI, Role paramRole, Color paramColor);
}
