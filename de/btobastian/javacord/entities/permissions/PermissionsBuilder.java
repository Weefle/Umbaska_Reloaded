package de.btobastian.javacord.entities.permissions;

public abstract interface PermissionsBuilder
{
  public abstract PermissionsBuilder setState(PermissionType paramPermissionType, PermissionState paramPermissionState);
  
  public abstract PermissionState getState(PermissionType paramPermissionType);
  
  public abstract Permissions build();
}
