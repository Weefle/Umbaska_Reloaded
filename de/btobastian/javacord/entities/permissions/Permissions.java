package de.btobastian.javacord.entities.permissions;

public abstract interface Permissions
{
  public abstract PermissionState getState(PermissionType paramPermissionType);
}
